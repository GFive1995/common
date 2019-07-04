package com.thread.atomicity;

/**
 * 
 * volatile自增
 * volatile可以保证可见性。
 * 可见性只能保证每次读取的是最新的值，但是volatile无法保证对变量操作的原子性。
 * 自增操作不是原子性：包括读取变量原始值，进行+1操作，写入内存操作
 * 
 * @version 1.0
 */
public class AtomicityVolatile {

	private volatile int count = 0;
	private void increase() {
		count++;
	}
	
	public static void main(String[] args) {
		final AtomicityVolatile atomicityVolatile = new AtomicityVolatile();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 1000; j++) {
						atomicityVolatile.increase();
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
		System.out.println("volatile:" + atomicityVolatile.count);
	}
	
}
