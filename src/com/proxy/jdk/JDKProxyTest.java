package com.proxy.jdk;

import java.lang.reflect.Proxy;

import com.proxy.Saying;
import com.proxy.SayingImpl;


public class JDKProxyTest {

	public static void main(String[] args) {
		// 被代理的业务类
		Saying target = new SayingImpl();
		// 将目标类与横切类编织在一起
		MyInvocationHandler handler = new MyInvocationHandler(target);
		// 创建代理实例
		Saying proxy = (Saying) Proxy.newProxyInstance(
				target.getClass().getClassLoader(), 	// 目标类加载器
				target.getClass().getInterfaces(), 		// 目标类接口
				handler);
		//横切类
		proxy.say("张三");
		proxy.talk("李四");
	}
	
}
