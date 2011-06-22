package br.ufpe.cin.algoritmos.binarytree;

public class PostOrder<TKey extends Comparable<TKey>, TValue> extends
		TreeTransversal<TKey, TValue> {

	public PostOrder(TreeNode<TKey, TValue> root) {
		super(root);
	}

	@Override
	protected void doTransversal(TreeNode<TKey, TValue> node) {
		pushVisit(node);
		pushTransverse(node.getRight());
		pushTransverse(node.getLeft());
	}
}
