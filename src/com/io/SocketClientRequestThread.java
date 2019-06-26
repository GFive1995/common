package com.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * 客户端多线程调用
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 下午1:48:06
 */
public class SocketClientRequestThread implements Runnable {

	private CountDownLatch countDownLatch;
	private Integer clientIndex;
	
	public SocketClientRequestThread(CountDownLatch countDownLatch, Integer clientIndex) {
		this.countDownLatch = countDownLatch;
		this.clientIndex = clientIndex;
	}
	
	public void run() {
		Socket socket = null;
		OutputStream clientRequest = null;
		InputStream clientResponse = null;
		try {
			socket = new Socket("localhost", 83);
			clientRequest = socket.getOutputStream();
			clientResponse = socket.getInputStream();
			// 等待，知道SocketClientDaemon完成所有线程的启动，然后所有线程一起发送请求
			this.countDownLatch.await();
			
			// 发送请求信息
			clientRequest.write(("这是第" + clientIndex + "个客户端的请求。").getBytes());
			clientRequest.flush();
			
			// 在这里等待，知道服务器返回信息
			System.out.println("第" + clientIndex + "个客户端的请求发送完成，等待服务器返回信息");
			int maxLen = 1024;
			byte[] contextBytes = new byte[maxLen];
			int realLen;
			String message = "";
			// 等待服务器返回信息
			while((realLen = clientResponse.read(contextBytes, 0, maxLen)) != -1) {
				message += new String(contextBytes, 0, realLen);
			}
			System.out.println("接收到来自服务器的信息：" + message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (clientRequest != null) {
					clientRequest.close();
				}
				if (clientResponse != null) {
					clientResponse.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
