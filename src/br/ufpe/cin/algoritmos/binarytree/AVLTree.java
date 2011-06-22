package br.ufpe.cin.algoritmos.binarytree;

public class AVLTree<TKey extends Comparable<TKey>, TValue> extends
		SearchTree<TKey, TValue> {

	@Override
	protected void onInsert(TreeNode<TKey, TValue> node) {
		super.onInsert(node);
		ensureBalance(node);
	}

	@Override
	protected void onDelete(TreeNode<TKey, TValue> node) {
		super.onDelete(node);
		ensureBalance(node);
	}

	private void ensureBalance(TreeNode<TKey, TValue> node) {
		int bf = balanceFactor(node);
		if (bf == -2) {
			if (balanceFactor(node.getRight()) == 1)
				rotateRight(node.getRight());
			rotateLeft(node);
		} else if (bf == 2) {
			if (balanceFactor(node.getLeft()) == -1)
				rotateLeft(node.getLeft());
			rotateRight(node);
		} else if (bf > 2 || bf < -2)
			throw new IllegalStateException("Invalid balance factor.");
	}

	private int balanceFactor(TreeNode<TKey, TValue> node) {
		int leftHeight = nodeHeight(node.getLeft());
		int rightHeight = nodeHeight(node.getRight());
		return leftHeight - rightHeight;
	}

	private void recalculateHeights(TreeNode<TKey, TValue> node) {
		if (node != null) {
			TreeNode<TKey, TValue> left = node.getLeft();
			TreeNode<TKey, TValue> right = node.getRight();
			if (left != null)
				left.setHeight(calculateHeight(left));
			if (right != null)
				right.setHeight(calculateHeight(right));
			node.setHeight(calculateHeight(node));
		}
	}

	private void rotateLeft(TreeNode<TKey, TValue> node) {
		TreeNode<TKey, TValue> right = node.getRight();
		TreeNode<TKey, TValue> left = node.getLeft();
		TreeNode<TKey, TValue> newLeft = new TreeNode<TKey, TValue>(node.getKey(), node
				.getValue());

		// Update root
		node.setKey(right.getKey());
		node.setValue(right.getValue());

		// Set new left
		newLeft.setLeft(left);
		node.setLeft(newLeft);

		// Set new right
		node.setRight(right.getRight());

		// Transfer the old right's left to
		// the new left's right
		newLeft.setRight(right.getLeft());

		// Recalculate heights
		recalculateHeights(node);
	}

	private void rotateRight(TreeNode<TKey, TValue> node) {
		TreeNode<TKey, TValue> right = node.getRight();
		TreeNode<TKey, TValue> left = node.getLeft();
		TreeNode<TKey, TValue> newRight = new TreeNode<TKey, TValue>(node.getKey(),
				node.getValue());

		// Set new root
		node.setKey(left.getKey());
		node.setValue(left.getValue());

		// Set new right
		newRight.setRight(right);
		node.setRight(newRight);

		// Set new left
		node.setLeft(left.getLeft());

		// Transfer the old left's right to
		// the new right's left
		newRight.setLeft(left.getRight());

		// Recalculate heights
		recalculateHeights(node);
	}

}
