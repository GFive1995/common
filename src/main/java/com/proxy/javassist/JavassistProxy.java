package com.proxy.javassist;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

/**
 * 
 * javassist动态代理
 * 
 * @version 1.0
 */
public class JavassistProxy {

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public Object getProxy(Class clazz) throws InstantiationException, IllegalAccessException {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(clazz);
		
		proxyFactory.setHandler(new MethodHandler() {
			public Object invoke(Object self, Method thismethod, Method proceed, Object[] args) throws Throwable {
				System.out.println("-----javassist-----");
				System.out.println("-----方法调用前-----");
				Object result = proceed.invoke(self, args);
				System.out.println("-----方法调用后-----");
				return result;
			}
		});

		// 通过字节码技术动态创建子类实例
		return proxyFactory.createClass().newInstance();
	}
	
}
