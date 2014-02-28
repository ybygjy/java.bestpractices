package org.ybygjy.basic.enumtype;

import org.junit.Before;
import org.junit.Test;
import org.ybygjy.basic.TestInterface;

public class EnumTestTest {
    /**enumTest*/
    private TestInterface etInst = null;
    @Before
    public void setUp() throws Exception {
        etInst = new EnumTest();
    }

    @Test
    public void testDoTest() {
        etInst.doTest();
    }
}
