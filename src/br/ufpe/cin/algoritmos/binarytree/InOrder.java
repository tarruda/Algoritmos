package br.ufpe.cin.algoritmos.binarytree;

public class InOrder<TKey extends Comparable<TKey>, TValue> extends
		TreeTransversal<TKey, TValue> {

	public InOrder(TreeNode<TKey, TValue> root) {
		super(root);
	}

	@Override
	protected void doTransversal(TreeNode<TKey, TValue> node) {
		pushTransverse(node.getRight());
		pushVisit(node);
		pushTransverse(node.getLeft());
	}
}
