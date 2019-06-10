package com.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class MyInvocationHandler implements InvocationHandler {

	private Object target;
	
	public MyInvocationHandler(Object target) {
		this.target = target;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 目标方法执行前
		System.out.println("-----准备-----");
		// 目标方法调用
		Object object = method.invoke(target, args);
		// 目标方法执行后
		System.out.println("-----结束-----");
		return object;
	}

}
