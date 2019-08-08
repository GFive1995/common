package com.algorithm;

import java.util.LinkedHashMap;

/**
 * 
 * LRU算法：最近最少使用，淘汰时间最长未被使用的数据
 * @param <K>
 * @param <V>
 * 
 * @version 1.0
 */
public class LRU<K, V> extends LinkedHashMap<K, V> {

	private int cacheSize;
	
	public LRU(int cacheSize) {
		/**
		 * accessOrder: true=访问顺序排序(get/put时排序),false=插入顺序排序
		 */
		super(16, 0.75f, true);
		this.cacheSize = cacheSize;
	}
	
	/**
	 * 新增元素时调用，返回true时删除最老元素
	 */
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return size() > cacheSize;
	}
	
}
