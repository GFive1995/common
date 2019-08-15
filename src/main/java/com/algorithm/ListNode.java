package com.algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 链表
 * 
 * @version 1.0
 */
public class ListNode {

	int val;
	ListNode next;

	public ListNode(int val) {
		this.val = val;
	}
	
	/**
	 * 
	 * 方法描述:删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点。
	 * 
	 * 1、只有被删除的节点，所有无法获取删除节点之前的节点。
	 * 2、将删除节点是值修改为该节点下个节点的值。
	 * 3、将删除节点的 next指针指向其下个节点的下个节点。
	 *
	 * @param node
	 * 
	 */
	public void deleteNode(ListNode node) {
		node.val = node.next.val;
		node.next = node.next.next;
	}
	
	/**
	 * 
	 * 方法描述:反转一个单链表
	 * 
	 * 1、将当前节点的 next指针改为指向前一个元素。
	 * 2、由于节点没有引用上一个节点，所有必须事先存储其前一个元素。
	 * 3、修改引用之前，需要另一个指针来存储下一个节点。
	 * 4、返回头引用。
	 *
	 * @param node
	 * 
	 */
	public ListNode reverseList(ListNode head) {
		ListNode prev = null;
		ListNode curr = head;
		while (curr != null) {
			ListNode tempNode = curr.next;
			curr.next = prev;
			prev = curr;
			curr = tempNode;
		}
		return prev;
	}
	
	
	/**
	 * 
	 * 方法描述:返回链表中间节点，如果两个中间节点，返回第二个。
	 * 
	 * 1、按顺序将每个节点放入数组中。
	 * 2、返回中间节点A[A.length/2]
	 *
	 * @param head
	 * @return
	 * 
	 */
	public ListNode middleNode(ListNode head) {
		ListNode[] A = new ListNode[100];
		int t = 0;
		while (head != null) {
			A[t++] = head.next;
			head = head.next;
		}
		return A[t/2];
	}
	
	/**
	 * 
	 * 方法描述:合并两个有序链表
	 * 
	 * 递归
	 * 		list1[0] + merge(list[1:], list2)	list1[0] < list2[0]
	 * 		list2[0] + merge(list1, list[2:])	otherwise
	 *
	 * @param l1
	 * @param l2
	 * @return
	 * 
	 */
	public ListNode mergeTwoList_01(ListNode l1, ListNode l2) {
		if (l1 == null) {
			return l2;
		} else if (l2 == null) {
			return l1;
		} else if (l1.val < l2.val) {
			l1.next = mergeTwoList_01(l1.next, l2);
			return l1;
		} else {
			l2.next = mergeTwoList_01(l1, l2.next);
			return l2;
		}
	}
	
	/**
	 * 
	 * 方法描述:合并两个有序链表
	 * 
	 * 迭代
	 * 1、创建一个哨兵节点 prehead，返回合并的链表。
	 * 2、维护一个 prev指针，调整它的 next节点。
	 *
	 * @param l1
	 * @param l2
	 * @return
	 * 
	 */
	public ListNode mergerTowList_02(ListNode l1, ListNode l2) {
		ListNode prehead = new ListNode(-1);
		
		ListNode prev = prehead;
		
		while (l1 !=null && l2 !=null) {
			if (l1.val <= l2.val) {
				prev.next = l1;
				l1 = l1.next;
			} else {
				prev.next = l2;
				l2 = l2.next;
			}
			prev = prev.next;
		}
		prev.next = l1 == null ? l2 : l1;

		return prehead;
	}
	
	/**
	 * 
	 * 方法描述:删除链表中的重复元素
	 *
	 * @param head
	 * @return
	 * 
	 */
	public ListNode deleteDuplicates(ListNode head) {
		ListNode current = head;
		while (current != null && current.next != null) {
			if (current.val == current.next.val) {
				current.next = current.next.next;
			} else {
				current = current.next;
			}
		}
		return head;
	}
	
	/**
	 * 
	 * 方法描述:获取两个链表相交的起始节点
	 * 
	 * 双指针法
	 * 1、创建两个指针pA、pB，分别初始化为l1和l2的头节点。
	 * 2、两个指针向后遍历。
	 * 3、pA到链表尾部时，重定向到l2的头部；pB到链表尾部时，重定向到l1的头部。
	 * 4、如果pA和pB相交，则pA/pB为相交节点。
	 *
	 * @param l1
	 * @param l2
	 * @return
	 * 
	 */
	public ListNode getIntersectionNode(ListNode l1, ListNode l2) {
		if (l1 == null || l2 == null) {
			return null;
		}
		ListNode pA = l1;
		ListNode pB = l2;
		while (pA != pB) {
			pA = pA == null ? l2 : l1.next;
			pB = pB == null ? l1 : l2.next;
		}
		return pA;
	}
	
	/**
	 * 
	 * 方法描述:判断链表是否有环
	 *
	 * @param head
	 * @return
	 * 
	 */
	public boolean hasCycle(ListNode head) {
		Set<ListNode> nodeSet = new HashSet<>();
		while (head != null) {
			if (nodeSet.contains(head)) {
				return true;
			} else {
				nodeSet.add(head);
			}
			head = head.next;
		}
		return false;
	}
	
	/**
	 * 
	 * 方法描述:移除链表元素
	 *
	 * @param head
	 * @param val
	 * @return
	 * 
	 */
	public ListNode removeElements(ListNode head, int val) {
		if (head == null) {
			return null;
		}
		head.next = removeElements(head.next, val);
		if (head.val == val) {
			head = head.next;
		} 
		return head;
	}
	
	/**
	 * 
	 * 方法描述:判断链表时候是回文链表
	 *
	 * @param head
	 * @return
	 * 
	 */
	public boolean isPalindrome(ListNode head) {
		if (head == null || head.next == null) {
			return true;
		}
		/**
		 * 快慢两个节点
		 */
		ListNode fast = head;
		ListNode slow = head;
		while (fast !=null && fast.next!=null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		/**
		 * 反转后半部分链表
		 */
		ListNode iter = slow;
		ListNode prev = null;
		while (slow != null) {
			ListNode tempNode = iter.next;
			iter = prev;
			prev = iter;
			iter = tempNode;
		}
		iter = head;
		ListNode tail = prev;
		/**
		 * 对比是否是回文链表
		 */
		boolean flag = true;
		while (tail != null) {
			if (tail.val != iter.val) {
				flag = false;
				break;
			}
			tail = tail.next;
			iter = iter.next;
		}
		/**
		 * 恢复链表原来状态
		 */
		iter = prev;
		prev = null;
		while (iter != null) {
			ListNode tempNode = iter.next;
			iter = prev;
			prev = iter;
			iter = tempNode;
		}
		return flag;
	}
	
	/**
	 * 
	 * 方法描述:链表排序
	 * 
	 * 归并排序
	 * 图片地址:https://pic.leetcode-cn.com/8c47e58b6247676f3ef14e617a4686bc258cc573e36fcf67c1b0712fa7ed1699-Picture2.png
	 * 1、分割 cut环节:找到链表中点，并从中点将链表断开。
	 * 2、合并 merge环节:将两个链表合并，转化为一个排序链表。
	 *
	 * @param head
	 * @return
	 * 
	 */
	public ListNode sortList(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		/**
		 * 分割
		 */
		ListNode fast = head;
		ListNode slow = head;
		while (fast != null && fast.next != null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		ListNode temp = slow;
		slow.next = null;
		ListNode left = sortList(head);
		ListNode right = sortList(temp);
		/**
		 * 合并
		 */
		ListNode headNode = new ListNode(-1);
		ListNode prev = headNode;
		while (left != null && right != null) {
			if (left.val < right.val) {
				prev.next = left;
				left = left.next;
			} else {
				prev.next = right;
				right = right.next;
			}
			prev = prev.next;
		}
		prev = left == null ? right : left;
		return headNode;
	}
	
	/**
	 * 
	 * 方法描述:两两交换链表中的节点(不是单纯改变节点的值，需要实际进行节点交换)
	 *
	 * @param head
	 * @return
	 * 
	 */
	public ListNode swapPairs(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode next = head.next;
		head.next = swapPairs(next.next);
		next.next = head;
		return next;
	}
	
	/**
	 * 
	 * 方法描述:奇偶链表
	 * 把所有的奇数节点和偶数节点分别排在一起(节点编号的奇偶性)。
	 *
	 * @param head
	 * @return
	 * 
	 */
	public ListNode oddEvenList(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode odd = head, even = head.next, evenHead = even;
		while (even != null && even.next != null) {
			odd.next = even.next;
			odd = odd.next;
			even.next = odd.next;
			even = even.next;
		}
		odd.next = evenHead;
		return odd;
	}
	
	/**
	 * 
	 * 方法描述:给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
	 *
	 * @param head
	 * @param x
	 * @return
	 * 
	 */
	public ListNode partition(ListNode head, int x) {
		ListNode before_head = new ListNode(0);
		ListNode before = before_head;
		ListNode after_head = new ListNode(0);
		ListNode after = after_head;
		while (head != null) {
			if (head.val < x) {
				before.next = head;
				before = before.next;
			} else {
				after.next = head;
				after = after.next;
			}
			head = head.next;
		}
		after.next = null;
		before.next = after_head.next;
		return before_head.next;
	}
}
