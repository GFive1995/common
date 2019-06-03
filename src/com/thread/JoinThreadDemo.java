package com.thread;

/**
 * 
 * 线程按顺序执行
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月3日 上午10:18:19
 */
public class JoinThreadDemo {
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			JoinThread joinThread = new JoinThread();
			joinThread.setName("线程" + i);
			joinThread.start();
			joinThread.join();	
		}
	}
	
}

class JoinThread extends Thread {
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread());
	}
	
}
