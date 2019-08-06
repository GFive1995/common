package com.basics;

/**
 * 
 * final
 * 1、基本数据类型
 * 		对于基本数据类型final使数值恒定不变。
 * 2、引用类型
 * 		表示引用的地址是不能改变的。
 * 3、方法
 * 		可以把方法锁住，防止任何继承类修改它的含义。
 * 		统一编译器将针对该方法的所有调用都转换为内嵌调用。
 * 		所有private方法隐式地指为final。
 * 4、类
 * 		表示此类不能被继承。(String)
 * 
 * @version 1.0
 */
public class Final {

	private final int AttRIBUTE_A = 0;
	private final String ATTRIBUTR_B = "final";
	/**
	 * static + final 表示只占据一段不能改变的存储空间。
	 */
	private static final String ATTRIBUTE_C = "staticFinal";
	
	public final void Method() {
		System.out.println("final Method");
	}
	
	final class Class {
		
	}
	
	public static void main(String[] args) {
		/**
		 * 参数列表中声明final，意味着无法在方法中更改参数引用所指向的对象。(主要用来向匿名内部类传递数据)
		 */
		final String attributeC = "匿名内部类";
		new Thread(new Runnable() {
			public void run() {
				System.out.println(attributeC);
			}
		}).start();
	}
	
}
