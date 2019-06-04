package com.designpattern.single;


public class SingleTest {
	
	public static void main(String[] args) {
		EagerSingleton eagerSingleton1, eagerSingleton2;
		eagerSingleton1 = EagerSingleton.getInstance();
		eagerSingleton2 = EagerSingleton.getInstance();
		System.out.println(eagerSingleton1 == eagerSingleton2);
		
		LazySingleton lazySingleton1, lazySingleton2;
		lazySingleton1 = LazySingleton.getInstance();
		lazySingleton2 = LazySingleton.getInstance();
		System.out.println(lazySingleton1 == lazySingleton2);
		
		StaticInnerSingleton staticInnerSingleton1, staticInnerSingleton2;
		staticInnerSingleton1 = StaticInnerSingleton.getInstance();
		staticInnerSingleton2 = StaticInnerSingleton.getInstance();
		System.out.println(staticInnerSingleton1 == staticInnerSingleton2);
	}
	
}
