package com.thread;

/**
 * 
 * 中断信号Interrupt
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月25日 上午10:42:19
 */
public class InterruptDemo {

	public static void main(String[] args) throws InterruptedException {
		// 模拟正常运行发出中断信号
		Thread threadOne = new Thread(new Runnable() {
			public void run() {
				Thread currentThread = Thread.currentThread();
				while (!currentThread.isInterrupted()) {
					System.out.println("Thread One 正在运行!");
				}
				System.out.println("Thread One 正常结束!" + currentThread.isInterrupted());
			}
		});
		// 模拟阻塞线程发出中断信号
		Thread threadTwo = new Thread(new Runnable() {
			public void run() {
				Thread currentThread = Thread.currentThread();
				while (!currentThread.isInterrupted()) {
					synchronized (currentThread) {
						try {
							currentThread.wait();
						} catch (Exception e) {
							e.printStackTrace(System.out);
							System.out.println("Thread Two 由于中断信号，异常结束!" + currentThread.isInterrupted());
						}
					}
				}
				System.out.println("Thread Two 正常结束!" + currentThread.isInterrupted());
			}
		});
		
		threadOne.start();
		threadTwo.start();
		Thread.sleep(2);
		System.out.println("两个线程正常运行，现在开始发出中断信号");
		threadOne.interrupt();
		threadTwo.interrupt();
		
	}
	
}
