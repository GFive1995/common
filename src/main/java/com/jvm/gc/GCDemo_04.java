package com.jvm.gc;

/**
 * 
 * 模拟YoungGC后对象因为放不下Survivor区域，直接进入老年代
 * 1、新生代大小20M，Eden大小8M，from大小1M，to大小1M，JVM系统使用内存大小1M(GCDemo_00得知)。
 * 2、第一次GC
 * 		1.创建三个2M的数组，让后让array_1指向第三个数组，表示前两个数组可以被GC回收。
 * 		2.创建一个128的数组，将其设置为null。
 * 		3.此时Eden内存大小 2*3M + 1M + 128K = 7M 多一点。
 * 		4.创建一个2M的数组，Eden内存不够将触发YoungGC。
 * 3、第一次GC后内存情况
 * 		1.Eden区域一个2M的数组(array_3)。
 * 		2.from区域是JVM系统使用的一部分内存。
 * 		3.Old区域一个2M的数组(array_1)。
 * 
 * JVM参数设置(JDK1.8)
 * -XX:InitialHeapSize=20971520
 * -XX:MaxHeapSize=20971520
 * -XX:NewSize=10485760
 * -XX:MaxNewSize=10485760
 * -XX:SurvivorRatio=8
 * -XX:MaxTenuringThreshold=15
 * -XX:PretenureSizeThreshold=10485760
 * -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCTimeStamps
 * -Xloggc:gc.log
 * 
 * @version 1.0
 */
public class GCDemo_04 {

	public static void main(String[] args) {
		byte[] array_1 = new byte[2 * 1024 * 1024];
		array_1 = new byte[2 * 1024 * 1024];
		array_1 = new byte[2 * 1024 * 1024];
		
		byte[] array_2 = new byte[128 * 1024];
		array_2 = null;
		
		byte[] array_3 = new byte[2 * 1024 * 1024];
	}
	
}
