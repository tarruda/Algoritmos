package br.ufpe.cin.algoritmos.binarytree;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SearchTreeTest extends BinaryTreeTest {

	private SearchTree<Integer, Integer> target;

	@Before
	public void setUp() throws Exception {
		target = new SearchTree<Integer, Integer>();
	}

	@Test
	public void degraded_transversal() {
		insertDegradedSequence(target);
		assertInOrderTransversal(target, 1, 2, 3, 4, 5, 6, 7);
		assertPreOrderTransversal(target, 1, 2, 3, 4, 5, 6, 7);
		assertPostOrderTransversal(target, 7, 6, 5, 4, 3, 2, 1);
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
		assertPreOrderTransversal(target, 7, 2, 1, 3, 4, 5, 6);
		assertPostOrderTransversal(target, 1, 6, 5, 4, 3, 2, 7);
	}

	@Test
	public void degraded_height() {
		insertDegradedSequence(target);
		assertEquals(6, target.getHeight());
	}

	@Test
	public void balanced_height() {
		insertBalancedSequence(target);
		assertEquals(2, target.getHeight());
	}

	@Test
	public void random_height() {
		insertRandomSequence(target);
		assertEquals(5, target.getHeight());
	}

	@Test
	public void random_height_after_delete() {
		insertRandomSequence(target);
		target.delete(4);
		assertEquals(4, target.getHeight());
	}

	@Test
	public void balanced_height_after_delete() {
		insertBalancedSequence(target);
		target.delete(1);
		target.delete(7);
		assertEquals(2, target.getHeight());
		target.delete(4);
		assertEquals(2, target.getHeight());
		target.delete(3);
		assertEquals(2, target.getHeight());
		target.delete(5);
		assertEquals(1, target.getHeight());
	}

	@Test
	public void degraded_height_after_delete() {
		insertDegradedSequence(target);
		target.delete(1);
		target.delete(2);
		target.delete(3);
		assertEquals(3, target.getHeight());
	}

	@Test
	public void degraded_updates() {
		insertDegradedSequence(target);
		target.update(1, 7);
		target.update(7, 7);
		assertEquals(7, target.lookup(1).intValue());
		assertEquals(7, target.lookup(7).intValue());
	}

	@Test
	public void balanced_updates() {
		insertBalancedSequence(target);
		target.update(6, 7);
		target.update(3, 8);
		assertEquals(7, target.lookup(6).intValue());
		assertEquals(8, target.lookup(3).intValue());
	}

	@Test
	public void random_updates() {
		insertRandomSequence(target);
		target.update(5, 7);
		target.update(5, 7);
		assertEquals(7, target.lookup(5).intValue());
		assertEquals(7, target.lookup(5).intValue());
	}

	@Test
	public void balanced_lookups() {
		insertBalancedSequence(target);
		assertEquals(3, target.lookup(3).intValue());
		assertEquals(6, target.lookup(6).intValue());
	}

	@Test
	public void random_lookups() {
		insertRandomSequence(target);
		assertEquals(3, target.lookup(3).intValue());
		assertEquals(2, target.lookup(2).intValue());
	}

	@Test
	public void degraded_lookups() {
		insertDegradedSequence(target);
		assertEquals(1, target.lookup(1).intValue());
		assertEquals(5, target.lookup(5).intValue());
	}

	@Test
	public void balanced_lookup_after_delete() {
		insertBalancedSequence(target);
		deleteSequence(target, 1, 7);
		assertAreInTree(target, 2, 6);
		deleteSequence(target, 4, 3);
		assertAreInTree(target, 2, 6, 5);
		assertAreNotInTree(target, 1, 7);
	}

	@Test
	public void random_lookup_after_delete() {
		insertRandomSequence(target);
		deleteSequence(target, 1, 7, 4, 3);
		assertAreNotInTree(target, 1, 7);
		assertAreInTree(target, 2, 5, 6);

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
		int maxVal = 1000;
		int midVal = 500;
		int minVal = 12;

		insertUpTo(target, maxVal);
		target.lookup(maxVal);
		target.lookup(midVal);
		target.lookup(minVal);
		deleteUpTo(target, maxVal);
	}
}
