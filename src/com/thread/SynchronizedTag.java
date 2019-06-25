package com.thread;


public class SynchronizedTag {

	static {
		synchronized (SynchronizedTag.class) {
			
		}
	}
	
	// 和synchronized(this){}的意义相同
	// 对拥有这个方法的对象进行对象锁状态检查
	public synchronized void methodOne() {
		
	}
	
	// 和synchronized(Class.class)的意义相类似
	// 对拥有这个方法的类的对象进行对象锁检查(类本身也是一个对象)
	public synchronized static void methodTwo() {
		
	}
	
	public static void methodThree() {
		synchronized (SynchronizedTag.class) {
			
		}
	}
	
	public void methodFour() {
		synchronized (SynchronizedTag.class) {
			
		}
	}
	
}
