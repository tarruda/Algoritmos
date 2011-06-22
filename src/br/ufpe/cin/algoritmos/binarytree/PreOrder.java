package br.ufpe.cin.algoritmos.binarytree;

public class PreOrder<TKey extends Comparable<TKey>, TValue> extends TreeTransversal<TKey, TValue> {

	public PreOrder(TreeNode<TKey, TValue> root) {
		super(root);		
	}

	@Override
	protected void doTransversal(TreeNode<TKey, TValue> node) {
		pushTransverse(node.getRight());		
		pushTransverse(node.getLeft());	
		pushVisit(node);
	}
}
