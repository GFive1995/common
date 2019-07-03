package com.thread;

/**
 * 
 * 死锁Demo
 * 
 * @version 1.0
 */
public class DeadLockDemo {
	private static Object resource1 = "资源1";
	private static Object resource2 = "资源2";
	
	public static void main(String[] args) {
		DeadLockThread deadLockThread1 = new DeadLockThread(resource1, resource2);
		deadLockThread1.setName("线程1");
		deadLockThread1.start();

		DeadLockThread deadLockThread2 = new DeadLockThread(resource2, resource1);
		deadLockThread2.setName("线程2");
		deadLockThread2.start();
	}
	
}

class DeadLockThread extends Thread {
	private Object resource1;
	private Object resource2;
	
	public DeadLockThread(Object resource1, Object resource2) {
		this.resource1 = resource1;
		this.resource2 = resource2;
	}
	
	@Override
	public void run() {
		synchronized (resource1) {
			System.out.println(Thread.currentThread() + "get " + resource1);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() + "waiting get " + resource2);
			synchronized (resource2) {
				System.out.println(Thread.currentThread() + "get " + resource2);
			}
		}
	}
	
}
