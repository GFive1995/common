package com.thread;

/**
 * 
 * 创建线程：集成Thread
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 上午10:00:32
 */
public class ThreadDemo extends Thread {

	public void run() {
		System.out.println("线程名称："+this.getName()+"-----线程Id："+this.getId());
	}
	
	public static void main(String[] args) {
		new ThreadDemo().start();
	}
	
}
