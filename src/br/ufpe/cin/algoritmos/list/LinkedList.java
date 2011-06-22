package br.ufpe.cin.algoritmos.list;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LinkedList<T> implements List<T> {

	private ListBounds<T> list;

	@Override
	public boolean add(T e) {
		list = appendTo(list, e);
		return true;
	}

	@Override
	public void add(int index, T element) {
		list = insertAt(list, element, index);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return addAll(size(), c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		ListBounds<T> toAppend = createList(c);
		list = insertAt(list, toAppend, index);
		return true;
	}

	@Override
	public void clear() {
		list = null;
	}

	@Override
	public boolean contains(Object o) {
		boolean found = false;
		Iterator<T> iter = iterator();
		while (iter.hasNext() && !found)
			found = equals(iter.next(), o);
		return found;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean containsAll = true;
		Iterator<?> iter = c.iterator();
		while (iter.hasNext() && containsAll)
			containsAll = containsAll && contains(iter.next());
		return containsAll;
	}

	@Override
	public T get(int index) {
		return getNode(index).value;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return list == null;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			ListNode<T> current = list != null ? list.head : null;

			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public T next() {
				T ret = current.value;
				current = current.next;
				return ret;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		ensureNodeArrayConsistency();
		return new ListNodeIterator<T>(index, list);
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T set(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return list != null ? list.size : 0;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <E> E[] toArray(E[] a) {
		throw new UnsupportedOperationException();
	}

	

	private ListNode<T> getNode(int index) {
		return nodeAt(list, index);
	}

	private void ensureNodeArrayConsistency() {
		if (list.nodeArray == null && list != null)
			list.nodeArray = createNodeArray(list.head, list.size);
	}

	

	private ListBounds<T> createList(Iterable<? extends T> c) {		
		ListNode<T> head = null;
		ListNode<T> tail = null;
		int size = 0;
		for (T obj : c) {
			tail = appendAndReturn(tail, obj);
			if (head == null)
				head = tail;
			size++;
		}
		return new ListBounds<T>(head, tail, size);
	}

	private ListNode<T> appendAndReturn(ListNode<T> node, T value) {
		ListNode<T> newNode = new ListNode<T>(value);
		if (node == null)
			node = newNode;
		else
			node.next = newNode;
		return newNode;
	}

	private ListBounds<T> appendTo(ListBounds<T> list, T value) {
		return insertAt(list, value, list != null ? list.size : 0);
	}

	private ListBounds<T> insertAt(ListBounds<T> list, T value, int index) {
		ListNode<T> newNode = new ListNode<T>(value);
		ListBounds<T> lb = new ListBounds<T>(newNode, newNode, 1);
		return insertAt(list, lb, index);
	}

	private ListBounds<T> insertAt(ListBounds<T> list, ListBounds<T> otherList,
			int index) {
		if (index < 0 || index > size())
			throw new IndexOutOfBoundsException();

		if (list == null)
			list = otherList;
		else {
			ListNode<T> current = null;
			if (index > 0) {
				ListNode<T> previous = nodeAt(list, index - 1);
				current = previous.next;
				previous.next = otherList.head;
			} else {
				current = list.head;
				list.head = otherList.head;
			}
			otherList.tail.next = current;
			list.size += otherList.size;
			
			ListNode<T> newTail = otherList.tail;
			while (newTail.next != null)
				newTail = newTail.next;
			list.tail = newTail;
		}
		return list;
	}
	
	private ListNode<T> nodeAt(ListBounds<T> list, int index) {
		if (index < 0 || list == null || index >= list.size)
			throw new IndexOutOfBoundsException();

		ListNode<T> ret = null;
		if (index == list.size - 1)
			ret = list.tail;
		else if (list.nodeArray != null)
			ret = list.nodeArray[index];
		else
			ret = nodeAt(list.head, index);
		return ret;
	}

	private ListNode<T> nodeAt(ListNode<T> head, int index) {
		ListNode<T> ret = null;
		if (index >= 0) {
			int pos = 0;
			ListNode<T> current = head;
			while (current != null && pos < index) {
				current = current.next;
				pos++;
			}
			ret = current;
		}
		return ret;
	}

	private boolean equals(Object obj1, Object obj2) {
		return (obj1 != null && obj1.equals(obj2)) || obj1 == null
				&& obj2 == null;
	}

	@SuppressWarnings("unchecked")
	private ListNode<T>[] createNodeArray(ListNode<T> node, int size) {
		ListNode<T>[] ret = (ListNode<T>[]) Array.newInstance(ListNode.class,
				size);
		int pos = 0;
		while (node != null) {
			ret[pos] = node;
			node = node.next;
			pos++;
		}
		return ret;
	}

}
