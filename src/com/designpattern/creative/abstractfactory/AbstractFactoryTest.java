package com.designpattern.creative.abstractfactory;

public class AbstractFactoryTest {

	public static void main(String[] args) {
		AbstractFactory shapeFactory = FactoryProducer.getFactory("SHAPE");
		Shape shape1 = shapeFactory.getShape("CIRCLE");
		shape1.draw();
		Shape shape2 = shapeFactory.getShape("RECTANGLE");
		shape2.draw();
		Shape shape3 = shapeFactory.getShape("SQUARE");
		shape3.draw();

		AbstractFactory colorFactory = FactoryProducer.getFactory("COLOR");
		Color color1 = colorFactory.getColor("RED");
		color1.fill();
		Color color2 = colorFactory.getColor("GREEN");
		color2.fill();
		Color color3 = colorFactory.getColor("BLUE");
		color3.fill();
	}

}
