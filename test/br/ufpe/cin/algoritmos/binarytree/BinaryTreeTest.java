package br.ufpe.cin.algoritmos.binarytree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Iterator;

public class BinaryTreeTest {

	protected void insertDegradedSequence(BinaryTree<Integer, Integer> target) {
		insertSequence(target, 1, 2, 3, 4, 5, 6, 7);
	}

	protected void insertBalancedSequence(BinaryTree<Integer, Integer> target) {
		insertSequence(target, 4, 2, 6, 1, 3, 5, 7);
	}

	protected void insertRandomSequence(BinaryTree<Integer, Integer> target) {
		insertSequence(target, 7, 2, 1, 3, 4, 5, 6);
	}

	protected void insertSequence(BinaryTree<Integer, Integer> target,
			Integer... sequence) {
		for (int i = 0; i < sequence.length; i++)
			target.insert(sequence[i], sequence[i]);
	}
	
	protected void insertUpTo(BinaryTree<Integer, Integer> target,
			Integer max){
		for (int i = 1; i <= max ; i++) 
			target.insert(i, i);		
	}
	
	protected void deleteUpTo(BinaryTree<Integer, Integer> target,
			Integer max){
		for (int i = 1; i <= max ; i++) 
			target.delete(i);		
	}

	protected void assertInOrderTransversal(
			BinaryTree<Integer, Integer> target, Integer... sequence) {
		Iterator<Integer> iter = target.inOrder().iterator();
		for (int i = 0; i < sequence.length; i++)
			assertEquals(sequence[i], iter.next());
	}

	protected void assertPreOrderTransversal(
			BinaryTree<Integer, Integer> target, Integer... sequence) {
		Iterator<Integer> iter = target.preOrder().iterator();
		for (int i = 0; i < sequence.length; i++)
			assertEquals(sequence[i], iter.next());
	}

	protected void assertPostOrderTransversal(
			BinaryTree<Integer, Integer> target, Integer... sequence) {
		Iterator<Integer> iter = target.postOrder().iterator();
		for (int i = 0; i < sequence.length; i++)
			assertEquals(sequence[i], iter.next());
	}

	protected void deleteSequence(BinaryTree<Integer, Integer> target,
			Integer... sequence) {
		for (int i = 0; i < sequence.length; i++)
			target.delete(sequence[i]);
	}
	
	
	protected void assertAreInTree(BinaryTree<Integer, Integer> target,
			Integer... sequence) {
		for (int i = 0; i < sequence.length; i++) 
			assertEquals(sequence[i], target.lookup(sequence[i]));
	}
	
	protected void assertAreNotInTree(BinaryTree<Integer, Integer> target,
			Integer... sequence) {
		for (int i = 0; i < sequence.length; i++)
			assertNull(target.lookup(sequence[i]));
	}
}
