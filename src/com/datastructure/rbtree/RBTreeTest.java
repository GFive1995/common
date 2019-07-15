package com.datastructure.rbtree;

/**
 * 
 * 红黑树测试
 * 
 * @version 1.0
 */
public class RBTreeTest {
	
	public static void main(String[] args) {
		final int a[] = {10, 90, 20, 80, 30, 70, 40, 50, 60};
		
		RBTree<Integer> tree = new RBTree<Integer>();
		for (int i : a) {
			tree.putTreeVal(i);
			tree.preOrder();
		}
		
		tree.removeTreeVal(60);
		tree.preOrder();
		
		tree.removeTreeVal(50);
		tree.preOrder();
	}
	
}
