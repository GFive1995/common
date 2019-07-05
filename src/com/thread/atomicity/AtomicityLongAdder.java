package com.thread.atomicity;

import java.util.concurrent.atomic.LongAdder;

/**
 * 
 * LongAdder自增
 * 乐观锁
 * 
 * @version 1.0
 */
public class AtomicityLongAdder {

	private LongAdder count = new LongAdder();
	private void increase() {
		count.increment();
	}
	
	public static void main(String[] args) {
		Long time = System.currentTimeMillis();
		final AtomicityLongAdder atomicityLongAdder = new AtomicityLongAdder();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 10000000; j++) {
						atomicityLongAdder.increase();
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
		System.out.println("LongAdder(乐观锁):" + atomicityLongAdder.count);
	}
	
}
