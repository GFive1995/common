package com.designpattern.structural.proxy;


public class ProxyDemo {

	public static void main(String[] args) {
		Image image = new ProxyImage("proxy.jpg");
		// 磁盘加载
		image.display();
		// 不需要加载磁盘
		image.display();
	}
	
}
