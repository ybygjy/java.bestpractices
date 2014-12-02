package org.ybygjy.basic.properties;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * 外部配置加载测试
 * <p>1、配置文件名称格式baseName_语言代码_国家代码.properties</p>
 * <p>2、注意native2assii</p>
 * @author WangYanCheng
 * @version 2009-12-7
 */
public class PropertiesTest {
    /**resource boundle inst*/
    private static ResourceBundle resBoundInst = null;
    /**
     * constructor
     */
    public PropertiesTest() {
        resBoundInst =
            ResourceBundle.getBundle("org.ybygjy.basic.properties.defaults");
    }
    /**
     * show parameters
     */
    public void doShowParameters() {
        Enumeration enumObj = resBoundInst.getKeys();
        while (enumObj.hasMoreElements()) {
            String key = (String) enumObj.nextElement();
            System.out.println(key + ":" + resBoundInst.getString(key));
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        PropertiesTest proTestInst = new PropertiesTest();
        proTestInst.doShowParameters();
    }
}
