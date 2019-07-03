package com.thread.atomicity;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * lock自增
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
		final AtomicityLock atomicityLock = new AtomicityLock();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 1000; j++) {
						atomicityLock.increase();
					}
				}
			}).start();
		}
		while (Thread.activeCount() > 1) {
			Thread.yield();
		}
		System.out.println(atomicityLock.count);
	}
	
}
