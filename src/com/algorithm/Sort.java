package com.algorithm;

import java.util.Arrays;

/**
 * 
 * 常见排序算法
 * 
 * @version 1.0
 */
public class Sort {

	public static void main(String[] args) {
		int a[] = { 10, 90, 20, 80, 30, 70, 40, 50, 60 };
		System.out.println("初始数据:" + Arrays.toString(a));
		bubbleSort(a);					// 冒泡排序
		cocktailSort(a);				// 鸡尾酒排序
		selectionSort(a);				// 选择排序
		insertSort1(a);					// 直接插入排序
		insertSort2(a);					// 直接插入排序
		quickSort(a, 0, a.length-1);	// 快速排序
	}
	
	/**
	 * 
	 * 方法描述:冒泡排序
	 * 它重复地走访过要排序的元素列，依次比较相邻的元素，如果他们的顺序错误就把他们交换过来。
	 * 
	 * @param a 数组数据
	 * 
	 */
	public static void bubbleSort(int[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n - 1; j++) {
				int swap1 = 0;
				int swap2 = 0;
				if (a[j] > a[j + 1]) {
					swap1 = a[j];
					swap2 = a[j + 1];
					swap(a, j, j + 1);
				}
				System.out.println("交换位置:" + j + "-----交换数据：" + swap1 + "," + swap2 + "-----交换结果：" + Arrays.toString(a));
			}
			System.out.println("第" + i + "次排序结果：" + Arrays.toString(a));
		}
	}
	
	/**
	 * 
	 * 方法描述:鸡尾酒排序
	 * 双向冒泡排序，排序时双向在队列中进行排序。
	 *
	 * @param a	
	 * 
	 */
	public static void cocktailSort(int[] a) {
		int left = 0;
		int right = a.length - 1;
		for (int i = left; i < right; i++) {
			for (int j = left; j < right; j++) {
				int swap1 = 0;
				int swap2 = 0;
				if (a[j] > a[j + 1]) {
					swap1 = a[j];
					swap2 = a[j + 1];
					swap(a, j, j + 1);
				}
				System.out.println("左交换位置:" + j + "-----交换数据：" + swap1 + "," + swap2 + "-----交换结果："
						+ Arrays.toString(a));
			}
			left++;
			for (int j = right - 1; j > left; j--) {
				int swap1 = 0;
				int swap2 = 0;
				if (a[j] > a[j + 1]) {
					swap1 = a[j];
					swap2 = a[j + 1];
					swap(a, j, j + 1);
				}
				System.out.println("右交换位置:" + j + "-----交换数据：" + swap1 + "," + swap2 + "-----交换结果："
						+ Arrays.toString(a));
			}
			right--;
			System.out.println("第" + i + "次排序结果：" + Arrays.toString(a));
		}
	}
	
	/**
	 * 
	 * 方法描述:选择排序
	 * 第一次从待排序的数据元素中选出最小（或最大）的一个元素，存放在序列的起始位置，然后再从剩余的未排序元素中寻找到最小（大）元素，然后放到已排序的序列的末尾。
	 * 以此类推，直到全部待排序的数据元素的个数为零。
	 * 选择排序是不稳定的排序方法。
	 *
	 * @param a
	 * 
	 */
	public static void selectionSort(int[] a) {
		int min;
		int max;
		int count = a.length;
		for (int i = 0; i < count; i++) {
			min = i;
			max = count - 1;
			for (int j = i; j < count; j++) {
				if (a[min] > a[j]) {
					min = j;
				}
				if (a[max] < a[j]) {
					max = j;
				}
			}
			if (min != i) {
				System.out.println("min交换位置:"+max+","+i+"-----交换数据："+a[min]+","+a[i]+"-----min排序前：" + Arrays.toString(a));
				swap(a, min, i);
				System.out.println("min排序后：" + Arrays.toString(a));
			}
			if (max != count-1) {
				System.out.println("max交换位置:"+max+","+(count-1)+"-----交换数据："+a[max]+","+a[count-1]+"-----max排序前：" + Arrays.toString(a));
				swap(a, max, count-1);
				System.out.println("max排序后：" + Arrays.toString(a));
			}
			count--;
			System.out.println("第" + i + "次排序结果：" + Arrays.toString(a));
		}
	}
	
	/**
	 * 
	 * 方法描述:直接插入排序
	 * 将一条记录插入已经排好的有序表中，从而得到一个新的、记录数量增1的有序表
	 *
	 * @param a
	 * 
	 */
	public static void insertSort(int[] a) {
		for (int i = 1; i < a.length; i++) {	// 第0位独自作为有序数列，从第1位开始向后遍历
			if (a[i]<a[i-1]) {					// 第0~i-1位为有序位，若第i位小于i-1位，继续寻位并插入，否则认为0~i位是有序的，忽略此次
				int temp = a[i];
				int k = i -1;
				for (int j = k; j>0 && temp<a[j]; j--) {	// 从第i-1位向前遍历，直至找到小于第i位值停止
					a[j+1] = a[j];
					k--;
				}
				a[k+1] = temp;
			}
			System.out.println("第" + i + "次排序结果：" + Arrays.toString(a));
		}
	}
	
	/**
	 * 
	 * 方法描述:直接插入排序
	 *
	 * @param arr
	 * @return
	 * 
	 */
	public static void insertSort1(int[] a) {
		for (int i = 1; i < a.length; i++) {
			for (int j = i; j > 0; j--) {
				if (a[j] < a[j - 1]) {
					int temp = a[j];
					a[j] = a[j - 1];
					a[j - 1] = temp;
				}
			}
			System.out.println("第" + i + "次排序结果：" + Arrays.toString(a));
		}
	}
	
	/**
	 * 
	 * 方法描述:直接插入排序
	 *
	 * @param a
	 * 
	 */
	public static void insertSort2(int[] a) {
		for (int i = 1; i < a.length; i++) {
			int temp = a[i];
			int index = 0;
			for (int j = i; j > 0; j--) {
				if (a[j] < a[j - 1]) {
					a[j] = a[j - 1];
					index = j - 1;
				}
				a[index] = temp;
			}
			System.out.println("第" + i + "次排序结果：" + Arrays.toString(a));
		}
	}
	
	/**
	 * 
	 * 方法描述:快速排序
	 * 通过一趟排序将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另外一部分的所有数据都要小。
	 * 然后再按此方法对这两部分数据分别进行快速排序，整个排序过程可以递归进行，以此达到整个数据变成有序序列。
	 *
	 * @param a
	 * 
	 */
	public static int[] quickSort(int[] a, int start, int end) {
		int pivot = a[start];
		int i = start;
		int j = end;
		while (i < j) {
			while ((i < j) && (a[j] > pivot)) {
				j--;
			}
			while ((i < j) && (a[i] < pivot)) {
				i++;
			}
			if ((a[i] == a[j]) && (i < j)) {
				i++;
			} else {
				swap(a, i, j);
			}
		}
		System.out.println("起始位置 " + start + " 到终止位置 " + end + " 的排序结果：" + Arrays.toString(a));
		if (i - 1 > start)
			a = quickSort(a, start, i - 1);
		if (j + 1 < end)
			a = quickSort(a, j + 1, end);
		return a;
	}
	
	/**
	 * 
	 * 方法描述:相邻元素交换位置
	 * 
	 * @param a
	 * @param i
	 * @param j
	 * 
	 */
	public static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
}
