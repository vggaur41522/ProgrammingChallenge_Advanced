package adv;

import java.util.List;

import adv.TreeProblem.Data_Type;
import adv.TreeProblem.Tree;

public class LongestCommonPath {
	public int maxLen = 0;
	public int getLCP(TreeProblem tn)
	{
		System.out.println("\n LCP CALC Starts ... ");
		tn.inOrderAdv(tn.root);
		Tree<Data_Type> root = tn.root;
		lcpUtil(root,root.data.getVal() - 1,0);
		return maxLen;
	}
	public void lcpUtil(Tree<Data_Type> root, int preRoot,int currLen)
	{
		if(root == null)
			return ;
		if(root.data.getVal() == preRoot+1)
			currLen = currLen+1;
		maxLen = Math.max(maxLen,currLen);
		lcpUtil(root.left,root.data.getVal(),currLen);
		lcpUtil(root.right,root.data.getVal(),currLen);
	}
}
