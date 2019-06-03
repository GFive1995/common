package com.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;


/**
 * 
 * 被观察者
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年5月31日 下午1:17:52
 */
public abstract class Observable {

	protected List<Observer> observers = new ArrayList<Observer>();
	
	protected void registObserver(Observer observer) {
		observers.add(observer);
	}
	
	protected void removeObserver(Observer observer) {
		observers.remove(observer);
	}
	
	protected void notifyObserver(Object object) {
		for (Observer observer : observers) {
			try {
				observer.update(this, object);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("###Observable##nofithObserver#发布通知{},事件执行失败{}" + JSONObject.toJSONString(object));
			}
		}
	}
}
