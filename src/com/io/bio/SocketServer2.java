package com.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * 服务端多线程：阻塞IO
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 下午1:48:22
 */
public class SocketServer2 {

	public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(83);
        try {
            while(true) {
                Socket socket = serverSocket.accept();
                //业务处理过程可以交给一个线程（这里可以使用线程池）,并且线程的创建是很耗资源的。
                //最终改变不了.accept()只能一个一个接受socket的情况,并且被阻塞的情况
                SocketServerThreadOne socketServerThreadOne = new SocketServerThreadOne(socket);
                new Thread(socketServerThreadOne).start();
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

class SocketServerThreadOne implements Runnable {

	private Socket socket;
	
	public SocketServerThreadOne(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		InputStream in = null;
		OutputStream out = null;
		try {
			// 收取信息
			in = socket.getInputStream();
			out = socket.getOutputStream();
			Integer sourcePort = socket.getPort();
			int maxLen = 1024;
			byte[] contextBytes = new byte[maxLen];
			// 阻塞，直到数据准备好
			int readLen = in.read(contextBytes, 0, maxLen);
			// 读取信息
			String message = new String(contextBytes, 0, readLen);
			// 打印信息
			System.out.println("服务器收到来自于端口："+sourcePort+"的信息："+message);
			// 发送信息
			out.write("回发响应信息！".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
                if(this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
            	e.printStackTrace();
            }
		}
	}
	
}