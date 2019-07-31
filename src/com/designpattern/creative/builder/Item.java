package com.designpattern.creative.builder;

/**
 * 
 * 食物条目
 * 
 * @version 1.0
 */
public interface Item {
	public String name();
	public Packing packing();
	public float price();
}
