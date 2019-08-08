package com.basics;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 
 * 反射
 * 
 * @version 1.0
 * @date 2019年8月7日 上午9:48:54
 */
public class Reflex {

	public static void main(String[] args) throws Exception {
		Class class_1 = ReflexObject.class;
		
		Class class_2 = new ReflexObject().getClass();
		
		Class class_3 = Class.forName("com.basics.ReflexObject");
		
		/**
		 * 创建类对象
		 */
		ReflexObject reflexObject = (ReflexObject) class_1.newInstance();
		reflexObject.Method();
		
		/**
		 * 操作属性
		 */
		Field[] fields = class_1.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field.getName());
		}
		
		/**
		 * 操作方法
		 */
		Method[] methods = class_1.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
		}
		
		/**
		 * 操作构造器
		 */
		Constructor[] constructors = class_1.getDeclaredConstructors();
		for (Constructor constructor : constructors) {
			System.out.println(constructor.getName());
		}
		
	}
	
}

class ReflexObject {
	
	public String publicAttribute;
	private String privateAttribute;
	protected String protectedAttribute;
	
	public ReflexObject() {
		System.out.println("反射构造器");
	}
	
	public void Method() {
		System.out.println("反射方法");
	}
	
}