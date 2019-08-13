package com.basics;

/**
 * 
 * 类加载器
 * 1、Bootstrap 启动类加载器:JRE\lib\rt.jar
 * 2、Extension 扩展类加载器:JRE\lib\ext\*.jar
 * 3、App 应用类加载器:CLASSPATH
 * 4、自定义类加载器:通过自定义类加载
 * 
 * @version 1.0
 */
public class ClassLoader {

	public static void main(String[] args) {
		java.lang.ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	
        System.out.println("当前类加载器:" + classLoader);
        System.out.println("当前类父类加载器:" + classLoader.getParent());
        System.out.println("当前类祖父类加载器:" + classLoader.getParent().getParent());
	}
	
}
