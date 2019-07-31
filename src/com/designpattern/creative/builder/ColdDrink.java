package com.designpattern.creative.builder;

/**
 * 
 * 冷饮
 * 
 * @version 1.0
 */
public abstract class ColdDrink implements Item {

	public Packing packing() {
		return new Bottle();
	}
	
	public abstract float price();
	
}
