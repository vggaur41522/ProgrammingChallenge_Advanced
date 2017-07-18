package adv;

import adv.TreeProblem.Data_Type;
import adv.TreeProblem.Tree;

public class ValidateBST {
	public boolean checkBST(TreeProblem tn) {
		Tree<Data_Type> root = tn.root;
		return checkBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public boolean checkBSTUtil(Tree<Data_Type> root, int min, int max) {
		if (root == null)
			return true;

		if (root.data.getVal() < min || root.data.getVal() > max)
			return false;
		return checkBSTUtil(root.left, min, root.data.getVal()) && 
				checkBSTUtil(root.right, root.data.getVal(), max);
	}
}
