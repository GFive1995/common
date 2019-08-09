package com.designpattern.creative.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 食物类
 * 
 * @version 1.0
 */
public class Meal {
	
	private List<Item> items = new ArrayList<Item>();
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public float getCost() {
		float cost = 0.0f;
		for (Item item : items) {
			cost += item.price(); 
		}
		return cost;
	}
	
	public void showItems() {
		for (Item item : items) {
			StringBuffer bill = new StringBuffer("Item : " + item.name());
			bill.append(", Packing : " + item.packing().pack());
			bill.append(", Price : " + item.price());
			System.out.println(bill);
		}
	}
	
}
