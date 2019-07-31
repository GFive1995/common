package com.designpattern.creative.builder;


/**
 * 
 * 可乐
 * 
 * @version 1.0
 */
public class Coke extends ColdDrink {

	@Override
	public String name() {
		return "Coke";
	}

	@Override
	public float price() {
		return 30.0f;
	}

}
