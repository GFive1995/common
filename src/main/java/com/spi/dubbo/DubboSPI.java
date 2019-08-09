package com.spi.dubbo;


public class DubboSPI {

	public static void main(String[] args) {
		ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
		System.out.println("Dubbo SPI");
		Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
		optimusPrime.sayHello();
		Robot bumblebee = extensionLoader.getExtension("bumblebee");
		bumblebee.sayHello();
	}
}
