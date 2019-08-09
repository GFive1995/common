package com.designpattern.behavioral.template;


public class Basketball extends Game {

	@Override
	void initialize() {
		System.out.println("篮球初始化");
	}

	@Override
	void startPlay() {
		System.out.println("篮球开始");
	}

	@Override
	void endPlay() {
		System.out.println("篮球结束");
	}

}
