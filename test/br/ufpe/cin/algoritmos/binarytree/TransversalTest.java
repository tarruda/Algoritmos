package br.ufpe.cin.algoritmos.binarytree;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

public class TransversalTest {

	protected void assertTransversal(Iterator<TreeNode<Integer, Integer>> target,
			Integer... sequence) {		
		for (int i = 0; i < sequence.length; i++)
			assertEquals(sequence[i], target.next().getValue());
	}
	

	protected TreeNode<Integer, Integer> getBalancedTree() {
		TreeNode<Integer, Integer> n1 = new TreeNode<Integer, Integer>(4, 4);
		TreeNode<Integer, Integer> n2 = new TreeNode<Integer, Integer>(2, 2);
		TreeNode<Integer, Integer> n3 = new TreeNode<Integer, Integer>(6, 6);
		TreeNode<Integer, Integer> n4 = new TreeNode<Integer, Integer>(1, 1);
		TreeNode<Integer, Integer> n5 = new TreeNode<Integer, Integer>(3, 3);
		TreeNode<Integer, Integer> n6 = new TreeNode<Integer, Integer>(5, 5);
		TreeNode<Integer, Integer> n7 = new TreeNode<Integer, Integer>(7, 7);
		n1.setLeft(n2);
		n1.setRight(n3);
		n2.setLeft(n4);
		n2.setRight(n5);
		n3.setLeft(n6);
		n3.setRight(n7);
		return n1;
	}

	protected TreeNode<Integer, Integer> getUnbalancedTree() {
		TreeNode<Integer, Integer> n1 = new TreeNode<Integer, Integer>(1, 1);
		TreeNode<Integer, Integer> n2 = new TreeNode<Integer, Integer>(2, 2);
		TreeNode<Integer, Integer> n3 = new TreeNode<Integer, Integer>(3, 3);
		TreeNode<Integer, Integer> n4 = new TreeNode<Integer, Integer>(4, 4);
		TreeNode<Integer, Integer> n5 = new TreeNode<Integer, Integer>(5, 5);
		TreeNode<Integer, Integer> n6 = new TreeNode<Integer, Integer>(6, 6);
		TreeNode<Integer, Integer> n7 = new TreeNode<Integer, Integer>(7, 7);
		n1.setRight(n2);
		n2.setRight(n3);
		n3.setRight(n4);
		n4.setRight(n5);
		n5.setRight(n6);
		n6.setRight(n7);
		return n1;
	}
}
