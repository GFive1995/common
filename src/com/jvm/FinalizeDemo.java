package com.jvm;

/**
 * 
 * finalize方法
 * 1、对象可以在被GC时自我拯救
 * 2、这种自救的机会只有一次，因为一个对象的finalise()方法最多会被系统调用一次
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月24日 上午11:00:06
 */
public class FinalizeDemo {

	public static FinalizeDemo Hook = null;
	
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("执行finalize方法");
		FinalizeDemo.Hook = this;
	}
	
	public static void main(String[] args) throws InterruptedException {
		Hook = new FinalizeDemo();
		// 第一次拯救
		Hook = null;
		System.gc();
		Thread.sleep(500);	// 等待finalize执行
		if (Hook !=null) {
			System.out.println("我还活着");
		} else {
			System.out.println("我已经死了");
		}
		// 第二次
		Hook = null;
		System.gc();
		Thread.sleep(500);	// 等待finalize执行
		if (Hook !=null) {
			System.out.println("我还活着");
		} else {
			System.out.println("我已经死了");
		}
	}
}
