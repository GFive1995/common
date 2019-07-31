package com.designpattern.behavioral.observer;

import java.util.Map;

public class JobOne implements Observer {

	public void update(Observable o, Object obj) {
		if(obj instanceof Map) {
			if (((Map<?, ?>) obj).get("key").equals("JobOne")) {
				System.out.println("###JobOne##JobOne开始执行:" + ((Map<?, ?>) obj).get("key"));
				Common.jobOne = true;
			}
		}
	}

}
