package adv;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TreeProblem {

	private Tree root = null;

	class Tree<T> {
		Tree left;
		Tree right;
		Tree parent;
		T data;

		Tree(T data) {
			this.left = null;
			this.right = null;
			this.parent = null;
			this.data = data;
		}

		Tree(T data, Tree p) {
			this.left = null;
			this.right = null;
			this.parent = p;
			this.data = data;
		}
	}

	public void loadData() {
		root = new Tree(10);
		root.left = new Tree(7);
		root.left.right = new Tree(9);
		root.left.right.left = new Tree(8);
		root.right = new Tree(16);
		root.right.left = new Tree(12);
		root.right.right = new Tree(18);
	}

	public void inOrder(Tree node) {
		if (node == null)
			return;
		inOrder(node.left);
		System.out.print(node.data + " ");
		inOrder(node.right);
	}

	public Tree lcaGeneric(Tree node1, Tree node2, Tree root) {
		if (root == null)
			return null;
		if (root == node1 || root == node2)
			return root;
		Tree leftLCA = lcaGeneric(node1, node2, root.left);
		Tree rightLCA = lcaGeneric(node1, node2, root.right);

		if (leftLCA != null && rightLCA != null)
			return root;

		return (leftLCA != null) ? leftLCA : rightLCA;
	}

	public Tree lcaBinaryWithParent(Tree node1, Tree node2, Tree root) {
		if (root == null)
			return null;
		Set<Tree> set = new HashSet<>();
		// Fill from Node1
		while (node1 != null) {
			set.add(node1);
			node1 = node1.parent;
		}
		while (node2 != null) {
			if (set.contains(node2))
				return node2;
			node2 = node2.parent;
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeProblem tp = new TreeProblem();
		tp.loadData();
		tp.inOrder(tp.root);
		System.out.println("\nLCA:" + tp.lcaGeneric(tp.root.right.right, tp.root.left, tp.root).data);

		TreeProblem tpParent = new TreeProblem();
		tpParent.loadDataParent();
		tpParent.inOrder(tpParent.root);
		Tree n1 = tpParent.root.right.right;
		Tree n2 = tpParent.root.left;
		System.out.println("\nLCA for " + n1.data + " and " + n2.data + " is: "
				+ tpParent.lcaBinaryWithParent(n1,n2, tpParent.root).data);

	}

	public void loadDataParent() {
		root = new Tree(11, null);
		root.left = new Tree(8, root);
		root.left.right = new Tree(10, root.left);
		root.left.right.left = new Tree(9, root.left.right);
		root.right = new Tree(18, root);
		root.right.left = new Tree(13, root.right);
		root.right.right = new Tree(20, root.right);
	}

}
