package br.ufpe.cin.algoritmos.binarytree;

public interface BinaryTree<TKey extends Comparable<TKey>, TValue> {
	void insert(TKey key, TValue value);

	TValue lookup(TKey key);

	void update(TKey key, TValue newValue);

	void delete(TKey key);

	int getHeight();

	int getCount();

	Iterable<TValue> preOrder();

	Iterable<TValue> postOrder();

	Iterable<TValue> inOrder();	
	
}
