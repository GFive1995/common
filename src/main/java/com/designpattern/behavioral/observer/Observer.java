package com.designpattern.behavioral.observer;

/**
 * 
 * 观察者
 * 
 * @version 1.0
 * @author wangcy
 * @date 2019年5月31日 下午1:15:47
 */
public interface Observer {
	// 声明响应方法
	public void update(Observable o, Object obj);
	
}
