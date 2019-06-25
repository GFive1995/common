package com.thread;

/**
 * 
 * 创建线程：实现Runnable
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 上午10:01:19
 */
public class RunnableDemo implements Runnable {

	public void run() {
		System.out.println("线程名称："+Thread.currentThread().getName()+"-----线程Id："+Thread.currentThread().getId());
	}

	public static void main(String[] args) {
		new Thread(new RunnableDemo()).start();
	}
	
}
