package com.basics.keyword;

/**
 * 
 * static
 * 1、静态变量
 * 		静态变量在内存中只有一个拷贝，JVM只分配一次内存，可以用类名直接访问。
 * 2、静态方法
 * 		不需要创建对象可以直接调用。
 * 3、静态代码块
 * 		JVM加载类时会按顺序执行这些静态的代码块。
 * 4、静态内部类
 * 		不需要依赖外部类，不能访问外部类非静态属性。
 * 5、静态导包
 * 		JDK1.5新特性。
 * 6、静态常量
 * 		static + final
 * 
 * @version 1.0
 */
public class Static {

	private static String ATTRIBUTE_A = "静态变量";
	private static final String ATTRIBUTE_B = "静态常量";
	
	static {
		System.out.println("静态代码块");
	}
	
	public static void method() {
		System.out.println("静态方法");
	}
	
}
