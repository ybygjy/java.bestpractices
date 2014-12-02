package org.ybygjy.pattern.proxy.test3;
/**
 * Proxy test 3
 * @author WangYanCheng
 * @version 2009-12-31
 */
public class Test3 {
    /**
     * 测试入口
     * @param args arguments list
     */
    public static void main(String[] args) {
        DaoFactory.instance().getDaoInst().update();
    }
}
