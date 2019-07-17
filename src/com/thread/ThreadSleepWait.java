package com.thread;

/**
 * 
 * sleep()、wait() 比较分析
 * sleep():使当前执行的线程休眠（暂时停止执行）指定的毫秒数，线不会丢失任何监视器的所有权。
 * wait():使当前线程等待，直到另一个线程调用notify()或notifyAll()方法。
 * 			当前线程必须拥有此对象的监视器。
 * 			线程释放此监视器的所有权并等待另一个线程通知等待此对象监视器唤醒的线程通过调用@code notify方法或@code notifyall方法。
 * 			然后线程等待，直到它可以重新获得监视器的所有权并继续执行。
 * 
 * @version 1.0
 */
public class ThreadSleepWait {

	public static void main(String[] args) throws InterruptedException {
		final Object sleep = new Object();
		Thread t1 = new Thread() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+"(sleep)-----开始");
				synchronized (sleep) {
					try {
						sleep(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName()+"(sleep)-----结束");
			}
		};
		t1.setName("线程1");
		t1.start();
		
		final Object wait = new Object();
		Thread t2 = new Thread() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+"(wait)-----开始");
				synchronized (wait) {
					try {
						wait.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName()+"(wait)-----结束");
			}
		};
		Thread t3 = new Thread() {
			public void run() {
				synchronized (wait) {
					wait.notify();	// 只有调用notify之后，线程2才会进入运行状态
				}
			}
		};
		t2.setName("线程2");
		t3.setName("线程3");
		t2.start();
		Thread.sleep(5);
		t3.start();
	}
	
}
