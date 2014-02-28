package org.ybygjy.web.utils;

import org.junit.Assert;
import org.junit.Test;

public class ThreadLocalMapTest {
    @Test
    public void testGetContext() {
        Assert.assertNotNull(ThreadLocalMap.getContext());
    }

    @Test
    public void testPut() {
        ThreadLocalMap.put("Key1", "Value1");
        ThreadLocalMap.put("Key2", "Value2");
        Assert.assertNotNull(ThreadLocalMap.get("Key1"));
        Assert.assertNotNull(ThreadLocalMap.get("Key2"));
    }

    @Test
    public void testGet() {
        Assert.assertNotNull(ThreadLocalMap.get("Key1"));
    }

    @Test
    public void testRemove() {
        ThreadLocalMap.put("Key1", "Value1");
        Assert.assertNotNull(ThreadLocalMap.remove("Key1"));
    }

    @Test
    public void testClear() {
        ThreadLocalMap.clear();
        Assert.assertTrue(ThreadLocalMap.getContext().size() == 0);
    }

}
