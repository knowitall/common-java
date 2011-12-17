package edu.washington.cs.knowitall.commonlib;

import junit.framework.Assert;

import org.junit.Test;


public class RangeTest {
	@Test
	public void testRangeExtend() {
		Range range;
		
		range = Range.EMPTY;
		range = range.extend(5);
		Assert.assertEquals(range, new Range(5));
		
		range = range.extend(6);
		Assert.assertEquals(range, new Range(5, 2));
		
		range = range.extend(4);
		Assert.assertEquals(range, new Range(4, 3));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRangeExtendAtFirstIndex() {
		Range range = new Range(4, 2);
		range.extend(4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRangeExtendAtLastIndex() {
		Range range = new Range(4, 2);
		range.extend(5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRangeExtendInside() {
		Range range = new Range(4, 3);
		range.extend(5);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRangeExtendOutsideLeft() {
		Range range = new Range(4, 2);
		range.extend(2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRangeExtendOutsideRight() {
		Range range = new Range(4, 2);
		range.extend(8);
	}
}
