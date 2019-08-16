package com.jvm.gc;

/**
 * 
 * 模拟躲过指定年龄阈值进入老年代(XX:MaxTenuringThreshold=5，表示5次GC后进入老年代)
 * 1、新生代大小20M，Eden大小6M，from大小2M，to大小2M，JVM系统使用内存大小1M(GCDemo_00得知)。
 * 2、第一次GC
 * 		1.创建两个2M的数组，然后将其设置为null，表示其可以被GC回收。
 * 		2.创建一个128K的数组，这是躲过指定年龄阈值进入老年代的对象。
 * 		3.此时Eden内存大小 2*2M + 1M + 128K = 5M 多一点。
 * 		4.创建一个1M的数组，Eden内存不够将触发YoungGC。
 * 3、第二次GC
 * 		1.创建4个1M的数组，然后将其设置为null，表示其可以被GC回收。
 * 		2.此时Eden区域内存大小 4*1M + 1M = 5 M 多一点。
 * 		4.创建一个1M的数组，Eden内存不够将触发YoungGC。
 * 4、第三次GC。
 * 5、第四次GC。
 * 6、第五次GC。
 * 7、第六次GC。此时128K的数组(array_2)和JVM系统使用的一部分内存一共677K躲过了指定年龄阈值进入老年代。
 * 		
 * JVM参数设置(JDK1.8)
 * -XX:InitialHeapSize=20971520
 * -XX:MaxHeapSize=20971520
 * -XX:NewSize=10485760
 * -XX:MaxNewSize=10485760
 * -XX:SurvivorRatio=3
 * -XX:MaxTenuringThreshold=5
 * -XX:PretenureSizeThreshold=10485760
 * -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCTimeStamps
 * -Xloggc:gc.log
 * -XX:InitialHeapSize=20971520
 * @version 1.0
 */
public class GCDemo_03 {

	public static void main(String[] args) {
		byte[] array_1 = new byte[2 * 1024 * 1024];
		array_1 = new byte[2 * 1024 * 1024];
		array_1 = null;
		
		byte[] array_2 = new byte[128 * 1024];
		
		byte[] array_3 = new byte[1 * 1024 * 1024];
		array_3 = new byte[1 * 1024 * 1024];
		array_3 = new byte[1 * 1024 * 1024];
		array_3 = new byte[1 * 1024 * 1024];
		array_3 = new byte[1 * 1024 * 1024];
		array_3 = null;
		
		byte[] array_4 = new byte[1024 * 1024];
		array_4 = new byte[1 * 1024 * 1024];
		array_4 = new byte[1 * 1024 * 1024];
		array_4 = new byte[1 * 1024 * 1024];
		array_4 = new byte[1 * 1024 * 1024];
		array_4 = null;
		
		byte[] array_5 = new byte[1 * 1024 * 1024];
		array_5 = new byte[1 * 1024 * 1024];
		array_5 = new byte[1 * 1024 * 1024];
		array_5 = new byte[1 * 1024 * 1024];
		array_5 = new byte[1 * 1024 * 1024];
		array_5 = null;

		byte[] array_6 = new byte[1 * 1024 * 1024];
		array_6 = new byte[1 * 1024 * 1024];
		array_6 = new byte[1 * 1024 * 1024];
		array_6 = new byte[1 * 1024 * 1024];
		array_6 = new byte[1 * 1024 * 1024];
		array_6 = null;
		
		
		byte[] array_7 = new byte[1 * 1024 * 1024];
		array_7 = new byte[1 * 1024 * 1024];
		array_7 = new byte[1 * 1024 * 1024];
		array_7 = new byte[1 * 1024 * 1024];
		array_7 = new byte[1 * 1024 * 1024];
		array_7 = null;
		
		byte[] array_8 = new byte[1 * 1024 * 1024];
	}
	
}
