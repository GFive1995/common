package com.spi;

import java.util.ServiceLoader;

/**
 * 
 * SPI:Service Provider Interface，一种服务发现机制。
 * SPI的本质是将接口实现类的全限定名配置在文件中，并由服务加载器读取配置文件，加载实现类。
 * 
 * @version 1.0
 */
public class JDKSPI {

	public static void main(String[] args) {
		ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
		System.out.println("Java SPI");
		for (Robot robot : serviceLoader) {
			robot.sayHello();
		}
	}
	
}
