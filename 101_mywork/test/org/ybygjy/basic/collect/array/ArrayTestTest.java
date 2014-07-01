package org.ybygjy.basic.collect.array;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ybygjy.basic.collect.ArrayTest;

public class ArrayTestTest {

    /** test src instance */
    private ArrayTest atInst = null;

    @Before
    public void setUp() throws Exception {
        atInst = new ArrayTest();
    }

    @Test
    public void testDoTestSysArrCopy() {
        List<String> tmpList = new ArrayList<String>();
        tmpList.add("A");
        tmpList.add("B");
        atInst.doTestSysArrCopy();
        System.out.println(tmpList.toArray(new String[tmpList.size()])[0]);
    }
    @Test
    public void testdoTestSysArrCopy2() {
        atInst.doTestSysArrCopy2();
    }
    @Test
    public void testDoTestTwoDimensionTest() {
        atInst.doTestTwoDimensionTest();
    }
}
