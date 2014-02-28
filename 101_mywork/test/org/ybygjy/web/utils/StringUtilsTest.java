package org.ybygjy.web.utils;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StringUtilsTest {
    /** ≤‚ ‘¿‡ µ¿˝ */
    private StringUtils strUtil = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        strUtil = StringUtils.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoReplace() {
        String[] testStr = { "SELECT @QUERY_FIELD@ FROM @TABLE_CODE@ @WHERE@" };
        Map<String, String> paramMap = new HashMap<String, String>() {
            /** serialId */
            private static final long serialVersionUID = -6371674123436350025L;

            {
                put("TABLE_CODE", "tableCode$abc");
                put("QUERY_FIELD", "A,B,C,D");
                put("WHERE", "WHERE 1=1");
            };
        };
        Assert.assertEquals("SELECT A,B,C,D FROM tableCode$abc WHERE 1=1", strUtil.doReplace(testStr[0],
            paramMap));
    }
}
