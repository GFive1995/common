package com.designpattern.structural.decorator;


public class DecoratorDemo {

	public static void main(String[] args) {
		Shape Circle = new Circle();
		ShapeDecorator redCircle = new RedShapeDecorator(new Circle());
		ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());
		System.out.println("Circle with nornal border");
		Circle.draw();
		
		System.out.println("\nCircle with red border");
		redCircle.draw();
		
		System.out.println("\nRectangle od red border");
		redRectangle.draw();
	}
	
}
