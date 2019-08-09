package com.concurrent.thread;

import java.util.Date;

import com.util.DateUtil;

/**
 * 
 * start()、run() 比较分析
 * start():启动一个新线程，新线程会执行相应的run()方法。不能被重复调用。
 * run():	单独调用的话，会在当前线程中执行，并不会启动新线程。
 * 			必须执行完线程才会开始向下执行。
 * 			和普通的成员方法一样，可以被重复调用。
 * 			
 * 
 * @version 1.0
 */
public class ThreadStartRun {

	public static void main(String[] args) throws InterruptedException {
		ThreadStartRunDemo t1 = new ThreadStartRunDemo();
		ThreadStartRunDemo t2 = new ThreadStartRunDemo();
		ThreadStartRunDemo t3 = new ThreadStartRunDemo();
		t1.setName("线程1");
		t2.setName("线程2");
		t3.setName("线程3");
		t1.run();
		t2.start();
		t3.start();
	}
	
}

class ThreadStartRunDemo extends Thread {
	
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName()+"-----开始时间："+DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			Thread.sleep(5000);
			System.out.println(Thread.currentThread().getName()+"-----结束时间："+DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
