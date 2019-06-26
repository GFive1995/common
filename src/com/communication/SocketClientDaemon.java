package com.communication;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * 客户端
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 下午1:47:41
 */
public class SocketClientDaemon {

	public static void main(String[] args) throws InterruptedException {
		Integer clientNumber = 20;
		CountDownLatch countDownLatch = new CountDownLatch(clientNumber);
		for (int i = 0; i < clientNumber; i++, countDownLatch.countDown()) {
			SocketClientRequestThread client = new SocketClientRequestThread(countDownLatch, i);
			new Thread(client).start();
		}
		
		synchronized (SocketClientDaemon.class) {
            SocketClientDaemon.class.wait();
        }
	}
	
}
