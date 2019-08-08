package com.algorithm;

/**
 * 
 * 常见搜索算法
 * 
 * @version 1.0
 */
public class Search {
	
	public static void main(String[] args) {
		int[] a = {10, 20, 30, 40, 50, 60, 70, 80, 90};
		int index = binarySearch(a, 30);
		System.out.println(index);
	}
	
	/**
	 * 
	 * 方法描述:二分查找
	 *
	 * @param a
	 * @param num
	 * @return
	 * 
	 */
	public static int binarySearch(int[] a, int num) {
		int start = 0;
		int end = a.length - 1;
		while(start <= end) {
			int mid = (start + end) >>> 1;
			System.out.println("开始位置start:" + start + "-----中间位置mid:" + mid + "-----结束为止end:" + end);
			if (num == a[mid]) {
				return mid;
			} else if (num < a[mid]) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		return -1;
	}
	
}
