package com.concurrent.source;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 
 * Semaphore用法实例(来自JDK1.8 Semaphore). 
 * 同一时间只能最多只能有五个工作者同时进行工作。
 * 
 * @version 1.0
 */
public class SemaphoreDriverDemo {

	public static void main(String[] args) {
		SemaphorePool semaphorePool = new SemaphorePool();
		for (int i = 0; i < 20; i++) {
			SemaphoreWorker semaphoreWorker = new SemaphoreWorker(semaphorePool);
			semaphoreWorker.setName("工作者" + i);
			semaphoreWorker.start();
		}
	}
}

class SemaphoreWorker extends Thread {

	private SemaphorePool semaphorePool;

	public SemaphoreWorker(SemaphorePool semaphorePool) {
		this.semaphorePool = semaphorePool;
	}

	public void run() {
		try {
			/**
			 * 获取一个许可，最多获取5个许可
			 */
			Object object = semaphorePool.getItem();
			System.out.println(Thread.currentThread().getName() + "：开始工作-----当前时间：" + getFormatTimeStr());
			TimeUnit.SECONDS.sleep(2);
			/**
			 * 释放一个许可
			 * 如果不释放，那么其他线程无法获取许可将无法进行工作
			 */
			semaphorePool.putItem(object);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getFormatTimeStr() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sf.format(new Date());
	}

}


/**
 * 
 * 使用信号量来控制对一个池的访问
 * 
 * @version 1.0
 */
class SemaphorePool {

	private static final int MAX_AVAILABLE = 5;
	private final Semaphore available = new Semaphore(5, true);

	/**
	 * 
	 * 方法描述:获取一个许可
	 * 
	 * @return
	 * @throws InterruptedException
	 * 
	 */
	public Object getItem() throws InterruptedException {
		available.acquire();
		return getNextAvailAbleItem();
	}

	/**
	 * 
	 * 方法描述:释放一个许可
	 * 
	 * @param x
	 * 
	 */
	public void putItem(Object x) {
		if (markAsUnused(x)) {
			available.release();
		}
	}

	protected Object[] items = { "object_1", "object_2", "object_3", "object_4", "object_5" };
	protected boolean[] used = new boolean[MAX_AVAILABLE];

	protected synchronized Object getNextAvailAbleItem() {
		for (int i = 0; i < MAX_AVAILABLE; i++) {
			if (!used[i]) {
				used[i] = true;
				return items[i];
			}
		}
		return null;
	}

	protected synchronized boolean markAsUnused(Object item) {
		for (int i = 0; i < MAX_AVAILABLE; i++) {
			if (item == items[i]) {
				if (used[i]) {
					used[i] = false;
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

}
