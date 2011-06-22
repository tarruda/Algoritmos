package br.ufpe.cin.algoritmos.binarytree;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AVLTreeTest extends BinaryTreeTest {

	private AVLTree<Integer, Integer> target;

	@Before
	public void setUp() throws Exception {
		target = new AVLTree<Integer, Integer>();
	}

	@Test
	public void degraded_transversal() {
		insertDegradedSequence(target);
		assertInOrderTransversal(target, 1, 2, 3, 4, 5, 6, 7);
		assertPreOrderTransversal(target, 4, 2, 1, 3, 6, 5, 7);
		assertPostOrderTransversal(target, 1, 3, 2, 5, 7, 6, 4);
	}

	@Test
	public void balanced_transversal() {
		insertBalancedSequence(target);
		assertInOrderTransversal(target, 1, 2, 3, 4, 5, 6, 7);
		assertPreOrderTransversal(target, 4, 2, 1, 3, 6, 5, 7);
		assertPostOrderTransversal(target, 1, 3, 2, 5, 7, 6, 4);
	}

	@Test
	public void random_transversal() {
		insertRandomSequence(target);
		assertInOrderTransversal(target, 1, 2, 3, 4, 5, 6, 7);
		assertPreOrderTransversal(target, 4, 2, 1, 3, 6, 5, 7);
		assertPostOrderTransversal(target, 1, 3, 2, 5, 7, 6, 4);
	}

	@Test
	public void degraded_height() {
		insertDegradedSequence(target);
		assertEquals(2, target.getHeight());
	}

	@Test
	public void balanced_height() {
		insertBalancedSequence(target);
		assertEquals(2, target.getHeight());
	}

	@Test
	public void random_height() {
		insertRandomSequence(target);
		assertEquals(2, target.getHeight());
	}

	@Test
	public void random_height_after_delete() {
		insertRandomSequence(target);
		target.delete(4);
		assertEquals(2, target.getHeight());
	}

	@Test
	public void balanced_height_after_delete() {
		insertBalancedSequence(target);
		deleteSequence(target, 1, 7);
		assertEquals(2, target.getHeight());
		target.delete(4);
		assertEquals(2, target.getHeight());
		target.delete(3);		
		assertEquals(1, target.getHeight());
		target.delete(5);		
		assertEquals(1, target.getHeight());
	}

	@Test
	public void degraded_height_after_delete() {
		insertDegradedSequence(target);
		deleteSequence(target, 1, 2, 3, 4);
		assertEquals(1, target.getHeight());
	}

	@Test
	public void balanced_lookup_after_delete() {
		insertBalancedSequence(target);
		deleteSequence(target, 1, 7);
		assertAreNotInTree(target, 1, 7);
		assertAreInTree(target, 2, 3, 4, 5, 6);
	}

	@Test
	public void random_lookup_after_delete() {
		insertRandomSequence(target);
		deleteSequence(target, 1, 7);
		assertAreNotInTree(target, 1, 7);
		assertAreInTree(target, 2, 3, 4, 5, 6);
	}

	@Test
	public void degraded_lookup_after_delete() {
		insertDegradedSequence(target);
		deleteSequence(target, 1, 7);
		assertAreNotInTree(target, 1, 7);
		assertAreInTree(target, 2, 3, 4, 5, 6);
	}

	@Test
	public void hugetree_insert_lookup_delete_benchmark() {
		insertUpTo(target, 8000);
		target.lookup(8000);
		target.lookup(2500);
		target.lookup(12);
		deleteUpTo(target, 8000);
	}

	@Test
	public void bigtree_delete_stress() {
		insertUpTo(target, 27);

		assertEquals(27, target.getCount());

		assertInOrderTransversal(target, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
				13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
		assertPreOrderTransversal(target, 16, 8, 4, 2, 1, 3, 6, 5, 7, 12, 10,
				9, 11, 14, 13, 15, 20, 18, 17, 19, 24, 22, 21, 23, 26, 25, 27);
		assertPostOrderTransversal(target, 1, 3, 2, 5, 7, 6, 4, 9, 11, 10, 13,
				15, 14, 12, 8, 17, 19, 18, 21, 23, 22, 25, 27, 26, 24, 20, 16);

		deleteSequence(target, 16, 15, 14);
		assertEquals(24, target.getCount());
		assertAreNotInTree(target, 14, 15, 16);
		assertAreInTree(target, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 17,
				18, 19, 20, 21, 22, 23, 24, 25, 26, 27);
		assertPreOrderTransversal(target, 13, 8, 4, 2, 1, 3, 6, 5, 7, 10, 9,
				12, 11, 20, 18, 17, 19, 24, 22, 21, 23, 26, 25, 27);
		assertEquals(4, target.getHeight());

		deleteSequence(target, 8, 7, 1, 3, 13, 20, 21, 23, 25, 27);
		assertEquals(14, target.getCount());
		assertPostOrderTransversal(target, 2, 5, 4, 9, 11, 10, 6, 17, 18, 22,
				26, 24, 19, 12);
		assertEquals(3, target.getHeight());

		deleteSequence(target, 10);
		assertPostOrderTransversal(target, 2, 5, 4, 11, 9, 6, 17, 18, 22, 26,
				24, 19, 12);
	}

}
