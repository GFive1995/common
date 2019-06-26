package com.communication.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * 服务端：阻塞IO
 * 
 * @version 1.0
 * @date 2019年6月25日 下午1:46:10
 */
public class SocketServer1 {

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(83);
		try {
			while(true) {
				Socket socket = serverSocket.accept();
				
				// 接收信息
				InputStream in = socket.getInputStream();
				OutputStream out = socket.getOutputStream();
				Integer sourcePort = socket.getPort();
				int maxLen = 2048;
				byte[] contextBytes = new byte[maxLen];
				// 阻塞，直到有数据准备好
				int realLen = in.read(contextBytes, 0, maxLen);
				// 读取信息
				String message = new String(contextBytes, 0, realLen);
				// 打印信息
				System.out.println("服务器收到来自于端口："+sourcePort+"的信息："+message);
				out.write("回发响应信息！".getBytes());
				
				// 关闭
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
