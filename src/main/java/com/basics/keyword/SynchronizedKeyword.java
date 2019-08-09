package com.basics.keyword;

/**
 * 
 * synchronized字段
 * 修饰实例方法：对拥有这个方法的对象进行对象锁状态检查。
 * 修饰静态方法：对拥有这个方法的类对象进行对象锁检查（静态方法是属于类的，不属于当前实例）。
 * 修饰代码块：
 * 
 * @version 1.0
 */
public class SynchronizedKeyword {

	/**
	 * 
	 * 方法描述:修饰实例方法
	 *
	 */
	public synchronized void FunctionA() {
		
	}
	
	/**
	 * 
	 * 方法描述:修饰静态方法
	 *
	 */
	public synchronized static void FunctionB() {
		
	}
	
	
	public void FunctionC() {
		/**
		 * 与修饰普通方法一样
		 */
		synchronized (new Object()) {
			
		}
		
		/**
		 * 与修饰静态方法一样
		 */
		synchronized (SynchronizedKeyword.class) {
			
		}
	}
	
}
