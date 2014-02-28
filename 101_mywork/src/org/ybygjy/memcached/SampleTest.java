package org.ybygjy.memcached;

import java.io.Serializable;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * Memcached最佳实践
 * <p>1、Memcached连接</p>
 * @author WangYanCheng
 * @version 2011-5-6
 */
public class SampleTest {
    /**Memcached服务器地址*/
    public static final String[] SERVER_ADDRS= {"172.16.5.37:11211"};
    /**权重*/
    public static Integer[] weights = {1};
    /**MemCached连接*/
    private MemCachedClient mccInst;
    private SockIOPool sockPool;
    /**参与测试用基础数据*/
    private String[][] testData;
    /**参与测试用基础对象数据*/
    private TestEntity[] testData4Obj;
    /**
     * 构造方法，用于初始化测试环境
     */
    public SampleTest() {
        mccInst = new MemCachedClient();
        sockPool = SockIOPool.getInstance(true);
        sockPool.setServers(SERVER_ADDRS);
        sockPool.setWeights(weights);
        sockPool.initialize();
        generalTestData(10);
    }
    /**
     * 构建测试用基础数据
     * @param size 数据集大小
     */
    public void generalTestData(int size) {
        testData = new String[size][2];
        testData4Obj = new TestEntity[size];
        for (int i = size - 1; i>=0; i--) {
            testData[i][0] = "Key_" + i;
            testData[i][1] = "Value——" + Math.random();
            testData4Obj[i] = new TestEntity("KeyObj_" + i, "第" + i + "个对象");
        }
    }
    /**
     * 测试set操作
     */
    public void testSet() {
        for (int i = testData.length - 1; i >= 0; i--) {
            mccInst.set(testData[i][0], testData[i][1]);
        }
    }
    /**
     * 测试set对象操作
     */
    public void testSetObj() {
        for (int i = testData4Obj.length - 1; i >=0; i--) {
            mccInst.set(testData4Obj[i].getSerialCode(), testData4Obj[i]);
        }
    }
    /**
     * 测试get操作
     */
    public void testGet() {
        Object[] obj = new Object[testData.length];
        for (int i = obj.length - 1; i >= 0; i--) {
            obj[i] = mccInst.get(testData[i][0]);
        }
        for (int i = 0; i < obj.length; i++) {
            System.out.println(testData[i][0] + "\t" + obj[i]);
        }
    }
    /**
     * 测试get操作
     */
    public void testGetObj() {
        Object[] obj = new Object[testData4Obj.length];
        for (int i = 0; i < testData4Obj.length; i++) {
            obj[i] = mccInst.get(testData4Obj[i].getSerialCode());
        }
        for (int i = 0; i < obj.length; i++) {
            System.out.println(testData[i][0] + "\t" + obj[i]);
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        SampleTest st = new SampleTest();
        st.testSet();
        st.testGet();
        st.testSetObj();
        st.testGetObj();
    }
}
/**
 * 参与测试的临时对象
 * @author WangYanCheng
 * @version 2011-5-6
 */
class TestEntity implements Serializable {
    /**
     * serialVersion
     */
    private static final long serialVersionUID = 4011683150758956871L;
    /**编码*/
    private String serialCode;
    /**信息描述*/
    private String testInfo;
    public TestEntity(String serialCode, String testInfo) {
        super();
        this.serialCode = serialCode;
        this.testInfo = testInfo;
    }
    
    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getTestInfo() {
        return testInfo;
    }

    public void setTestInfo(String testInfo) {
        this.testInfo = testInfo;
    }

    @Override
    public String toString() {
        return "TestEntity [serialCode=" + serialCode + ", testInfo=" + testInfo + "]";
    }
}
