package org.ybygjy;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * ≤Œ ˝ªØ≤‚ ‘
 * @author WangYanCheng
 * @version 2010-12-8
 */
@RunWith(Parameterized.class)
public class ParameterTest {
    private String expected;
    private String target;
    public ParameterTest(String expected, String target) {
        this.expected = expected;
        this.target = target;
    }
    @Parameters
    public static Collection words() {
        return Arrays.asList(new Object[][]{
            {"EMP_INFO", "EMP_INFO"},
            {null, null},
            {"", ""},
            {"A", "A"}
        });
    }
    @Test
    public void doTest() {
        assertEquals(expected, SimpleTest.wordFormat(target));
    }
}
