package com.thread.atomicity;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * ReentrantLock自增
 * 可重入锁
 * 
 * @version 1.0
 */
public class AtomicityLock {

	private int count = 0;
	Lock lock = new ReentrantLock();
	private void increase() {
		lock.lock();
		try {
			count++;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		Long time = System.currentTimeMillis();
		final AtomicityLock atomicityLock = new AtomicityLock();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 10000000; j++) {
						atomicityLock.increase();
					}
				}
			}).start();
		}
		while (Thread.activeCount() > 1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("运行时间:" + (System.currentTimeMillis() - time));
		System.out.println("ReentrantLock(可重入锁):" + atomicityLock.count);
	}
	
}
