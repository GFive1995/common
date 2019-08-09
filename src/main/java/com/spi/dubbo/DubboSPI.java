package com.spi.dubbo;

/**
 * 
 * Dubbo SPI
 * 通过键值对的方式配置
 * 
 * @version 1.0
 */
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
