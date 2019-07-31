package com.designpattern.behavioral.strategy;


public class OperationMul implements Strategy {

	public int doOperation(int num1, int num2) {
		return num1 * num2;
	}

}
