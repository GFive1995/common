package com.jvm.gc;

/**
 * 
 * 查看JVM运行时多少内存是系统内存
 * 查看日志得出大约1M内存是系统内存(很重要，后面分析使用)
 * 
 * JVM参数设置(JDK1.8)
 * -XX:InitialHeapSize			初始化堆大小
 * -XX:MaxHeapSize				最大堆大小
 * -XX:NewSize					初始新生代大小
 * -XX:MaxNewSize				最大新生代大小
 * -XX:SurvivorRatio			新生代Eden和Survivor的比例
 * -XX:MaxTenuringThreshold		动态年龄判定阈值
 * -XX:PretenureSizeThreshold	大对象阈值
 * -XX:+UseParNewGC				新生代使用ParNew垃圾收集器
 * -XX:+UseConcMarkSweepGC		老年代使用CMS垃圾收集器
 * -XX:+PrintGCDetails			打印详细的GC日志
 * -XX:+PrintGCTimeStamps		打印每次GC发生的时间
 * -Xloggc:gc.log				将GC日志写入一个磁盘文件
 * 
 * @version 1.0
 */
public class GCDemo_00 {

	public static void main(String[] args) {
		
	}
	
}
