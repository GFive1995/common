package com.designpattern.creative.prototype;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * 将类存储在营业Map中
 * 
 * @version 1.0
 */
public class ShapeCache {
	
	private static ConcurrentHashMap<String, Shape> shapeMap = new ConcurrentHashMap<>();
	
	public static Shape getShape(String shapeId) {
		Shape cachedShape = shapeMap.get(shapeId);
		return (Shape) cachedShape.clone();	
	}
	
	/**
	 * 
	 * 方法描述:创建每种形状
	 *
	 */
	public static void loadCache() {
		Circle circle = new Circle();
		circle.setId("1");
		shapeMap.put(circle.getId(), circle);
		
		Square square = new Square();
		square.setId("2");
		shapeMap.put(square.getId(), square);
		
		Rectangle rectangle = new Rectangle();
		rectangle.setId("3");
		shapeMap.put(rectangle.getId(), rectangle);
	}
	
}
