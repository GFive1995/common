package com.designpattern.strategy;


public class StrategyTest {

	public static void main(String[] args) {
		Context context = null;
		context = new Context(new OperationAdd());
		System.out.println("10 + 5 = " + context.executeStrategy(10, 5));
		context = new Context(new OperationSub());
		System.out.println("10 - 5 = " + context.executeStrategy(10, 5));
		context = new Context(new OperationMul());
		System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
		context = new Context(new OperationDiv());
		System.out.println("10 / 5 = " + context.executeStrategy(10, 5));
	}
	
}
