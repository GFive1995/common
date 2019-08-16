package com.jvm.gc;

/**
 * 
 * 模拟老年代GC触发情况
 * 老年代可用空间小于新生代存活对象所占空间，没有开启空间担保参数，触发FullGC。
 * 1、新生代大小10M，Eden大小8M，from大小1M，to大小1M，XX:PretenureSizeThreshold=3145728(大于3M对象直接进入老年代)。
 * 2、创建一个4M的数组，这个对象直接进入老年代，然后这个对象不再被引用。
 * 3、创建三个2M的数组，一个128K的数组，此时Eden区域大约7M多。
 * 4、创建一个2M的数组，Eden区域放不下，触发YoungGC。
 * 5、此时Eden区域7M多内存加上Old区域4M内存，Old区域放不下，触发FullGC。
 * 
 * JVM参数设置(JDK1.8)
 * -XX:InitialHeapSize=20971520			
 * -XX:MaxHeapSize=20971520				
 * -XX:NewSize=10485760					
 * -XX:MaxNewSize=10485760				
 * -XX:SurvivorRatio=8					
 * -XX:MaxTenuringThreshold=15			
 * -XX:PretenureSizeThreshold=3145728	
 * -XX:+UseParNewGC					
 * -XX:+UseConcMarkSweepGC				
 * -XX:+PrintGCDetails					
 * -XX:+PrintGCTimeStamps				
 * -Xloggc:gc.log	
 * @version 1.0
 */
public class GCDemo_06 {

	public static void main(String[] args) {
		byte[] array_1 = new byte[4 * 1024 * 1024];
		array_1 = null;
		
		byte[] array_2 = new byte[2 * 1024 * 1024];
		byte[] array_3 = new byte[2 * 1024 * 1024];
		byte[] array_4 = new byte[2 * 1024 * 1024];
		byte[] array_5 = new byte[128 * 1024];
		
		byte[] array_6 = new byte[2 * 1024 * 1024];
	}
	
}
