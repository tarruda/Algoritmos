package br.ufpe.cin.algoritmos.binarytree;

import java.util.Iterator;

abstract class TreeTransversal<TKey extends Comparable<TKey>, TValue>
		implements Iterator<TreeNode<TKey, TValue>> {

	private Instruction[] instructionStack;
	private int sp;

	public TreeTransversal(TreeNode<TKey, TValue> root) {
		sp = -1;
		instructionStack = initializeStack();
		pushTransverse(root);
	}

	@Override
	public boolean hasNext() {
		return sp > -1;
	}

	@Override
	public TreeNode<TKey, TValue> next() {
		while (transverse())
			;
		return visit();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	protected void pushTransverse(TreeNode<TKey, TValue> node) {
		if (node != null) {
			Instruction inst = instructionStack[++sp];
			inst.node = node;
			inst.type = InstructionType.TRANSVERSE;
		}
	}

	protected void pushVisit(TreeNode<TKey, TValue> node) {
		if (node != null) {
			Instruction inst = instructionStack[++sp];
			inst.node = node;
			inst.type = InstructionType.VISIT;
		}
	}

	protected abstract void doTransversal(TreeNode<TKey, TValue> node);

	@SuppressWarnings("unchecked")
	private boolean transverse() {
		boolean ret = false;
		Instruction inst = pop();
		if (inst.type == InstructionType.TRANSVERSE) {
			ret = true;
			doTransversal((TreeNode<TKey, TValue>) inst.node);
		} else
			pushVisit((TreeNode<TKey, TValue>) inst.node);
		return ret;
	}

	@SuppressWarnings("unchecked")
	private TreeNode<TKey, TValue> visit() {
		return (TreeNode<TKey, TValue>) pop().node;
	}

	private Instruction pop() {
		return instructionStack[sp--];
	}

	private Instruction[] initializeStack() {
		Instruction[] ret = new Instruction[64];
		for (int i = 0; i < ret.length; i++)
			ret[i] = new Instruction();
		return ret;
	}

	private static class Instruction {
		public TreeNode<?, ?> node;
		public InstructionType type;
	}

	private static enum InstructionType {
		TRANSVERSE, VISIT
	}
}
