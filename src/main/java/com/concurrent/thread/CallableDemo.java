package com.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 
 * 创建线程：实现Callable
 * 
 * @version 1.0
 */
public class CallableDemo implements Callable<String> {

	private String attribute;
	
	public CallableDemo(String attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public String call() throws Exception {
		return attribute;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newCachedThreadPool();
		/**
		 * FutureTask
		 */
		FutureTask<String> futureTask = new FutureTask<>(new CallableDemo("FutureTask"));
		executor.submit(futureTask);
		/**
		 * Future
		 */
		Future<String> future = executor.submit(new CallableDemo("Future"));
		
		System.out.println("FutureTask获取执行结果:" + futureTask.get());
		System.out.println("Future获取执行结果:" + future.get());
	}
	
}
