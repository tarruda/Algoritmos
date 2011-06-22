package br.ufpe.cin.algoritmos.list;

import java.util.ListIterator;

class ListNodeIterator<T> implements ListIterator<T> {
	private int idx;
	private ListNode<T>[] array;

	public ListNodeIterator(ListBounds<T> listBounds) {
		this(0, listBounds);
	}

	public ListNodeIterator(int index, ListBounds<T> listBounds) {
		idx = index;
		this.array = listBounds != null ? listBounds.nodeArray : null;
	}

	@Override
	public void add(T e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNext() {
		return idx < (array != null ? array.length : 0);
	}

	@Override
	public boolean hasPrevious() {
		return idx > 0;
	}

	@Override
	public T next() {
		return array[idx++].value;
	}

	@Override
	public int nextIndex() {
		return idx;
	}

	@Override
	public T previous() {
		return array[--idx].value;
	}

	@Override
	public int previousIndex() {
		return idx - 1;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(T e) {
		throw new UnsupportedOperationException();
	}
}