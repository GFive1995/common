package com.proxy;

public class SayingImpl implements Saying {

	public void say(String name) {
		System.out.println(name + ":大家好!");
	}

	public void talk(String name) {
		System.out.println(name + ":始终贯彻社会主义核心价值观!");
	}

}
