package com.concurrent.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
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
		
		// 创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE，根据需要创建新线程，但将重用以前构造的线程可用。
		Executors.newCachedThreadPool();
		new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		
		// 创建重用固定数量线程的线程池在共享的无边界队列上操作。
		Executors.newFixedThreadPool(5);
		new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		// 创建使用单个工作线程操作的执行器从一个没有边界的队列中。
		Executors.newSingleThreadExecutor();
		new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		
		// 创建一个线程池，该线程池可以安排命令在给予延迟，或定期执行。
		Executors.newScheduledThreadPool(5);
//		new ThreadPoolExecutor(5, Integer.MAX_VALUE, 0, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), Executors.defaultThreadFactory(), new AbortPolicy());
		
		// 创建可调度命令的单线程执行器在给定的延迟之后运行，或定期执行。
		Executors.newSingleThreadScheduledExecutor();
//		new ThreadPoolExecutor(1, Integer.MAX_VALUE, 0, TimeUnit.NANOSECONDS, new DelayedWorkQueue(), Executors.defaultThreadFactory(), new AbortPolicy());
		
		// 创建持有足够线程的线程池来支持给定的并行级别，并通过使用多个队列，减少竞争，它需要穿一个并行级别的参数，如果不传，则被设定为默认的CPU数量。
		Executors.newWorkStealingPool();
		new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
		
		// corePoolSize		要保留在池中的线程数，即使它们是空闲的
		// maximumPoolSize	池中允许的最大线程数
		// keepAliveTime	当线程数大于核心时，这是多余的空闲线程在终止前等待新任务的最长时间
		// unit				单位时间的单位
		// workQueue		工作队列用于在执行任务之前保存任务的队列
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());
		for (int i = 0; i < 10; i++) {
			threadPoolExecutor.submit(new ThreadPoolExecutorSimple.TestRunnable(i));
			System.out.println("线程池中线程数目："+threadPoolExecutor.getPoolSize()+"，队列中等待执行的任务数目："+
					threadPoolExecutor.getQueue().size()+"，已执行玩别的任务数目："+threadPoolExecutor.getCompletedTaskCount());
		}
		threadPoolExecutor.shutdown();
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
