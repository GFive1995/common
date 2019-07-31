package com.designpattern.behavioral.template;


public abstract class Game {
	abstract void initialize();
	abstract void startPlay();
	abstract void endPlay();
	
	public final void Play() {
		initialize();
		
		startPlay();
		
		endPlay();
	}
}
