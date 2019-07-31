package com.designpattern.structural.flyweight;

import java.util.HashMap;


public class ShapeFactory {

	private static final HashMap<String, Shape> circleMap = new HashMap<>();
	
	public static Shape getCirclr(String color) {
		Circle circle = (Circle) circleMap.get(color);
		
		if (circle == null) {
			circle = new Circle(color);
			circleMap.put(color, circle);
			System.out.println("创建圆，颜色：" + color);
		}
		return circle;
	}
	
}
