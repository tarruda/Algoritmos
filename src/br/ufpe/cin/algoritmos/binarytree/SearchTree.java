package br.ufpe.cin.algoritmos.binarytree;

import java.util.Iterator;

public class SearchTree<TKey extends Comparable<TKey>, TValue> implements
		BinaryTree<TKey, TValue> {

	private TreeNode<TKey, TValue> root;
	private int count;

	public SearchTree() {
		count = 0;
	}

	@Override
	public void insert(TKey key, TValue value) {
		root = insert(key, value, root);
	}

	@Override
	public TValue lookup(TKey key) {
		TValue ret = null;
		TreeNode<TKey, TValue> node = lookup(key, root);
		if (node != null)
			ret = node.getValue();
		return ret;
	}

	@Override
	public void update(TKey key, TValue newValue) {
		root = update(key, newValue, root);
	}

	@Override
	public void delete(TKey key) {
		root = delete(key, root);
	}

	@Override
	public int getHeight() {
		return calculateHeight(root);
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Iterable<TValue> inOrder() {
		return new Iterable<TValue>() {
			public Iterator<TValue> iterator() {
				return new ValueIterator(new InOrder<TKey, TValue>(root));
			}
		};
	}

	@Override
	public Iterable<TValue> postOrder() {
		return new Iterable<TValue>() {
			public Iterator<TValue> iterator() {
				return new ValueIterator(new PostOrder<TKey, TValue>(root));
			}
		};
	}

	@Override
	public Iterable<TValue> preOrder() {
		return new Iterable<TValue>() {
			public Iterator<TValue> iterator() {
				return new ValueIterator(new PreOrder<TKey, TValue>(root));
			}
		};
	}

	protected int calculateHeight(TreeNode<TKey, TValue> node) {
		int ret = -1;
		ret = Math.max(ret, nodeHeight(node.getLeft()) + 1);
		ret = Math.max(ret, nodeHeight(node.getRight()) + 1);
		return ret;
	}

	protected int nodeHeight(TreeNode<TKey, TValue> node) {
		if (node == null)
			return -1;
		return node.getHeight();
	}

	protected void onInsert(TreeNode<TKey, TValue> node) {
		node.setHeight(calculateHeight(node));
	}

	protected void onDelete(TreeNode<TKey, TValue> node) {
		node.setHeight(calculateHeight(node));
	}

	private TreeNode<TKey, TValue> insert(TKey key, TValue value,
			TreeNode<TKey, TValue> relativeRoot) {
		if (relativeRoot == null) {
			relativeRoot = new TreeNode<TKey, TValue>(key, value);
			count++;
		} else if (key.compareTo(relativeRoot.getKey()) < 0)
			relativeRoot.setLeft(insert(key, value, relativeRoot.getLeft()));
		else if (key.compareTo(relativeRoot.getKey()) > 0)
			relativeRoot.setRight(insert(key, value, relativeRoot.getRight()));
		else
			throw new IllegalArgumentException(
					"Key already exists in this tree");
		onInsert(relativeRoot);
		return relativeRoot;
	}

	private TreeNode<TKey, TValue> lookup(TKey key, TreeNode<TKey, TValue> relativeRoot) {
		TreeNode<TKey, TValue> ret = null;
		if (relativeRoot != null) {
			if (key.compareTo(relativeRoot.getKey()) < 0)
				ret = lookup(key, relativeRoot.getLeft());
			else if (key.compareTo(relativeRoot.getKey()) > 0)
				ret = lookup(key, relativeRoot.getRight());
			else
				ret = relativeRoot;
		}
		return ret;
	}

	private TreeNode<TKey, TValue> update(TKey key, TValue newValue,
			TreeNode<TKey, TValue> relativeRoot) {
		if (relativeRoot == null)
			throw new IllegalArgumentException(
					"Key does not exist in this tree");
		else if (key.compareTo(relativeRoot.getKey()) < 0)
			relativeRoot.setLeft(update(key, newValue, relativeRoot.getLeft()));
		else if (key.compareTo(relativeRoot.getKey()) > 0)
			relativeRoot
					.setRight(update(key, newValue, relativeRoot.getRight()));
		else
			relativeRoot.setValue(newValue);
		return relativeRoot;
	}

	private TreeNode<TKey, TValue> delete(TKey key, TreeNode<TKey, TValue> relativeRoot) {
		if (relativeRoot == null)
			throw new IllegalArgumentException(
					"Key does not exist in this tree");
		else if (key.compareTo(relativeRoot.getKey()) < 0)
			relativeRoot.setLeft(delete(key, relativeRoot.getLeft()));
		else if (key.compareTo(relativeRoot.getKey()) > 0)
			relativeRoot.setRight(delete(key, relativeRoot.getRight()));
		else
			relativeRoot = deleteNode(relativeRoot);
		if (relativeRoot != null)
			onDelete(relativeRoot);
		return relativeRoot;
	}

	private TreeNode<TKey, TValue> deleteNode(TreeNode<TKey, TValue> relativeRoot) {
		boolean deleting = true;
		TreeNode<TKey, TValue> ret = null;
		TreeNode<TKey, TValue> left = relativeRoot.getLeft();
		TreeNode<TKey, TValue> right = relativeRoot.getRight();
		if (left == null && right != null)
			ret = right;
		else if (left != null && right == null)
			ret = left;
		else if (left != null && right != null) {
			deleting = false;
			TreeNode<TKey, TValue> inOrderPredecessor = inOrderPredecessor(relativeRoot);
			relativeRoot.setKey(inOrderPredecessor.getKey());
			relativeRoot.setValue(inOrderPredecessor.getValue());
			// Only after replacing relativeRoot with its in order
			// predecessor, I delete using the left subtree as root.
			// This is because the deletion will only execute the onDelete
			// hook up to the left node, avoiding key conflicts(since the
			// current node has the same key as the in order predecessor).
			relativeRoot.setLeft(delete(inOrderPredecessor.getKey(),
					relativeRoot.getLeft()));
			ret = relativeRoot;
		}
		if (deleting)
			count--;
		return ret;
	}

	private TreeNode<TKey, TValue> inOrderPredecessor(TreeNode<TKey, TValue> node) {
		TreeNode<TKey, TValue> current = node.getLeft();
		if (current != null)
			while (current.getRight() != null)
				current = current.getRight();
		return current;
	}

	private class ValueIterator implements Iterator<TValue> {

		protected Iterator<TreeNode<TKey, TValue>> inner;

		public ValueIterator(Iterator<TreeNode<TKey, TValue>> inner) {
			this.inner = inner;
		}

		@Override
		public boolean hasNext() {
			return inner.hasNext();
		}

		@Override
		public TValue next() {
			return inner.next().getValue();
		}

		@Override
		public void remove() {
			inner.remove();
		}
	}
}
