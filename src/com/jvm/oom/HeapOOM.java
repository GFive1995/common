package com.jvm.oom;


import java.util.ArrayList;
import java.util.List;

/**
 * Java堆溢出
 * java.lang.OutOfMemoryError: Java heap space
 * 
 * -Xms20m-Xmx20m			
 * -XX:+HeapDumpOnOutOfMemoryError(JVM出现内存溢出异常时Dump出当前的内存堆转储快照)
 * 
 * @version 1.0
 */

public class HeapOOM {
	
	static class OOMObject {
		
	}
	
	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<OOMObject>();
		while(true) {
			list.add(new OOMObject());
		}
	}

}
