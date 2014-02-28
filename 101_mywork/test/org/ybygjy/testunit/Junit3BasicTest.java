package org.ybygjy.testunit;

import org.junit.Assert;

import junit.framework.TestCase;

public class Junit3BasicTest extends TestCase {
    public void testArithmetic() {
        int a = 0;
        long b = 0l;
        Assert.assertEquals(a, b);
    }
}
