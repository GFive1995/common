package com.designpattern.single;

/**
 * 
 * 饿汉式单例类
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年6月4日 上午10:12:05
 */
public class EagerSingleton {

	private static final EagerSingleton INSTANCE = new EagerSingleton();
	
	private EagerSingleton() {
		
	}
	
	public static EagerSingleton getInstance() {
		return INSTANCE;
	}
	
}
