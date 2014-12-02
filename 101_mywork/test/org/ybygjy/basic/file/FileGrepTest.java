package org.ybygjy.basic.file;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试用例
 * @author WangYanCheng
 * @version 2011-11-1
 */
public class FileGrepTest {
    private FileGrep fileGrep;
    @Before
    public void setUp() throws Exception {
        fileGrep = FileGrep.getInst(Charset.forName("UTF-8"));
    }

    @After
    public void tearDown() throws Exception {
        fileGrep = null;
    }

    @Test
    public void testGetInst() {
        fail("Not yet implemented");
    }

    @Test
    public void testDoWork() {
        String pattern = "@depJs\\s+((\\w+\\.?)+\\w+)\\r?\\n?";
        File fileInst = FileUtils.lookup(this.getClass(), "Nstc.webgate.Test.js");
        String[] tmpResult = null;
        try {
            tmpResult = fileGrep.findResult4Regexp(pattern, fileInst, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null != tmpResult) {
            for (String tmpStr : tmpResult) {
                System.out.println(tmpStr);
            }
        }
    }

}
