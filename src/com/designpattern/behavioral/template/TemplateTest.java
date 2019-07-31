package com.designpattern.behavioral.template;


public class TemplateTest {

	public static void main(String[] args) {
		Game game = null;
		game = new Basketball();
		game.Play();
		game = new Football();
		game.Play();
	}
	
}
