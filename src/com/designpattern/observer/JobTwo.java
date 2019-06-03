package com.designpattern.observer;

import java.util.Map;


public class JobTwo implements Observer {

	public void update(Observable o, Object obj) {
		if (obj instanceof Map) {
			if (((Map) obj).get("key").equals("JobTwo")) {
				System.out.println("###JobTwo##JobTwo开始执行:" + ((Map) obj).get("key"));
				Common.jobTwo = true;
			}
		}
	}

}
