package com.thread.pool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * ThreadPoolExecutor
 * 可以通过这个线程池中的submit()方法或者execute()方法，执行所有实现了Runnable接口或者Callable接口的任务
 * 对于这些任务的执行是立即的、一次性的
 * 
 * @version 1.0
 */
public class ThreadPoolExecutorSimple {

	public static void main(String[] args) {
		// corePoolSize		要保留在池中的线程数，即使它们是空闲的
		// maximumPoolSize	池中允许的最大线程数
		// keepAliveTime	当线程数大于核心时，这是多余的空闲线程在终止前等待新任务的最长时间
		// unit				单位时间的单位
		// workQueue		工作队列用于在执行任务之前保存任务的队列
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());
		for (int i = 0; i < 10; i++) {
			threadPoolExecutor.submit(new ThreadPoolExecutorSimple.TestRunnable(i));
		}
	}
	
	private static class TestRunnable implements Runnable {

		private int index;
		
		public TestRunnable(int index) {
			this.index = index;
		}
		
		public void run() {
			Thread currentThread = Thread.currentThread();
			System.out.println("线程：" + currentThread.getId() + " 中的任务（" + index + "）开始执行");
			synchronized (currentThread) {
				try {
					Thread.sleep(index * 500);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println("线程：" + currentThread.getId() + " 中的任务（" + index + "）执行完成");
		}
	}
}
