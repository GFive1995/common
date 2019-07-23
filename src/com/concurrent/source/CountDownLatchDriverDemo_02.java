package com.concurrent.source;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 
 * CountDownLatch用法实例(来自JDK1.8 CountDownLatch)
 * 将一个问题分成N个部分，用一个Runnable描述每个部分，该Runnable执行该部分并在锁存器上计数，然后将所有Runnables排队给一个执行器。
 * 当所有子部件都完成时，协调线程将能够通过wait。
 * 
 * @version 1.0
 */
public class CountDownLatchDriverDemo_02 {

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch doneSignal = new CountDownLatch(10);
		Executor executor = Executors.newCachedThreadPool();
		
		for (int i = 0; i < 10; i++) {
			executor.execute(new CountDownLatchWorkerDemo_02(doneSignal, i));
		}
		
		System.out.println("开始工作");
		doneSignal.await();
		System.out.println("工作全部完成");
	}
	
}

class CountDownLatchWorkerDemo_02 implements Runnable {
	private final CountDownLatch doneSignal;
	private final int i;
	
	public CountDownLatchWorkerDemo_02(CountDownLatch doneSignal, int i) {
		this.doneSignal = doneSignal;
		this.i = i;
	}
	
	public void run() {
		try {
			Random random = new Random();
			Integer sleep = random.nextInt(1000);
			Thread.sleep(sleep);
			System.out.println("工人" + i + "开始工作,工作时间 " + sleep + "毫秒，工作完成。");
			doneSignal.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
