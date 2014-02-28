package org.ybygjy.testunit;

import junit.framework.Assert;
import junit.framework.TestCase;

public class JUnit3SimpleTest extends TestCase {
    public void testSayHello() {
        Assert.assertEquals(1, 1);
    }
}
