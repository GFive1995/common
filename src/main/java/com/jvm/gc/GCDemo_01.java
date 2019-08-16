package com.jvm.gc;

/**
 * 
 * 模拟发生YoungGC
 * 1、新生代大小5M，Eden大小4M，from大小512K，to大小512K，JVM系统使用内存大小1M(GCDemo_00得知)。
 * 2、创建三个1M的数组，然后将其设置为null，表示其可以被GC回收。
 * 3、此时Eden区域4M大小全部被使用，新建一个1M的数组，Eden内存不够将触发YoungGC。
 * 
 * JVM参数设置(JDK1.8)
 * -XX:InitialHeapSize=10485760			
 * -XX:MaxHeapSize=10485760				
 * -XX:NewSize=5242880					
 * -XX:MaxNewSize=5242880				
 * -XX:SurvivorRatio=8					
 * -XX:MaxTenuringThreshold=15			
 * -XX:PretenureSizeThreshold=10486750	
 * -XX:+UseParNewGC					
 * -XX:+UseConcMarkSweepGC				
 * -XX:+PrintGCDetails					
 * -XX:+PrintGCTimeStamps				
 * -Xloggc:gc.log						
 * 
 * @version 1.0
 */
public class GCDemo_01 {

	public static void main(String[] args) {
		/**
		 * 创建三个1M大小的数组
		 * 将array_1设置为null
		 */
		byte[] array_1 = new byte[1024 * 1024];
		array_1 = new byte[1024 * 1024];
		array_1 = new byte[1024 * 1024];
		array_1 = null;
		/**
		 * 创建1M大小的数组
		 * Eden区域内存不够发生GC
		 */
		byte[] array_2 = new byte[1024 * 1024];
	}
	
}
