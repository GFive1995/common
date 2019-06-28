package com.thread.atomicity;

/**
 * 
 * synchronized自增
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月28日 上午11:36:10
 */
public class AtomicitySynchronized {

	private int count = 0;
	private synchronized void increase() {
		count++;
	}
	
	public static void main(String[] args) {
		final AtomicitySynchronized atomicitySynchronized = new AtomicitySynchronized();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 1000; j++) {
						atomicitySynchronized.increase();
					}
				}
			}).start();;
		}
		if (Thread.activeCount() > 1) {
			Thread.yield();
		}
		System.out.println(atomicitySynchronized.count);
	}
	
}
