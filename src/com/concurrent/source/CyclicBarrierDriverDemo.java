package com.concurrent.source;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * CyclicBarrier用法实例
 * 有一组工作者需要完成一组工作。工作之前需要等待所有工作者全部准备好，准备好以后所有工作者开始工作。
 * 
 * @version 1.0
 */
public class CyclicBarrierDriverDemo {

	public static void main(String[] args) {
		Integer N = 5;
		Runnable barrierAction = new Runnable() {
			public void run() {
				System.out.println("工作者全部准备好了，可以开始工作了。");
			}
		};
		
		CyclicBarrier cyclicBarrier = new CyclicBarrier(N, barrierAction);
		for (int i = 0; i < N; i++) {
			new CyclicBarrierWorker(cyclicBarrier, i).start();
		}
	}
	
}

class CyclicBarrierWorker extends Thread {
	CyclicBarrier barrier;
	private Integer i;
	
	public CyclicBarrierWorker(CyclicBarrier barrier, Integer i) {
		this.barrier = barrier;
		this.i = i;
	}
	
	public void run() {
		try {
			Random random = new Random();
			Integer prepare = random.nextInt(1000);
			Thread.sleep(prepare);
			System.out.println("工作者" + i + "准备好了，准备时间 " + prepare + " 毫秒。");
			barrier.await();
			Integer work = random.nextInt(1000);
			Thread.sleep(work);
			System.out.println("工作者" + i + "工作完成，工作时间 " + work + " 毫秒。");
		} catch (Exception e) {

		}
	}
	
}
