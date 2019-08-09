package com.designpattern.creative.builder;

/**
 * 
 * 汉堡
 * 
 * @version 1.0
 */
public abstract class Burger implements Item {

	public Packing packing() {
		return new Wrapper();
	}
	
	public abstract float price();
}
