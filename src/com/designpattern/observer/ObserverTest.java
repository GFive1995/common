package com.designpattern.observer;

import java.util.HashMap;
import java.util.Map;


public class ObserverTest {

	public static void main(String[] args) {
		System.out.println("jobOne:" + Common.jobOne + "-----jobTwo:" + Common.jobTwo);
		Map<String,Object> mapOne = new HashMap<String, Object>();
		mapOne.put("key", "JobOne");
		mapOne.put("value",  "JobOne执行");
		ObserverUtil.notifyObservers(mapOne);
		Map<String,Object> mapTwo = new HashMap<String, Object>();
		mapTwo.put("key", "JobTwo");
		mapTwo.put("value",  "JobTwo执行");
		ObserverUtil.notifyObservers(mapTwo);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("jobOne:" + Common.jobOne + "-----jobTwo:" + Common.jobTwo);
	}
	
}
