package com.designpattern.creative.single;

/**
 * 
 * 懒汉式单例类
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月4日 上午10:15:19
 */
public class LazySingleton {

	private static LazySingleton INSTANCE = null;
	
	private LazySingleton() {
		
	}
	
	public static LazySingleton getInstance() {
		if (INSTANCE == null) {
			synchronized (LazySingleton.class) {
				INSTANCE = new LazySingleton();
			}
		}
		return INSTANCE;
	}
}
