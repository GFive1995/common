package com.aop.javassist;

import com.aop.SayingImpl;


public class JavassistProxyTest {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		JavassistProxy javassistProxy = new JavassistProxy();
		SayingImpl sayingImpl = (SayingImpl) javassistProxy.getProxy(SayingImpl.class);
		sayingImpl.say("张三");
		sayingImpl.talk("李四");
	}
	
}
