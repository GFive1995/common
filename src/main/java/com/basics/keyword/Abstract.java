package com.basics.keyword;

/**
 * 
 * 抽象类
 * 
 * @version 1.0
 */
public abstract class Abstract {

	/**
	 * 抽象方法可以包含常量，变量，静态成员，构造方法，方式实现
	 */
	private final String ATTRIBUTE_A = "ATTRIBUTE_A";
	private String ATTRIBUTE_B;
	private static String ATTRIBUTE_C;
	
	public Abstract() {}
	
	public void FunctionA() {
		System.out.println("抽象类可以有方法实现");
	}
	
	/**
	 * 
	 * 方法声明必须抽象的
	 * public void FunctionC();	不行
	 *	
	 */
	public abstract void FunctionB();
	
}

