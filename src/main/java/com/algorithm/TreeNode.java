package com.algorithm;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 树
 * 
 * @version 1.0
 */
public class TreeNode {

	int val;
	TreeNode left;
	TreeNode right;
	
	public TreeNode(int val) {
		this.val = val;
	}
	
	/**
	 * 
	 * 方法描述:合并二叉树
	 *
	 * @param t1
	 * @param t2
	 * @return
	 * 
	 */
	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null) {
			return t2;
		}
		if (t2 == null) {
			return t1;
		}
		TreeNode resultNode = new TreeNode(t1.val + t2.val);
		resultNode.left = mergeTrees(t1.left, t2.left);
		resultNode.right = mergeTrees(t1.right, t2.right);
		return resultNode;
	}
	
	/**
	 * 
	 * 方法描述:反转二叉树
	 *
	 * @param t1
	 * @param t2
	 * @return
	 * 
	 */
	public TreeNode inverTree(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode left = inverTree(root.left);
		TreeNode right = inverTree(root.right);
		root.left = right;
		root.right = left;
		return root;
	}
	
	/**
	 * 
	 * 方法描述:二叉树最大深度
	 *
	 * @param root
	 * @return
	 * 
	 */
	public int maxDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int left = maxDepth(root.left);
		int right = maxDepth(root.right);
		return Math.max(left, right) + 1;
	}
	
	/**
	 * 
	 * 方法描述:修剪二叉树
	 * 给定一个二叉搜索树，同时给定最小边界L 和最大边界 R。通过修剪二叉搜索树，使得所有节点的值在[L, R]中 (R>=L) 。
	 *
	 * @param root
	 * @param L
	 * @param R
	 * @return
	 * 
	 */
	public TreeNode trimBST(TreeNode root, int L, int R) {
		if (root == null) {
			return root;
		}
		if (root.val > R) {
			return trimBST(root.left, L, R);
		}
		if (root.val < L) {
			return trimBST(root.right, L, R);
		}
		root.left = trimBST(root.left, L, R);
		root.right = trimBST(root.right, L, R);
		return root;
	}
	
	/**
	 * 
	 * 方法描述:二叉树的所有路径
	 * 给定一个二叉树，返回所有从根节点到叶子节点的路径
	 *
	 * @param root
	 * @return
	 * 
	 */
	public List<String> binaryTreePaths(TreeNode root) {
		LinkedList<String> paths = new LinkedList<>();
		if (root == null) {
			return paths;
		}
		LinkedList<TreeNode> node_stack = new LinkedList<>();
		LinkedList<String> path_stack = new LinkedList<>();
		node_stack.add(root);
		path_stack.add(Integer.toString(root.val));
		TreeNode node;
		String path;
		while (!node_stack.isEmpty()) {
			node = node_stack.pollLast();
			path = path_stack.pollLast();
			if (node.left == null && node.right == null) {
				paths.add(path);
			}
			if (node.left != null) {
				node_stack.add(node.left);
				path_stack.add(path + "->" + Integer.valueOf(node.left.val));
			}
			if (node.right != null) {
				node_stack.add(node.right);
				path_stack.add(path + "->" + Integer.valueOf(node.right.val));
			}
		}
		return paths;
	}
	
	
}
