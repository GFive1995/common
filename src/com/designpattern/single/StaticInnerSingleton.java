package com.designpattern.single;

/**
 * 
 * 静态内部类单例
 * 
 * @version 1.0
 * @since JDK1.7
 * @author wangcy
 * @company 上海朝阳永续信息技术有限公司
 * @copyright (c) 2019 SunTime Co'Ltd Inc. All rights reserved.
 * @date 2019年6月4日 上午10:26:58
 */
public class StaticInnerSingleton {

	private StaticInnerSingleton() {
		
	}
	
	private static class SingletonHolder {
		private static final StaticInnerSingleton INSTANCE = new StaticInnerSingleton();
	}
	
	public static StaticInnerSingleton getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
}
