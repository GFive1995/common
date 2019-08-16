package com.jvm.gc;

/**
 * 
 * 对象如何进入老年
 * 模拟大对象直接进入老年代(-XX:PretenureSizeThreshold=5242880)
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
 * 
 * @version 1.0
 */
public class GCDemo_05 {

	public static void main(String[] args) {
		byte[] array_2 = new byte[5 * 1024 * 1024];
	}
	
}
