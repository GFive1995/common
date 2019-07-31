package com.designpattern.creative.builder;

/**
 * 
 * 百事
 * 
 * @version 1.0
 */
public class Pepsi extends ColdDrink {

	@Override
	public String name() {
		return "Pepsi";
	}

	@Override
	public float price() {
		return 35.0f;
	}

}
