package com.concurrent.source;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * CountDownLatch用法实例(来自JDK1.8 CountDownLatch)
 * 有一个驾驶员和多个工作者，驾驶员准备好以后工作者才可以开始工作，工作者全部工作完以后进行整理汇报。
 * 对一组工作线程使用两个倒计时锁。
 * 	第一个是一个启动信号(startSignal)，阻止任何工作者继续工作，直到驾驶员准备好继续行驶。
 * 	第二个信号是完成信号(doneSignal)，允许驾驶员等待直到所有工作者都完成。
 * 
 * @version 1.0
 */
public class CountDownLatchDriverDemo_01 {

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(10);
		
		for (int i = 0; i < 10; i++) {
			new Thread(new CountDownLatchWorkerDemo_01(startSignal, doneSignal)).start();
		}
	
		Random random = new Random();
		Integer sleepDrver = random.nextInt(1000);
		Thread.sleep(sleepDrver);
		System.out.println("驾驶员准备了 " + sleepDrver + " 毫秒，已经准备好了。");
		/**
		 * 减少锁的计数，如果计数为零，释放所有线程
		 * 如果注释掉，线程不会继续运行
		 */
		startSignal.countDown();			
		System.out.println("工作者已经准备好了，开始工作。");
		/**
		 * 使当前线程等待，直到锁计数为零
		 * 如果注释掉，不会等到线程运行完就直接运行后续代码
		 */
		doneSignal.await();					
		System.out.println("工作者工作全部完成。");
	}
	
}

class CountDownLatchWorkerDemo_01 implements Runnable {
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;
	
	CountDownLatchWorkerDemo_01(CountDownLatch startSignal, CountDownLatch doneSignal) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
	}

	public void run() {
		try {
			startSignal.await();
			Random random = new Random();
			Integer sleep = random.nextInt(1000);
			Thread.sleep(sleep);
			System.out.println("工作者" + Thread.currentThread().getId() + "开始工作,工作时间 " + sleep + "毫秒，工作完成。");
			doneSignal.countDown();
		} catch (Exception e) {

		}
	}
	
}