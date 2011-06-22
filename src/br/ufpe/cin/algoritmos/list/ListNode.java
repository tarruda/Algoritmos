package br.ufpe.cin.algoritmos.list;

class ListNode<T> {
	T value;
	ListNode<T> next;

	public ListNode(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value != null ? value.toString()
				+ (next != null ? "," + next.toString() : "") : "null";
	}
}
