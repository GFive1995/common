package com.datastructure.rbtree;

/**
 * 
 * 红黑树
 * 
 * @param <T>
 * 
 * @version 1.0
 */
public class RBTree<T extends Comparable<T>> {

	private TreeNode<T> root;

	@SuppressWarnings("hiding")
	public class TreeNode<T extends Comparable<T>> {

		TreeNode<T> parent; 	// 父节点
		TreeNode<T> left; 		// 左节点
		TreeNode<T> right; 		// 右节点
		boolean red; 			// 节点颜色：true(红色)，false(黑色)
		T key; 					// key值
		int hash; 				// hash值，判断节点位置

		public TreeNode(T key, boolean red, TreeNode<T> parent, TreeNode<T> left, TreeNode<T> right) {
			this.key = key;
			this.red = red;
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.hash = key.hashCode();
		}

	}

	/**
	 * 
	 * 方法描述:遍历
	 *
	 */
	public void preOrder() {
		preOrder(root());
		System.out.println();
	}
	
	public void preOrder(TreeNode<T> tree) {
		if (tree != null) {
			StringBuffer treeBuffer = new StringBuffer();
			treeBuffer.append(tree.key);
			treeBuffer.append("(" + (tree.red ? "红色" : "黑色") + ")");
			treeBuffer.append((tree.parent != null ? "" : " 是根节点"));
			treeBuffer.append("     其左子节点:" + (tree.left != null ? tree.left.key : "NIL(空)"));
			treeBuffer.append("     其右子节点:" + (tree.right != null ? tree.right.key : "NIL(空)"));
			System.out.println(treeBuffer);
			preOrder(tree.left);
			preOrder(tree.right);
		}
	}

	public RBTree() {
		root = null;
	}

	/**
	 * 
	 * 方法描述:获取根节点
	 * 
	 * @return
	 * 
	 */
	public TreeNode<T> root() {
		if (root == null) {
			return this.root;
		}
		for (TreeNode<T> r = root, p;;) {
			if ((p = r.parent) == null)
				return r;
			r = p;
		}
	}

	/**
	 * 
	 * 方法描述:获取查询节点对象
	 * 
	 * @param k
	 * @return
	 * 
	 */
	public TreeNode<T> find(T k) {
		int h = k.hashCode();
		TreeNode<T> p = root();
		do {
			int ph;
			T pk;
			TreeNode<T> pl = p.left, pr = p.right, q;
			if ((ph = p.hash) > h)
				p = pl;
			else if (ph < h)
				p = pr;
			else if ((pk = p.key) == k || (k != null && k.equals(pk)))
				return p;
			else if (pl == null)
				p = pr;
			else if (pr == null)
				p = pl;
			else if ((q = find(k)) != null)
				return q;
			else
				p = pl;
		} while (p != null);
		return null;
	}

	/**
	 * 
	 * 方法描述:插入结点
	 * 
	 * @param t
	 * 
	 */
	public void putTreeVal(T t) {
		int h = t.hashCode();
		System.out.println("新增数据：" + t);
		TreeNode<T> root = root();
		if (root == null) {
			System.out.println("初始化根节点");
			this.root = new TreeNode<T>(t, false, null, null, null);
			return;
		}
		for (TreeNode<T> p = root;;) {
			int dir = 0, ph;
			if ((ph = p.key.hashCode()) > h)
				dir = -1;
			else if (ph < h)
				dir = 1;
			TreeNode<T> xp = p;
			if ((p = (dir <= 0) ? p.left : p.right) == null) {
				TreeNode<T> x = new TreeNode<T>(t, false, null, null, null);
				if (dir <= 0)
					xp.left = x;
				else
					xp.right = x;
				x.parent = xp;
				balanceInsertion(root, x);
				return;
			}
		}
	}

	/**
	 * 
	 * 方法描述:移除结点
	 * 
	 * @param t
	 * 
	 */
	public void removeTreeVal(T t) {
		System.out.println("删除节点:" + t);
		TreeNode<T> first = root(), rl;
		if (first == null)
			return;
		if (root.parent != null)
			root = root();
		if (root == null || ((root.right == null || (rl = root.left) == null || rl.left == null))) {
			return;
		}
		TreeNode<T> p = find(t);
		TreeNode<T> pl = p.left, pr = p.right, replacement;
		if (pl != null && pr != null) {
			TreeNode<T> s = pr, sl;
			while ((sl = s.left) != null)
				s = sl;
			boolean c = s.red;
			s.red = p.red;
			p.red = c;
			TreeNode<T> sr = s.right;
			TreeNode<T> pp = p.parent;
			if (s == pr) {
				p.parent = s;
				s.right = p;
			} else {
				TreeNode<T> sp = s.parent;
				if ((p.parent = sp) != null) {
					if (s == sp.left)
						sp.left = p;
					else
						sp.right = p;
				}
				if ((s.right = pr) != null)
					pr.parent = s;
			}
			p.left = null;
			if ((p.right = sr) != null)
				sr.parent = p;
			if ((s.left = pl) != null)
				pl.parent = s;
			if ((s.parent = pp) == null)
				root = s;
			else if (p == pp.left)
				pp.left = s;
			else
				pp.right = s;
			if (sr != null)
				replacement = sr;
			else
				replacement = p;
		} else if (pl != null)
			replacement = pl;
		else if (pr != null)
			replacement = pr;
		else
			replacement = p;
		if (replacement != p) {
			TreeNode<T> pp = replacement.parent = p.parent;
			if (pp == null)
				root = replacement;
			else if (p == pp.left)
				pp.left = replacement;
			else
				pp.right = replacement;
			p.left = p.right = p.parent = null;
		}
		balanceDeletion(root, replacement);
		if (replacement == p) {
			TreeNode<T> pp = p.parent;
			p.parent = null;
			if (pp != null) {
				if (p == pp.left)
					pp.left = null;
				else if (p == pp.right)
					pp.right = null;
			}
		}
	}

	/**
	 * 
	 * 方法描述:插入时保持红黑树结构
	 * 
	 * xp 	父节点				xp = x.parent
	 * xpp 	祖父节点				xpp = xp.parent
	 * xppl	祖父节点的左节点			xppl = xpp.left
	 * xppr	祖父节点的右节点			xppr = xpp.right
	 * 
	 * @param root
	 * @param x
	 * @return
	 * 
	 */
	public TreeNode<T> balanceInsertion(TreeNode<T> root, TreeNode<T> x) {
		// 插入结点设置为红色
		x.red = true;	
		for (TreeNode<T> xp, xpp, xppl, xppr;;) {
			// 父节点为空，那么当前节点作为根节点，并设置为黑色
			if ((xp = x.parent) == null) {
				x.red = false;
				return x;
			// 父节点是黑色或者祖父节点为空，不用处理，直接插入
			} else if (!xp.red || (xpp = xp.parent) == null)
				return root;
			// 父节点是祖父节点的左节点
			if (xp == (xppl = xpp.left)) {
				// 祖父节点的右节点不为空并且是红色
				if ((xppr = xpp.right) != null && xppr.red) {
					xppr.red = false;	// 祖父节点右节点设置为黑色
					xp.red = false;		// 父节点设置为黑色
					xpp.red = true;		// 祖父节点设置为红色
					x = xpp;			// 祖父节点设置为当前节点，可以开始新一轮的循环
				// 祖父节点的右节点为空或者是黑色
				} else {
					// 插入结点是父节点的右节点
					if (x == xp.right) {
						root = rotateLeft(root, x = xp);					// 父节点左旋
						xpp = (xp = x.parent) == null ? null : xp.parent;	// 设置祖父节点
					}
					// 父节点不为空
					if (xp != null) {
						xp.red = false;						// 设置父节点为黑色
						// 祖父节点不为空
						if (xpp != null) {
							xpp.red = true;					// 祖父节点设置为红色
							root = rotateRight(root, xpp);	// 祖父节点右旋
						}
					}
				}
			// 父节点是祖父节点右节点（与上边对称）
			} else {
				if (xppl != null && xppl.red) {
					xppl.red = false;
					xp.red = false;
					xpp.red = true;
					x = xpp;
				} else {
					if (x == xp.left) {
						root = rotateRight(root, x = xp);
						xpp = (xp = x.parent) == null ? null : xp.parent;
					}
					if (xp != null) {
						xp.red = false;
						if (xpp != null) {
							xpp.red = true;
							root = rotateLeft(root, xpp);
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 方法描述:删除时保持红黑树结构
	 * 
	 * @param root
	 * @param x
	 * @return
	 * 
	 */
	public TreeNode<T> balanceDeletion(TreeNode<T> root, TreeNode<T> x) {
		for (TreeNode<T> xp, xpl, xpr;;) {
			if (x == null || x == root)
				return root;
			else if ((xp = x.parent) == null) {
				x.red = false;
				return x;
			} else if (x.red) {
				x.red = false;
				return root;
			} else if ((xpl = xp.left) == x) {
				if ((xpr = xp.right) != null && xpr.red) {
					xpr.red = false;
					xp.red = true;
					root = rotateLeft(root, xp);
					xpr = (xp = x.parent) == null ? null : xp.right;
				}
				if (xpr == null)
					x = xp;
				else {
					TreeNode<T> sl = xpr.left, sr = xpr.right;
					if ((sr == null || !sr.red) && (sl == null || !sl.red)) {
						xpr.red = true;
						x = xp;
					} else {
						if (sr == null || !sr.red) {
							if (sl != null)
								sl.red = false;
							xpr.red = true;
							root = rotateRight(root, xpr);
							xpr = (xp = x.parent) == null ? null : xp.right;
						}
						if (xpr != null) {
							xpr.red = (xp == null) ? false : xp.red;
							if ((sr = xpr.right) != null)
								sr.red = false;
						}
						if (xp != null) {
							xp.red = false;
							root = rotateLeft(root, xp);
						}
						x = root;
					}
				}
			} else {
				if (xpl != null && xpl.red) {
					xpl.red = false;
					xp.red = true;
					root = rotateRight(root, xp);
					xpl = (xp = x.parent) == null ? null : xp.left;
				}
				if (xpl == null)
					x = xp;
				else {
					TreeNode<T> sl = xpl.left, sr = xpl.right;
					if ((sl == null || !sl.red) && (sr == null || !sr.red)) {
						xpl.red = true;
						x = xp;
					} else {
						if (sl == null || !sl.red) {
							if (sr != null)
								sr.red = false;
							xpl.red = true;
							root = rotateLeft(root, xpl);
							xpl = (xp = x.parent) == null ? null : xp.left;
						}
						if (xpl != null) {
							xpl.red = (xp == null) ? false : xp.red;
							if ((sl = xpl.left) != null)
								sl.red = false;
						}
						if (xp != null) {
							xp.red = false;
							root = rotateRight(root, xp);
						}
						x = root;
					}
				}
			}
		}
	}

	/**
	 * 
	 * 方法描述:左旋
	 * 左旋：以某个结点作为中心，其右子节点的左子节点变为其右子节点，其右子节点变为其父节点并成为新的中心，左子节点不变。
	 * p	旋转结点
	 * pp	旋转结点父节点
	 * r	旋转结点右节点
	 * rl	旋转结点右节点的左节点
	 * 
	 * @param root
	 * @param p
	 * @return
	 * 
	 */
	public TreeNode<T> rotateLeft(TreeNode<T> root, TreeNode<T> p) {
		TreeNode<T> r, pp, rl;
		if (p != null && (r = p.right) != null) {	// 旋转结点和旋转结点的右节点不为空
			if ((rl = p.right = r.left) != null)	// 将旋转结点右节点的左节点设置为旋转结点的右节点，并判断旋转结点右节点的左节点是否为空
				rl.parent = p;						// 如果旋转结点右节点的左节点不为空，将其的父节点设置为旋转结点
			if ((pp = r.parent = p.parent) == null)	// 将旋转结点的父节点设置为旋转结点右节点的父节点，并判断旋转结点父节点是否为空
				(root = r).red = false;				// 如果旋转结点父节点为空，将旋转结点右节点设置为根节点，颜色设置为黑色
			else if (pp.left == p)					// 如果旋转结点父节点的左节点是旋转结点
				pp.left = r;						// 将旋转结点的右节点设置为旋转结点父节点的左节点
			else
				pp.right = r;						// 否则将旋转结点右节点设置为旋转结点父节点的右节点
			r.left = p;								// 将旋转结点左节点设置为其又结点的左节点
			p.parent = r;							// 将旋转结点右节点设置为旋转结点的父节点
		}
		return root;
	}

	/**
	 * 
	 * 方法描述:右旋
	 * 类似左旋
	 * 
	 * @param root
	 * @param p
	 * @return
	 * 
	 */
	public TreeNode<T> rotateRight(TreeNode<T> root, TreeNode<T> p) {
		TreeNode<T> l, pp, lr;
		if (p != null && (l = p.left) != null) {
			if ((lr = p.left = l.right) != null)
				lr.parent = p;
			if ((pp = l.parent = p.parent) == null)
				(root = l).red = false;
			else if (pp.right == p)
				pp.right = l;
			else
				pp.left = l;
			l.right = p;
			p.parent = l;
		}
		return root;
	}

}
