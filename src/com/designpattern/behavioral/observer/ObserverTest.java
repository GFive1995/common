package com.designpattern.behavioral.observer;

import java.util.HashMap;
import java.util.Map;


public class ObserverTest {

	public static void main(String[] args) {
		Map<String,Object> mapOne = new HashMap<String, Object>();
		mapOne.put("key", "JobOne");
		mapOne.put("value",  "JobOne执行");
		ObserverUtil.notifyObservers(mapOne);
		Map<String,Object> mapTwo = new HashMap<String, Object>();
		mapTwo.put("key", "JobTwo");
		mapTwo.put("value",  "JobTwo执行");
		ObserverUtil.notifyObservers(mapTwo);
	}
	
}
