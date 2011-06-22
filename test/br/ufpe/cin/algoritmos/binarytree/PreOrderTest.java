package br.ufpe.cin.algoritmos.binarytree;

import org.junit.Before;
import org.junit.Test;

public class PreOrderTest extends TransversalTest {

	private PreOrder<Integer, Integer> balancedIterator;
	private PreOrder<Integer, Integer> unbalancedIterator;

	@Before
	public void setUp() {
		balancedIterator = new PreOrder<Integer, Integer>(getBalancedTree());
		unbalancedIterator = new PreOrder<Integer, Integer>(getUnbalancedTree());
	}

	@Test
	public void testBalancedIterator() {
		assertTransversal(balancedIterator, 4, 2, 1, 3, 6, 5, 7);
	}

	@Test
	public void testUnbalancedIterator() {
		assertTransversal(unbalancedIterator, 1, 2, 3, 4, 5, 6, 7);
	}
}
