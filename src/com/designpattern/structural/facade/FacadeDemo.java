package com.designpattern.structural.facade;


public class FacadeDemo {

	public static void main(String[] args) {
		ShapeMaker shapeMaker = new ShapeMaker();
		shapeMaker.drawCircle();
		shapeMaker.drawSquare();
		shapeMaker.drawRectangle();
	}
	
}
