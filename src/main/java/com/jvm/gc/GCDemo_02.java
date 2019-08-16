package com.jvm.gc;

/**
 * 
 * 对象进入老年的四种情况(1)
 * 模拟通过动态年龄判定规则进入老年代：如果Survivor区域 年龄1+...+年龄n 对象总和大于Survivor区的50%，此时年龄n以上的对象会进入老年代，不一定要达到15岁。
 * 1、新生代大小20M，Eden大小8M，from大小1M，to大小1M，JVM系统使用内存大小1M(GCDemo_00得知)。
 * 2、第一次YoungGC
 * 		1.创建三个2M的数组，然后将其设置为null，表示其可以被GC回收。
 * 		2.创建一个128K的数组，这是通过动态年龄规则进入老年代的内存。
 * 		3.此时Eden内存大小 3*2M + 1M + 128K = 7M 多一点。
 * 		4.创建一个2M的数组，Eden内存不够将触发YoungGC。
 * 3、第一次YoungGC后内存情况
 * 		1.Eden区域有一个2M的数组(array_3)。
 * 		2.from区域有一个128K的数组(array_2)和JVM系统使用的一部分内存一共677K，占比66%，超过了50%。
 * 		3.Old区域为空。
 * 4、第二次YoungGC
 * 		1.创建两个2M的数组，然后将其设置为null，表示其可以被GC回收。
 * 		2.此时Eden内存大小 2M + 2M*2 + 1M = 7M 多一点。
 * 		3.创建一个2M的数组，Eden内存不够将触发YoungGC。
 * 5、第二次YoungGC后内存情况
 * 		1.Eden区域有一个2M的数组(array_4)。
 * 		2.from区域为空。根据动态年龄判定规则进入老年代。
 * 		3.Old区域有一个128K的数组(array_2)和JVM系统使用的一部分内存一共677K。
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
public class GCDemo_02 {

	public static void main(String[] args) {
		/**
		 * 创建三个2M大小的数组
		 * 将array_1设置为null
		 */
		byte[] array_1 = new byte[2 * 1024 * 1024];
		array_1 = new byte[2 * 1024 * 1024];
		array_1 = new byte[2 * 1024 * 1024];
		array_1 = null;
		/**
		 * 创建一个128k大小的数组
		 */
		byte[] array_2 = new byte[128 * 1024];
		/**
		 * 创建一个2M大小的数组
		 * Eden区域内存不够发生GC
		 */
		byte[] array_3 = new byte[2 * 1024 * 1024];
		/**
		 * 创建两个2M大小的数组，一个128K大小的数组
		 * 将array_3设置为null
		 */
		array_3 = new byte[2 * 1024 * 1024];
		array_3 = new byte[2 * 1024 * 1024];
		array_3 = null;
		/**
		 * 创建一个2M大小的数组
		 * Eden区域内存不够发生GC，GC过后的内存加上Survivor的内存大于Survivor内存的50%，将Survivor内存数据放入老年代
		 */
		byte[] array_4 = new byte[2 * 1024 * 1024];
	}
	
}
