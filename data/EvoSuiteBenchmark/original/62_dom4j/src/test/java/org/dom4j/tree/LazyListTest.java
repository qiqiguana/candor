/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dom4j.tree;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ListIterator;

import static org.testng.AssertJUnit.*;

/**
 * @author Filip Jirsák
 */
@Test
public class LazyListTest {

	/**
	 * Test of add method, of class LazyList.
	 */
	@Test
	public void testAdd_1() {
		LazyList<Integer> list = new LazyList<Integer>();
		boolean result = list.add(1);
		assertEquals(true, result);
		assertEquals(1, list.size());
	}

	/**
	 * Test of add method, of class LazyList.
	 */
	@Test
	public void testAdd_2() {
		LazyList<Integer> list = new LazyList<Integer>();
		boolean result = list.add(1);
		assertEquals(true, result);
		result = list.add(2);
		assertEquals(true, result);
		assertEquals(2, list.size());
	}

	/**
	 * Test of addAll method, of class LazyList.
	 */
	@Test
	public void testAddAll_Collection() {
		Collection<Integer> collection = new ArrayList<Integer>(6);
		Collections.addAll(collection, 0, 1, 2, 3, 4, 5);
		LazyList<Integer> instance = new LazyList<Integer>();
		boolean result = instance.addAll(collection);
		assertEquals(true, result);
		assertEquals(collection.size(), instance.size());
	}
	//	/**
//	 * Test of addAll method, of class LazyList.
//	 */
//	@Test
//	public void testAddAll_int_Collection() {
//		System.out.println("addAll");
//		int index = 0;
//		Collection<? extends E> collection = null;
//		LazyList instance = new LazyList();
//		boolean expResult = false;
//		boolean result = instance.addAll(index, collection);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//

	/**
	 * Test of clear method, of class LazyList.
	 */
	@Test
	public void testClear() {
		LazyList<Integer> list = createList(5);
		list.clear();
		assertEquals(true, list.isEmpty());
		assertEquals(0, list.size());
	}

	/**
	 * Test of get method, of class LazyList.
	 */
	@Test
	public void testGet() {
		LazyList<Integer> list = createList(5);
		assertEquals(0, list.get(0).intValue());
		assertEquals(1, list.get(1).intValue());
		assertEquals(2, list.get(2).intValue());
		assertEquals(3, list.get(3).intValue());
		assertEquals(4, list.get(4).intValue());
	}

	/**
	 * Test of listIterator method, of class LazyList.
	 */
	@Test
	public void testListIterator() {
		ListIterator<Integer> iterator = createList(5).listIterator();
		for (int i = 0; i < 5; i++) {
			assertTrue(iterator.hasNext());
			assertEquals(i, iterator.next().intValue());
		}
		assertFalse(iterator.hasNext());
	}

	/**
	 * Test of remove method, of class LazyList.
	 */
	@Test
	public void testRemove_1() {
		LazyList<Integer> list = createList(5);
		list.remove(0);
		assertEquals(4, list.size());
		assertEquals(1, list.get(0).intValue());
		assertEquals(2, list.get(1).intValue());
		assertEquals(3, list.get(2).intValue());
		assertEquals(4, list.get(3).intValue());
	}

	/**
	 * Test of remove method, of class LazyList.
	 */
	@Test
	public void testRemove_2() {
		LazyList<Integer> list = createList(5);
		list.remove(2);
		assertEquals(4, list.size());
		assertEquals(0, list.get(0).intValue());
		assertEquals(1, list.get(1).intValue());
		assertEquals(3, list.get(2).intValue());
		assertEquals(4, list.get(3).intValue());
	}

	/**
	 * Test of remove method, of class LazyList.
	 */
	@Test
	public void testRemove_3() {
		LazyList<Integer> list = createList(5);
		list.remove(4);
		assertEquals(4, list.size());
		assertEquals(0, list.get(0).intValue());
		assertEquals(1, list.get(1).intValue());
		assertEquals(2, list.get(2).intValue());
		assertEquals(3, list.get(3).intValue());
	}

//	/**
//	 * Test of removeRange method, of class LazyList.
//	 */
//	@Test
//	public void testRemoveRange() {
//		System.out.println("removeRange");
//		int fromIndex = 0;
//		int toIndex = 0;
//		LazyList instance = new LazyList();
//		instance.removeRange(fromIndex, toIndex);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of set method, of class LazyList.
//	 */
//	@Test
//	public void testSet() {
//		System.out.println("set");
//		int index = 0;
//		E element = null;
//		LazyList instance = new LazyList();
//		E expResult = null;
//		E result = instance.set(index, element);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of subList method, of class LazyList.
//	 */
//	@Test
//	public void testSubList() {
//		System.out.println("subList");
//		int fromIndex = 0;
//		int toIndex = 0;
//		LazyList instance = new LazyList();
//		List<E> expResult = null;
//		List<E> result = instance.subList(fromIndex, toIndex);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of size method, of class LazyList.
//	 */
//	@Test
//	public void testSize() {
//		System.out.println("size");
//		LazyList instance = new LazyList();
//		int expResult = 0;
//		int result = instance.size();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of createIndexedList method, of class LazyList.
//	 */
//	@Test
//	public void testCreateIndexedList() {
//		System.out.println("createIndexedList");
//		LazyList instance = new LazyList();
//		instance.createIndexedList();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of addElement method, of class LazyList.
//	 */
//	@Test
//	public void testAddElement() {
//		System.out.println("addElement");
//		E e = null;
//		Entry<E> entry = null;
//		LazyList instance = new LazyList();
//		Entry<E> expResult = null;
//		Entry<E> result = instance.addElement(e, entry);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getEntry method, of class LazyList.
//	 */
//	@Test
//	public void testGetEntry() {
//		System.out.println("getEntry");
//		int index = 0;
//		LazyList instance = new LazyList();
//		Entry<E> expResult = null;
//		Entry<E> result = instance.getEntry(index);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of removeEntry method, of class LazyList.
//	 */
//	@Test
//	public void testRemoveEntry() {
//		System.out.println("removeEntry");
//		Entry<E> entry = null;
//		LazyList instance = new LazyList();
//		E expResult = null;
//		E result = instance.removeEntry(entry);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}

	protected LazyList<Integer> createList(int count) {
		LazyList<Integer> list = new LazyList<Integer>();
		for (int i = 0; i < count; i++) {
			list.add(i);
		}
		return list;
	}
}