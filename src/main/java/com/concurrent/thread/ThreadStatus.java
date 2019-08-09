package com.concurrent.thread;

/**
 * 
 * 线程状态
 * NEW(初始):尚未启动的线程的线程状态。
 * RUNNABLE(运行):可运行线程的线程状态。
 * BLOCKED(阻塞):线程的线程状态被阻止，正在等待监视器锁。
 * WAITING(等待):等待线程的线程状态。
 * TIMED_WAITING(超时等待):具有指定等待时间的等待线程的线程状态。
 * TERMINATED(终止):终止线程的线程状态。
 * 
 * @version 1.0
 */
public class ThreadStatus {
	
	private static Object resource1 = "资源1";
	private static Object resource2 = "资源2";
	
	public static void main(String[] args) throws InterruptedException {
		ThreadStatusDemo t1 = new ThreadStatusDemo(resource1, resource2);
		t1.setName("线程1");
		System.out.println("线程名称："+t1.getName() + "-----线程状态："+ t1.getState());
		t1.start();
		System.out.println("线程名称："+t1.getName() + "-----线程状态："+ t1.getState());

		ThreadStatusDemo t2 = new ThreadStatusDemo(resource2, resource1);
		t2.setName("线程2");
		t2.start();
		Thread.sleep(1000);
		System.out.println("线程名称："+t2.getName() + "-----线程状态："+ t2.getState());
		Thread.sleep(3000);
		System.out.println("线程名称："+t1.getName() + "-----线程状态："+ t1.getState());
		
		final Object lock = new Object();
		Thread t3 = new Thread() {
			public void run() {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						
					}
				}
			}
		};
		Thread t4 = new Thread() {
			public void run() {
				synchronized (lock) {
					lock.notifyAll();
				}
			}
		};
		t3.setName("线程3");
		t4.setName("线程4");
		t3.start();
		t4.start();
		System.out.println("线程名称："+t3.getName() + "-----线程状态："+ t3.getState());
		Thread.sleep(1000);
		System.out.println("线程名称："+t3.getName() + "-----线程状态："+ t3.getState());
	}
	
}

class ThreadStatusDemo extends Thread {
	
	private Object resource1;
	private Object resource2;
	
	public ThreadStatusDemo(Object resource1, Object resource2) {
		this.resource1 = resource1;
		this.resource2 = resource2;
	}
	
	public void run() {
		synchronized (resource1) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (resource2) {

			}
		}
	}
	
}
