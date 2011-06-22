package br.ufpe.cin.algoritmos.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LinkedListTest {

	private LinkedList<Integer> target;

	@Before
	public void setUp() throws Exception {
		target = new LinkedList<Integer>();
	}

	@Test
	public void size() {
		assertEquals(0, target.size());
	}

	@Test
	public void isempty() {
		assertTrue(target.isEmpty());
	}

	@Test
	public void clear_filledlist() {
		addSequence(target, 1, 3, 4, 5);
		assertEquals(4, target.size());
		target.clear();
		assertEquals(0, target.size());
		int iterations = 0;
		Iterator<Integer> iter = target.iterator();
		while (iter.hasNext()) {
			iter.next();
			iterations++;
		}
		assertEquals(0, iterations);
	}

	@Test
	public void add() {
		addSequence(target, 1, 4, 6, 7, 3, 7, 8);
		assertIteration(target, 1, 4, 6, 7, 3, 7, 8);
		assertEquals(7, target.size());
		assertFalse(target.isEmpty());
	}

	@Test
	public void add_atindex() {
		addSequence(target, 1, 4, 6, 7, 3, 7, 8);
		target.add(3, 55);
		target.add(3, 66);
		target.add(7, 1000);
		assertIteration(target, 1, 4, 6, 66, 55, 7, 3, 1000, 7, 8);
		assertEquals(10, target.size());
	}

	@Test
	public void add_atindex_emptylist() {
		target.add(0, 55);
		target.add(1, 66);
		target.add(0, 1000);
		assertIteration(target, 1000, 55, 66);
		assertEquals(3, target.size());
	}

	@Test
	public void addAll() {
		target.addAll(collectionFor(4, 3, 5, 2, 3, 766, 3));
		assertIteration(target, 4, 3, 5, 2, 3, 766, 3);
		assertEquals(7, target.size());
	}

	@Test
	public void addAll_atindex_filledlist() {
		addSequence(target, 1, 2, 3, 8, 9);
		target.addAll(3, collectionFor(4, 5, 6, 7));
		assertIteration(target, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		assertEquals(9, target.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void addAll_atindex_outofbounds() {
		target.addAll(2, collectionFor(4, 2, 5, 67, 8));
	}

	@Test
	public void addAll_atindex_lastIndex_filledlist() {
		addSequence(target, 1, 2, 3);
		target.addAll(3, collectionFor(4, 5, 6));
		assertIteration(target, 1, 2, 3, 4, 5, 6);
	}

	@Test
	public void addAll_atindex_firstindex_filledlist() {
		addSequence(target, 1, 2, 3);
		target.addAll(0, collectionFor(4, 5, 6));
		assertIteration(target, 4, 5, 6, 1, 2, 3);
	}


	protected void addSequence(List<Integer> target, Integer... sequence) {
		for (int i = 0; i < sequence.length; i++)
			target.add(sequence[i]);
	}

	protected void assertIteration(List<Integer> target, Integer... sequence) {
		int pos = 0;
		for (Integer i : target) {
			assertEquals(sequence[pos], i);
			pos++;
		}
		assertEquals(sequence.length, pos);
	}

	private Collection<Integer> collectionFor(Integer... sequence) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (Integer integer : sequence)
			ret.add(integer);
		return ret;
	}
}
