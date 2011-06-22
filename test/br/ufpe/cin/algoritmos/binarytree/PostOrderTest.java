package br.ufpe.cin.algoritmos.binarytree;

import org.junit.Before;
import org.junit.Test;

public class PostOrderTest extends TransversalTest {

	private PostOrder<Integer, Integer> balancedIterator;
	private PostOrder<Integer, Integer> unbalancedIterator;

	@Before
	public void setUp() {
		balancedIterator = new PostOrder<Integer, Integer>(getBalancedTree());
		unbalancedIterator = new PostOrder<Integer, Integer>(
				getUnbalancedTree());
	}

	@Test
	public void testbalancedIterator() {
		assertTransversal(balancedIterator, 1, 3, 2, 5, 7, 6, 4);
	}

	@Test
	public void testUnbalancedIterator() {
		assertTransversal(unbalancedIterator, 7, 6, 5, 4, 3, 2, 1);
	}
}
