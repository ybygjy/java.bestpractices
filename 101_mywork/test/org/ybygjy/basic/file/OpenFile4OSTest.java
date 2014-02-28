package org.ybygjy.basic.file;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试文件打开
 * @author WangYanCheng
 * @version 2011-10-18
 */
public class OpenFile4OSTest {
    private OpenFile4OS of4OsInst;
    @Before
    public void setUp() throws Exception {
        of4OsInst = new OpenFile4OS();
    }

    @After
    public void tearDown() throws Exception {
        of4OsInst = null;
    }

    @Test
    public void testDoOpenFile() {
        File opendFile = new File("C:\\build\\DBCompare.LOG");
        try {
            Assert.assertTrue(of4OsInst.doOpenFile(opendFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDoOpenURL() {
        fail("Not yet implemented");
    }

}
