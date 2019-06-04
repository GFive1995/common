package com.designpattern.factory;


public class FactoryTest {

	public static void main(String[] args) {
		ShapeFactory shapeFactory = new ShapeFactory();
		
		Shape shapeCircle = shapeFactory.getShape("CIRCLE");
		shapeCircle.draw();
		
		Shape shapeRectangle= shapeFactory.getShape("RECTANGLE");
		shapeRectangle.draw();
		
		Shape shapeSquare = shapeFactory.getShape("SQUARE");
		shapeSquare.draw();
	}
	
}
