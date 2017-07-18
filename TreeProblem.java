package adv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import javax.xml.crypto.Data;

public class TreeProblem {

	Tree root = null;

	class Data_Type {
		private int val;
		private int lCount;

		Data_Type(int val, int lCount) {
			this.val = val;
			this.lCount = lCount;
		}

		public int getVal() {
			return this.val;
		}

		public void setLCount(int LC) {
			this.lCount = LC;
		}

		public int getLCount() {
			return this.lCount;
		}
	}

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

	// Primitive Data Loading
	public void loadData() {
		root = new Tree(10);
		root.left = new Tree(7);
		root.left.right = new Tree(9);
		root.left.right.left = new Tree(8);
		root.right = new Tree(16);
		root.right.left = new Tree(12);
		root.right.right = new Tree(18);
	}

	// Data with Parent Pointer
	public void loadDataParent() {
		root = new Tree(11, null);
		root.left = new Tree(8, root);
		root.left.right = new Tree(10, root.left);
		root.left.right.left = new Tree(9, root.left.right);
		root.right = new Tree(18, root);
		root.right.left = new Tree(13, root.right);
		root.right.right = new Tree(20, root.right);
	}

	public void inOrder(Tree node) {
		if (node == null)
			return;
		inOrder(node.left);
		System.out.print(node.data + " ");
		inOrder(node.right);
	}

	public Tree leftMinima(Tree node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	public Tree inorderSuccessor(Tree root, Tree node, boolean parntPop) {
		if (root == null || node == null)
			return null;
		if (node.right != null) {
			return leftMinima(node.right);
		} else {
			// Parent Pointer Implementation
			if (parntPop) {
				Tree parent = node.parent;
				while (parent != null && node != parent.left) {
					node = parent;
					parent = parent.parent;
				}
				return parent;
			}
		}
		return null;
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
		TreeProblem tp = new TreeProblem();
		tp.loadData();
		tp.inOrder(tp.root);
		/*---------- LCA using Parent-------------*/
		System.out.println("\nLCA:" + tp.lcaGeneric(tp.root.right.right, tp.root.left, tp.root).data);

		TreeProblem tpParent = new TreeProblem();
		tpParent.loadDataParent();
		tpParent.inOrder(tpParent.root);

		/*---------- LCA using Parent-------------*/
		Tree n1 = tpParent.root.left.right;
		Tree n2 = tpParent.root;
		System.out.println("\nLCA for " + n1.data + " and " + n2.data + " is: "
				+ tpParent.lcaBinaryWithParent(n1, n2, tpParent.root).data);

		/*---------- In Ordedr Successor - Using Parent -------------*/
		Tree test = tpParent.inorderSuccessor(tpParent.root, n2, true);
		if (test == null)
			System.out.println("\nInorder Successor for present");
		else
			System.out.println("\nInorder Successor for " + n2.data + " is: " + test.data);

		/*---------- Level Order Traversal - Using Queue -------------*/
		tp.levelOrderQueue(tp.root);

		System.out.println("\n");
		/*---------- Level Order Traversal - Using Queue -------------*/
		tp.levelOrderUsingStack(tp.root);

		/*---------- Invert Binary Tree -------------*/
		Tree te = tp.invertBinaryTree(tp.root);
		System.out.println();
		tp.levelOrderUsingStack(te);

		/*---------- K Smallest -------------*/
		int k = 3;
		System.out.print("\n" + k + " smallest no:" + tp.kSmallestItem(tp.root, k));

		/*---------- BST INSERTION -------------*/
		int[] arr = new int[] { 20, 8, 22, 9, 4, 5, 10, 23 };
		TreeProblem tn = new TreeProblem();
		tn.root = tn.loadDataWithType(tn.root, arr);
		System.out.println();
		tn.inOrderAdv(tn.root);
		System.out.println("\nK Th Min using lCount:" + tn.kthMinUsingLCount(tn.root, 3));
		
		LongestCommonPath lcp = new LongestCommonPath();
		System.out.println("\n Longest Consecutive Path :"+lcp.getLCP(tn));
	}

	public int kthMinUsingLCount(Tree<Data_Type> root, int k) {
		if (root == null)
			return -1;
		while (root != null) {
			int N = root.data.getLCount();
			if (N + 1 == k)
				return root.data.getVal();
			else if (N > k)
				root = root.left;
			else {
				k = k - 1;
				root = root.right;
			}
		}
		return -1;
	}

	public void inOrderAdv(Tree<Data_Type> root) {
		if (root == null)
			return;
		inOrderAdv(root.left);
		System.out.println(root.data.getVal() + ":" + root.data.getLCount());
		inOrderAdv(root.right);
	}

	public Tree loadDataWithType(Tree root, int[] arr) {
		for (int i : arr) {
			Tree<Data_Type> node = new Tree<>(new Data_Type(i, 0));
			root = insertNode(root, node);
		}
		return root;
	}

	public Tree insertNode(Tree<Data_Type> root, Tree<Data_Type> node) {
		if (root == null) {
			root = node;
			return root;
		}
		if (node.data.getVal() < root.data.getVal()) {
			root.data.setLCount(root.data.getLCount() + 1);
			root.left = insertNode(root.left, node);
		} else {
			root.right = insertNode(root.right, node);
		}
		return root;
	}

	public void levelOrderUsingStack(Tree root) {
		if (root == null)
			return;
		int h = heightOfTree(root);
		// Bottoms up will be printed if traversed from h to 1
		// for(int i=h;i>=1;i--)
		for (int i = 1; i <= h; i++) {
			levelOrderUtil(root, i);
		}
	}

	public void levelOrderUtil(Tree root, int level) {
		if (root == null)
			return;
		if (level == 1)
			System.out.print(root.data + " ");

		levelOrderUtil(root.left, level - 1);
		levelOrderUtil(root.right, level - 1);
	}

	public Tree invertBinaryTree(Tree root) {
		if (root == null)
			return null;

		Queue<Tree> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Tree currNode = queue.poll();
			if (currNode.left != null)
				queue.offer(currNode.left);
			if (currNode.right != null)
				queue.offer(currNode.right);
			Tree temp = currNode.left;
			currNode.left = currNode.right;
			currNode.right = temp;
		}
		return root;
	}

	public Queue<Integer> pq = new PriorityQueue<>();

	public void kSmallUtil(Tree root) {
		if (root == null)
			return;
		kSmallUtil(root.left);
		pq.add((Integer) root.data);
		kSmallUtil(root.right);
	}

	public int kSmallestItem(Tree root, int k) {
		if (root == null)
			return -1;
		kSmallUtil(root);
		int n = -1;
		while (k > 0) {
			n = pq.poll();
			k--;
		}
		return n;
	}

	public int heightOfTree(Tree root) {
		if (root == null)
			return 0;
		return Math.max(heightOfTree(root.left), heightOfTree(root.right)) + 1;
	}

	public void levelOrderQueue(Tree root) {
		if (root == null)
			return;
		Queue<Tree> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Tree currNode = queue.poll();
			System.out.print(currNode.data + " ");
			if (currNode.left != null)
				queue.offer(currNode.left);
			if (currNode.right != null)
				queue.offer(currNode.right);
		}
	}
}
