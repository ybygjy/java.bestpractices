package org.ybygjy.basic.collect.list;

import org.junit.Before;
import org.junit.Test;
import org.ybygjy.basic.collect.ListTest;

public class ListTestTest {
    /**instance*/
    private ListTest ltInst = null;
    @Before
    public void setUp() throws Exception {
        ltInst = new ListTest();
    }

    @Test
    public void testDoTestToArray() {
        ltInst.doTestToArray();
    }
}
