package edu.washington.cs.knowitall.commonlib;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;


public class ListUtilsTest {

	@Test
	public void testRemoveNulls() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(null);
		list.add(2);
		list.add(3);
		list.add(null);
		list.add(4);
		list.add(null);
		
		ListUtils.removeNulls(list);
		Assert.assertEquals(list.size(), 3);
		Assert.assertEquals((int)list.get(0), 2);
		Assert.assertEquals((int)list.get(1), 3);
		Assert.assertEquals((int)list.get(2), 4);
	}
	
	@Test
	public void testSwapRemove() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		
		ListUtils.swapRemove(list, 0);
		ListUtils.swapRemove(list, 2);
		ListUtils.swapRemove(list, 3);
		
		Assert.assertEquals(list.size(), 3);
		Assert.assertTrue(list.contains(6));
		Assert.assertTrue(list.contains(5));
		Assert.assertTrue(list.contains(2));
	}
}
