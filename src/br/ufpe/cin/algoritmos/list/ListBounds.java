package br.ufpe.cin.algoritmos.list;

class ListBounds<T> {
	public transient ListNode<T>[] nodeArray;
	public ListNode<T> head;
	public ListNode<T> tail;
	public int size;

	public ListBounds(ListNode<T> head, ListNode<T> tail, int size) {
		this.head = head;
		this.tail = tail;
		this.size = size;
		this.nodeArray = null;
	}
}