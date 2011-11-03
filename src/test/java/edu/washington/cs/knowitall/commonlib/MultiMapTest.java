package edu.washington.cs.knowitall.commonlib;

import junit.framework.Assert;

import org.junit.Test;


public class MultiMapTest {
	@Test
	public void test() {
        MultiMap<String, String> multi = new MultiMap<String, String>();
        multi.put("one", "two");
        multi.put("one", "three");
        multi.put("one", "four");
        multi.put("one", "four");
        multi.put("two", "five");

        Assert.assertEquals(3, multi.get("one").size());
        Assert.assertEquals(1, multi.get("two").size());
	}
}
