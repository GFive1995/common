package com.basics;

/**
 * 
 * 静态属性和静态方法是否可以被继承重写？
 * 静态属性和静态方法可以被继承，但是没有被重写而是被隐藏了。
 * 
 * 1、静态方法和属性是属于类的，调用的时候直接通过 类名.方法名 完成，不需要继承机制可以调用。
 * 		如果子类里面定义了静态方法和属性，那么这时候父类的静态方法或属性称之为“隐藏”。
 * 		如果想要调用父类的静态方法和属性，直接通过 父类名.方法/变量 完成。
 * 		子类是有继承静态方法和属性，但是跟实例方法和属性不太一样，存在“隐藏”这种情况。
 * 2、多态之所以能够实现依赖于继承、接口和重写、重载。有了继承和重写就可以实现父类的引用指向不同子类的对象。
 * 		重写的功能是重写后子类的优先级要高于父类的优先级，但是“隐藏”是没有优先级之分的。
 * 3、静态属性、静态方法和非静态的属性都可以被继承和隐藏和不能被重写，因此不能实现多态，不能实现父类的引用可以指向不同子类的对象。
 * 		非静态方法可以被继承和重写，因此可以实现多态。
 * 
 * 原博客地址：https://blog.csdn.net/zhouhong1026/article/details/19114589
 * @version 1.0
 */
public class StaticTest {

	public static void main(String[] args) {
		/**
		 * 输出父类的静态属性、非静态属性、静态方法、非静态方法。
		 */
		ChildA childA = new ChildA();
		System.out.println(childA.nameStatic);
		System.out.println(childA.nameNoStatic);
		childA.methodStatic();
		childA.methodNostatic();
		System.out.println("--------------------");
		
		/**
		 * 输出父类的静态属性、非静态属性、静态方法、非静态方法。
		 */
		Father fatherA = new ChildA();
		System.out.println(fatherA.nameStatic);
		System.out.println(fatherA.nameNoStatic);
		fatherA.methodStatic();
		fatherA.methodNostatic();
		System.out.println("--------------------");
	
		/**
		 * 输出子类B重写后静态属性、非静态属性、静态方法、非静态方法
		 */
		ChildB childB = new ChildB();
		System.out.println(childB.nameStatic);
		System.out.println(childB.nameNoStatic);
		childB.methodStatic();
		childB.methodNostatic();
		System.out.println("--------------------");
		
		/**
		 * 输出父类静态属性、非静态属性、静态方法
		 * 输出子类B重写后的非静态方法
		 */
		Father fatherB = new ChildB();
		System.out.println(fatherB.nameStatic);
		System.out.println(fatherB.nameNoStatic);
		fatherB.methodStatic();
		fatherB.methodNostatic();
	}
	
}

class Father {
	public static String nameStatic = "父类静态属性";
	public String nameNoStatic = "父类非静态属性";
	
	public static void methodStatic() {
		System.out.println("父类静态方法");
	}
	
	public void methodNostatic() {
		System.out.println("父类非静态方法");
	}
}

/**
 * 
 * 子类A继承父类的所有属性和方法
 * 
 * @version 1.0
 */
class ChildA extends Father {
	
}

/**
 * 
 * 子类B重写
 * 
 * @version 1.0
 */
class ChildB extends Father {
	public static String nameStatic = "子类B重写后的静态属性";
	public String nameNoStatic = "子类B重写后的非静态属性";
	
	public static void methodStatic() {
		System.out.println("子类B重写后的静态方法");
	}
	
	public void methodNostatic() {
		System.out.println("子类B重写后的非静态方法");
	}
}