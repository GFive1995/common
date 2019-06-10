package com.aop.cglib;

import com.aop.Saying;
import com.aop.SayingImpl;


public class CglibProxyTest {

	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxy();
		//通过动态代理生成子类的方式创建代理类
		Saying target = (Saying) proxy.getProxy(SayingImpl.class);
		target.say("张三");
		target.talk("李四");;
	}
	
}
