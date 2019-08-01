package com.designpattern.structural.adapter;


public class AdapterDemo {

	public static void main(String[] args) {
		AudioPlayer audioPlayer = new AudioPlayer();
		
		audioPlayer.play("mp3", "hello world.mp3");
		audioPlayer.play("mp4", "hello world.mp4");
		audioPlayer.play("vlc", "hello world.vlc");
		audioPlayer.play("avi", "hello world.avi");
	}
	
}
