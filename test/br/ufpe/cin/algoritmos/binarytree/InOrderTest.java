package br.ufpe.cin.algoritmos.binarytree;

import org.junit.Before;
import org.junit.Test;

public class InOrderTest extends TransversalTest {

	private InOrder<Integer, Integer> balancedIterator;
	private InOrder<Integer, Integer> unbalancedIterator;

	@Before
	public void setUp() {
		balancedIterator = new InOrder<Integer, Integer>(getBalancedTree());
		unbalancedIterator = new InOrder<Integer, Integer>(getUnbalancedTree());
	}

	@Test
	public void testBalancedIterator() {
		assertTransversal(balancedIterator, 1, 2, 3, 4, 5, 6, 7);
	}

	@Test
	public void testUnbalancedIterator() {
		assertTransversal(unbalancedIterator, 1, 2, 3, 4, 5, 6, 7);
	}
}
