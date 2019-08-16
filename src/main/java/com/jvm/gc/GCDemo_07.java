package com.jvm.gc;

/**
 * 
 * 模拟老年代GC触发情况
 * 老年代可用空间小于新生代平均进入老年代的大小，提前触发Full GC
 * 2、第一次YoungGC
 * 		1.创建一个3M数组array_1，array_1指向第二个3M数组，原来指向的数组可以被GC清除。
 * 		2.创建一个3M数组array_2，Eden区域大小不够，引发YoungGC。
 * 		3.array_1的3M数组进入老年代。
 * 		4.此时老年代使用空间3M：一个array_1的3M数组。
 * 3、第二次YoungGC
 * 		1.array_2指向另一个3M数组，原来向的数组可以被GC清除。
 * 		2.创建一个3M数组array_3，Eden区域大小不够，引发YoungGC。
 * 		3.array_2的3M数组进入老年代。
 * 		4.此时老年代使用空间6M：一个array_1的3M数组，一个array_2的3M数组。
 * 4、第三次YoungGC
 * 		1.array_3指向另一个3M数组，原来指向的数组可以被GC清除。
 * 		2.创建一个3M数组array_4，Eden区域大小不够，引发YoungGC。
 * 		3.array_3的3M数组进入老年代。
 * 		4.此时老年代使用空间9M：一个array_1的3M数组，一个array_2的3M数组，一个array_3的3M数组。
 * 5、第四次YoungGC引发FullGC
 * 		1.将array_1、array_2、array_3，array_4，array_5设置为null，表示可以被GC清除。
 * 		2.创建一个3M数组array_5，一个1M数组，一个512K数组，两个256K数组。
 * 		3.此时Eden区域内存大小不够，引发YoungGC。
 * 		4.此时老年代使用空间9M，老年代老年代剩余空间1M，历次进入老年代平均大小3M，老年代可以空间小于新生代平均进入老年代大小，提前触发FullGC。
 *
 * JVM参数设置(JDK1.8)
 * -XX:InitialHeapSize=20971520			
 * -XX:MaxHeapSize=20971520				
 * -XX:NewSize=10485760					
 * -XX:MaxNewSize=10485760				
 * -XX:SurvivorRatio=8					
 * -XX:MaxTenuringThreshold=15			
 * -XX:PretenureSizeThreshold=5242880	
 * -XX:+UseParNewGC					
 * -XX:+UseConcMarkSweepGC				
 * -XX:+PrintGCDetails					
 * -XX:+PrintGCTimeStamps				
 * -Xloggc:gc.log	
 * @version 1.0
 */
public class GCDemo_07 {

	public static void main(String[] args) {
		byte[] array_1 = new byte[3 * 1024 * 1024];
		array_1 = new byte[3 * 1024 * 1024];
		
		byte[] array_2 = new byte[3* 1024 * 1024];
		array_2 = new byte[3* 1024 * 1024];
		
		byte[] array_3 = new byte[3* 1024 * 1024];
		array_3 = new byte[3* 1024 * 1024];
	
		byte[] array_4 = new byte[3* 1024 * 1024];
		
		array_1 = null;
		array_2 = null;
		array_3 = null;
		array_4 = null;
		byte[] array_5 = new byte[3* 1024 * 1024];
		array_5 = null;
		byte[] array_6 = new byte[1* 1024 * 1024];
		array_6 = new byte[512 * 1024];
		array_6 = new byte[256 * 1024];
		array_6 = new byte[256 * 1024];
	}
	
}
