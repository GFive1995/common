package com.concurrent.thread;

/**
 * 
 * 线程睡眠排序
 * 
 * @version 1.0
 */
public class ThreadSortDemo implements Runnable {

	private int num;
	
	private ThreadSortDemo(int num) {
		this.num = num;
	}
	
	public void run() {
		try {
			Thread.sleep(Integer.valueOf(num));
			System.out.println(num);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int[] nums = {11, 3, 998, 5455, 1, 152, 990};
		for (int i = 0; i < nums.length; i++) {
			new Thread(new ThreadSortDemo(nums[i])).start();
		}
	}

}
