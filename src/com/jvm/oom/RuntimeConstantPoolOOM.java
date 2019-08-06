package com.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 运行时常量池导致内存溢出
 * java.lang.OutOfMemoryError: PermGen space
 * 
 * -XX:PermSize=10M
 * -XX:MaxPermSize=10M
 * 
 * @version 1.0
 */
public class RuntimeConstantPoolOOM {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		int i = 0;
		while(true) {
			list.add(String.valueOf(i++).intern());
		}
	}
	
}
