package com.communication.nio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * 
 * 服务端多线程：非阻塞IO
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 下午2:34:21
 */
public class SocketServer4 {

    private static Object xWait = new Object();

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(83);
        serverSocket.setSoTimeout(100);
        try {
            while(true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                } catch(SocketTimeoutException e1) {
                	// 执行到这里，说明本次accept没有接收到任何TCP连接
                	// 主线程在这里就可以做一些事情，记为X
                    synchronized (SocketServer4.xWait) {
                    	System.out.println("这次没有从底层接收到任何TCP连接，等待10毫秒，模拟事件X的处理时间");
                        SocketServer4.xWait.wait(10);
                    }
                    continue;
                }
                // 业务处理过程可以交给一个线程（这里可以使用线程池）,并且线程的创建是很耗资源的。
                // 最终改变不了.accept()只能一个一个接受socket连接的情况
                SocketServerThreadTwo socketServerThreadTwo = new SocketServerThreadTwo(socket);
                new Thread(socketServerThreadTwo).start();
            }
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
            if(serverSocket != null) {
                serverSocket.close();
            }
        }
    }
	
}

class SocketServerThreadTwo implements Runnable {

    private Socket socket;

    public SocketServerThreadTwo(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            Integer sourcePort = socket.getPort();
            int maxLen = 2048;
            byte[] contextBytes = new byte[maxLen];
            int realLen;
            StringBuffer message = new StringBuffer();
            // 收取信息（设置成非阻塞方式，这样read信息的时候，又可以做一些其他事情）
            this.socket.setSoTimeout(10);
            BIORead:while(true) {
                try {
                    while((realLen = in.read(contextBytes, 0, maxLen)) != -1) {
                        message.append(new String(contextBytes , 0 , realLen));
                        // 我们假设读取到“over”关键字
                        // 表示客户端的所有信息在经过若干次传送后，完成
                        if(message.indexOf("over") != -1) {
                            break BIORead;
                        }
                    }
                } catch(SocketTimeoutException e2) {
                	// 执行到这里，说明本次read没有接收到任何数据流
                	// 主线程在这里又可以做一些事情，记为Y
                	System.out.println("这次没有从底层接收到任务数据报文，等待10毫秒，模拟事件Y的处理时间");
                	continue;
                }
            }
            //下面打印信息
            Long threadId = Thread.currentThread().getId();
            System.out.println("服务器(线程：" + threadId + ")收到来自于端口：" + sourcePort + "的信息：" + message);

            //下面开始发送信息
            out.write("回发响应信息！".getBytes());

            //关闭
            out.close();
            in.close();
            this.socket.close();
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
}
