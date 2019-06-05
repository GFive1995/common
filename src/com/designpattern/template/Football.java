package com.designpattern.template;


public class Football extends Game {

	@Override
	void initialize() {
		System.out.println("足球初始化");
	}

	@Override
	void startPlay() {
		System.out.println("足球开始");
	}

	@Override
	void endPlay() {
		System.out.println("足球结束");
	}

}
