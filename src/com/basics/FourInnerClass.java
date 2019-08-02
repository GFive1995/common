package com.basics;

/**
 * 
 * Java 内部类学习
 * 
 * 1、实现多继承
 * 2、可以将一定类组织在一起同时对外界隐藏
 * 
 * @version 1.0
 */
public class FourInnerClass {

	private String privateAttribute = "外部类私有属性";
	protected static String protectedAttribute = "外部类受保护属性(static)";
	public final String publicAttribute = "外部类公开属性(final)";

	private void privateMethod() {
		System.out.println("外部类私有方法");
	}

	protected static void protectedMethod() {
		System.out.println("外部类受保护方法(static)");
	}

	public final void publicMethod() {
		System.out.println("外部类公开方法(final)");
	}

	public static void main(String[] args) {
		/**
		 * 成员内部类依附于外部类存在，创建成员内部类，必须先创建一个外部类的对象。
		 */
		FourInnerClass fourInnerClass = new FourInnerClass();
		fourInnerClass.new MemberInnerClass();
		System.out.println("--------------------");
		
		/**
		 * 匿名内部类
		 * 创建时不需要依附于外部类，不能使用外部类非静态属性和方法
		 */
		new StaticInnerClass();
		System.out.println("--------------------");
		
		/**
		 * 局部内部类
		 * 不可以访问外部类私有属性
		 */
		new LocalClass();
		System.out.println("--------------------");
		
		/**
		 * 匿名内部类
		 */
		new Thread(new Runnable() {
			/**
			 * 匿名内部类可以定义常量
			 * 匿名内部类不能定义常量和静态属性
			 * protected static String attribute = "成员内部类静态属性";
			 * public final attribute1 = "成员内部类常量";
			 */
			private String attribute = "匿名内部类常量";
			
			/**
			 * 匿名内部类必须实现接口或抽象类的所有抽象方法
			 */
			public void run() {
				System.out.println("匿名内部类");
				System.out.println("匿名内部类只能访问外部类的静态属性：" + protectedAttribute);
				protectedMethod();
			}
		}).start();
	}

	/**
	 * 
	 * 成员内部类
	 * 
	 * @version 1.0
	 */
	class MemberInnerClass {
		/**
		 * 成员内部类可以定义变量
		 * 成员内部类不允许定义常量和静态属性
		 * protected static String attribute = "成员内部类静态属性";
		 * public final attribute = "成员内部类常量";
		 */
		private String attribute = "成员内部类变量";
		
		public MemberInnerClass() {
			System.out.println("成员内部类");
			System.out.println("成员内部类可以访问外部类所有成员和方法：" + privateAttribute + "、" + protectedAttribute + "、" + publicAttribute);
			privateMethod();
			protectedMethod();
			publicMethod();
		}
	}

	static class StaticInnerClass {
		
		public StaticInnerClass() {
			System.out.println("静态内部类");
			System.out.println("静态内部类只可以访问外部静态属性： "+ protectedAttribute);
			protectedMethod();
		}
		
	}
	
}

/**
 * 
 * 局部内部类
 * 
 * @version 1.0
 */
class LocalClass {
	
	private String privateAttribute = "私有属性";
	protected static String protectedAttribute = "受保护属性";
	public final String publicAttribute = "公开属性";
	
	public LocalClass() {
		FourInnerClass fourInnerClass = new FourInnerClass();
		System.out.println("局部内部类");
		System.out.println("局部内部类不可以访问私有属性，只可以访问外部类的受保护和公开属性： " + fourInnerClass.protectedAttribute + "、" + fourInnerClass.publicAttribute);
		fourInnerClass.protectedMethod();
		fourInnerClass.publicMethod();
	}
	
}
