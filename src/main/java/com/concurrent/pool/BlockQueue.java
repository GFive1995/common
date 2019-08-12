package com.concurrent.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * 
 * 阻塞队列
 * 
 * 方法/处理方式	抛出异常		返回特殊值		一直阻塞		超时退出
 * 插入方法		add(e)		offer(e)	put(e)		offer(e, time, unit)
 * 移除方法		remove(e)	poll()		take()		poll(time, unit)
 * 检查方法		element()	peek()		不可以		不可以	
 * 		
 * @version 1.0
 */
public class BlockQueue {

	public static void main(String[] args) {
		/**
		 * 数组结构组成的有界阻塞队列
		 */
		ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(5);
		/**
		 * 链表结构组成的有界阻塞队列
		 */
		LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>();
		/**
		 * 支持优先级排序的有界阻塞队列
		 */
		PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
		/**
		 * 使用优先级队列实现的无界阻塞队列
		 */
		DelayQueue delayQueue = new DelayQueue<>();
		/**
		 * 不存储元素的阻塞队列
		 */
		SynchronousQueue<Integer> synchronizedQueue = new SynchronousQueue<>();
		/**
		 * 链表结构组成的无界阻塞队列
		 */
		LinkedTransferQueue<Integer> linkedTransferQueue = new LinkedTransferQueue<>();
		/**
		 * 链表结构组成的双向阻塞队列
		 */
		LinkedBlockingDeque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<>();
	}
	
}
