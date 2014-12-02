package org.ybygjy.memcached;

import java.util.Calendar;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MemCachedMgrTest {
    /** 参与测试数据长度 */
    private static int TEST_DATA_SIZE = 100;
    /** 参与测试数据 */
    private static String[] tmpKeys = {"KEY_0", "UNKNOW", "KEY_1"};
    /** 参与测试对象 */
    private MemCachedMgr mcmInst;
    /** 服务器地址 */
    private static String[] serverAddr;
    /** 权重 */
    private static Integer[] weights;

    @Before
    public void setUp() {
         serverAddr = new String[]{"172.16.0.75:11211", "localhost:11211"};
        weights = new Integer[] {1};
        mcmInst = new MemCachedMgr(serverAddr, weights);
    }

    @After
    public void tearDown() {
        testFlushAll();
        mcmInst = null;
    }

    @Test
    public void testDelete() {
        testSetStringObject();
        Assert.assertTrue(mcmInst.delete(tmpKeys[0]));
        Assert.assertFalse(mcmInst.delete(tmpKeys[1]));
        Assert.assertTrue(mcmInst.delete(tmpKeys[2]));
    }

    @Test
    public void testGet() {
        testAdd();
        Assert.assertNotNull(mcmInst.get(tmpKeys[0]));
        Assert.assertNull(mcmInst.get(tmpKeys[1]));
        Assert.assertNotNull(mcmInst.get(tmpKeys[2]));
    }

    @Test
    public void testGetMulti() {
        testAdd();
        Map<String, Object> resultMap = mcmInst.getMulti(tmpKeys);
        Assert.assertNotNull(resultMap);
        Assert.assertNotNull(resultMap.get(tmpKeys[0]));
        Assert.assertNull(resultMap.get(tmpKeys[1]));
        Assert.assertNotNull(resultMap.get(tmpKeys[2]));
    }

    @Test
    public void testIsContain() {
        testAdd();
        Assert.assertFalse(mcmInst.isContain(tmpKeys[1]));
        Assert.assertTrue(mcmInst.isContain(tmpKeys[0]));
        Assert.assertTrue(mcmInst.isContain(tmpKeys[2]));
    }

    @Test
    public void testSetStringObject() {
        String[][] tmpStr = MemCachedMgr.getTestData(TEST_DATA_SIZE);
        for (int i = tmpStr.length - 1; i >= 0; i--) {
            Assert.assertTrue(mcmInst.set(tmpStr[i][0], tmpStr[i][1]));
        }
    }

    @Test
    public void testSetStringObjectBoolean() {
        String[][] tmpStr = MemCachedMgr.getTestData(TEST_DATA_SIZE);
        for (int i = tmpStr.length - 1; i >= 0; i--) {
            Assert.assertTrue(mcmInst.set(tmpStr[i][0], tmpStr[i][1], true));
            Assert.assertFalse(mcmInst.set(tmpStr[i][0], tmpStr[i][1], false));
        }
    }

    @Test
    public void testAdd() {
        String[][] tmpStr = MemCachedMgr.getTestData(300);
        for (int i = tmpStr.length - 1; i >= 0; i--) {
            Assert.assertTrue(mcmInst.add(tmpStr[i][0], tmpStr[i][1]));
        }
    }

    @Test
    public void testAdd4Expiry() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        // 增加数据项，指定生存时间为一天(API会自动计算成ms)
        mcmInst.add(tmpKeys[0], tmpKeys[0], cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        mcmInst.add(tmpKeys[2], tmpKeys[2], cal.getTime());
        // 这里用到线程等待用于测试expiry属性的时效性
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(mcmInst.get(tmpKeys[0]), tmpKeys[0]);
        Assert.assertNull(mcmInst.get(tmpKeys[2]));
    }

    @Test
    public void testAddOrIncr() {
        Assert.assertEquals(mcmInst.addOrIncr(tmpKeys[0], 20), 20);
    }

    @Test
    public void testAddOrDecr() {
        testAddOrIncr();
        Assert.assertEquals(mcmInst.addOrDecr(tmpKeys[0], 10), 10);
    }

    @Test
    public void testSyncAll() {
        Assert.fail("未实现");
    }

    @Test
    public void testFlushAll() {
        Assert.assertTrue(mcmInst.flushAll(serverAddr));
    }
}
