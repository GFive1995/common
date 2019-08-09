package com.concurrent.atomicity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * AtomicInteger自增
 * 乐观锁
 * 
 * @version 1.0
 */
public class AtomicityAtomicInteger {

	private AtomicInteger count = new AtomicInteger();
	private void increase() {
		count.getAndIncrement();
	}
	
	public static void main(String[] args) {
		Long time = System.currentTimeMillis();
		final AtomicityAtomicInteger atomicityAtomicInteger = new AtomicityAtomicInteger();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 10000000; j++) {
						atomicityAtomicInteger.increase();
					}
				}
			}).start();
		}
		while(Thread.activeCount() > 1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("运行时间:" + (System.currentTimeMillis() - time));
		System.out.println("AtomicInteger(乐观锁):" + atomicityAtomicInteger.count);
	}
	
}
