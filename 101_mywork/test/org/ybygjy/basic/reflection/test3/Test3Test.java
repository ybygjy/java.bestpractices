package org.ybygjy.basic.reflection.test3;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class Test3Test {
    /**innerInst*/
    private Test3 t3Inst;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        t3Inst = new Test3();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoTest() {
        t3Inst.doTest();
    }
}
