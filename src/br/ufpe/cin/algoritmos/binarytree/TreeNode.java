package br.ufpe.cin.algoritmos.binarytree;

import java.io.Serializable;

class TreeNode<TKey extends Comparable<TKey>, TValue> implements Serializable{
	
	private static final long serialVersionUID = 8392649820647246085L;
	
	private TKey key;
	private TValue value;
	private TreeNode<TKey, TValue> left;
	private TreeNode<TKey, TValue> right;
	private int height;

	public TreeNode(TKey key, TValue value) {
		super();
		this.key = key;
		this.value = value;
	}

	public TKey getKey() {
		return key;
	}

	public void setKey(TKey key) {
		this.key = key;
	}

	public TValue getValue() {
		return value;
	}

	public void setValue(TValue value) {
		this.value = value;
	}

	public TreeNode<TKey, TValue> getLeft() {
		return left;
	}

	public void setLeft(TreeNode<TKey, TValue> left) {
		this.left = left;
	}

	public TreeNode<TKey, TValue> getRight() {
		return right;
	}

	public void setRight(TreeNode<TKey, TValue> right) {
		this.right = right;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
