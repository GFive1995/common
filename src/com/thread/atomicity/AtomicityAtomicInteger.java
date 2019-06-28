package com.thread.atomicity;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * AtomicInteger自增
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月28日 上午11:42:35
 */
public class AtomicityAtomicInteger {

	private AtomicInteger count = new AtomicInteger();
	private void increase() {
		count.getAndIncrement();
	}
	
	public static void main(String[] args) {
		final AtomicityAtomicInteger atomicityAtomicInteger = new AtomicityAtomicInteger();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 1000; j++) {
						atomicityAtomicInteger.increase();
					}
				}
			}).start();
		}
		while(Thread.activeCount() > 1) {
			Thread.yield();
		}
		System.out.println(atomicityAtomicInteger.count);
	}
	
}
