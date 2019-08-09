package com.algorithm;

import java.util.Iterator;
import java.util.Map.Entry;

public class LRUTest {

	public static void main(String[] args) {
		LRU<Integer, Object> lru = new LRU<>(5);

		lru.put(0, "0");
		lru.put(1, "1");
		lru.put(2, "2");
		lru.put(3, "3");
		lru.put(4, "4");
		lru.put(5, "5");
		lru.put(6, "6");
		lru.put(7, "7");
		lru.put(8, "8");
		lru.put(9, "9");

		Iterator<Entry<Integer, Object>> iterator = lru.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Object> entry = iterator.next();
			Integer key = entry.getKey();
			System.out.println("Key: " + key);

		}

	}

}
