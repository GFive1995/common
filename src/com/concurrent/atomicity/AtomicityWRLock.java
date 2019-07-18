package com.concurrent.atomicity;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.google.common.collect.Maps;
import com.util.ObjectUtils;

/**
 * 
 * ReentrantReadWriteLock自增
 * 读写锁
 * 
 * @version 1.0
 */
public class AtomicityWRLock {

	private HashMap<String, Integer> countMap = Maps.newHashMap();
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private WriteLock writeLock = lock.writeLock();
	private ReadLock readLock = lock.readLock();
	
	private int getCount() {
		readLock.lock();
		Integer count = countMap.get("count");
		readLock.unlock();
		return count;
	}
	
	private void increase() {
		writeLock.lock();
		Integer count = ObjectUtils.castInteger(countMap.get("count"), 0);
		count++;
		countMap.put("count", count);
		writeLock.unlock();
	}
	
	public static void main(String[] args) {
		Long time = System.currentTimeMillis();
		final AtomicityWRLock atomicityWRLock = new AtomicityWRLock();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 10000000; j++) {
						atomicityWRLock.increase();
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
		System.out.println("运行时间:" + (System.currentTimeMillis() - time));
		System.out.println("ReentrantReadWriteLock(读写锁):" + atomicityWRLock.getCount());
	}
	
}
