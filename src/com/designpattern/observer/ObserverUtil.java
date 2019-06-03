package com.designpattern.observer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ObserverUtil {

	private static Observable observable;
	private static ScheduledExecutorService schedulePool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
	
	// 初始化监听器
	static {
		ObserverUtil.observable = new ObserverUtil.CachedObservable();
		observable.registObserver(new JobOne());
		observable.registObserver(new JobTwo());
	}
	
	public static void notifyObservers(final Object obj){
		schedulePool.execute(new Runnable() {
			public void run() {
				try{
					observable.notifyObserver(obj);
				}catch(Exception e){
					System.out.println("###ObserverUtil##notifyObservers#程池发布通知事件执行失败  信息:{}" + e.getMessage());
				}
			}
		});
	}
	
	private static class CachedObservable extends Observable {
		
	}
	
}
