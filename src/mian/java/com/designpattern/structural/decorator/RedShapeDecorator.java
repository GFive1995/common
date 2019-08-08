package com.designpattern.structural.decorator;


public class RedShapeDecorator extends ShapeDecorator {

	public RedShapeDecorator(Shape decoratorShape) {
		super(decoratorShape);
	}

	public void draw() {
		decoratorShape.draw();
		setRedBorder(decoratorShape);
	}
	
	private void setRedBorder(Shape decoratorShape) {
		System.out.println("Border Color: Red");
	}
}
