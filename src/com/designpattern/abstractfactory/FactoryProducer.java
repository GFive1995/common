package com.designpattern.abstractfactory;


public class FactoryProducer {

	public static AbstractFactory getFactory(String chioce) {
		if (chioce.equalsIgnoreCase("SHAPE")) {
			return new ShapeFactory();
		} else if (chioce.equalsIgnoreCase("COLOR")) {
			return new ColorFactory();
		}
		return null;
	}
	
}
