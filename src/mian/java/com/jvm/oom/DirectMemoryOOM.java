package com.jvm.oom;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * 
 * 本机直接内存溢出
 * java.lang.OutOfMemoryError
 * 
 * -Xmx20M
 * -XX:MaxDirectMemoryOOM=10M
 * 
 * @version 1.0
 */
public class DirectMemoryOOM {

	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe) unsafeField.get(null);
		while(true) {
			unsafe.allocateMemory(_1MB);
		}
	}
	
}
