package com.communication.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * 
 * 服务端：非阻塞IO
 * 
 * @version 1.0
 * @date 2019年6月25日 下午2:27:24
 */
public class SocketServer3 {
	
	private static Object xWait = new Object();

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(83);
			serverSocket.setSoTimeout(100);
			while(true) {
				Socket socket = null;
				try {
					socket = serverSocket.accept();
					
				} catch (SocketTimeoutException e) {
					// 执行说明本次accept没有接收到任何TCP连接
					synchronized (SocketServer3.xWait) {
						System.out.println("这次没有从底层接收到任何TCP连接，等待10毫秒，模拟时间X的处理时间");
						SocketServer3.xWait.wait(10);
					}
					continue;
				}
				
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				Integer sourcePort = socket.getPort();
				int maxLen = 2048;
				byte[] contextBytes = new byte[maxLen];
				int reaLen;
				StringBuffer message = new StringBuffer();
				// 收取信息(设置非阻塞方式)
				socket.setSoTimeout(10);
				BIORead:while(true) {
					try {
						while((reaLen = in.read(contextBytes, 0, maxLen)) != -1) {
							message.append(new String(contextBytes, 0, reaLen));
							// 假设读取到“over”关键字
							// 表示客户端的所有信息在经过若干次传送后，完成
							if (message.indexOf("over") != -1) {
								break BIORead;
							}
						}
					} catch (SocketTimeoutException e) {
						// 执行到这里，说明本次read没有接收到任何数据流
						System.out.println("这次没有从底层接收到任务数据报文，等待10毫秒，模拟事件Y的处理时间");
						continue;
					}
				}
				System.out.println("服务器收到来自于端口：" + sourcePort + "的信息：" + message);
				out.write("回发响应信息！".getBytes());
				out.close();
                in.close();
                socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				serverSocket.close();
			}
		}
	}
	
}
