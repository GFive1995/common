package com.aop.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


public class CglibProxy implements MethodInterceptor {

	Enhancer enhancer = new Enhancer();
	public Object getProxy(Class<?> clazz) {
		// 设置需要创建的子类
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		// 通过字节码技术动态创建子类实例
		return enhancer.create();
	}
	
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		// 目标方法执行前
		System.out.println("-----cglib-----");
		System.out.println("-----准备-----");
		// 目标方法调用
		Object result = proxy.invokeSuper(obj, args);
		// 目标方法执行后
		System.out.println("-----结束-----");
		return result;
	}

}
