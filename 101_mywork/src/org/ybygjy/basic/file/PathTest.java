package org.ybygjy.basic.file;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.ybygjy.basic.TestInterface;
import org.ybygjy.test.TestUtils;

/**
 * 以路径为题的测试
 * @author WangYanCheng
 * @version 2010-10-20
 */
public class PathTest implements TestInterface {
    /** hashMap */
    private Map<String, String> contentMap = new HashMap<String, String>();

    /**
     * {@inheritDoc}
     */
    public void doTest() {
        contentMap.put("user.dir(工作目录)", System.getProperty("user.dir"));
        // 相对路径\绝对路径问题
        // 当前类层级
        contentMap.put("this.getClass().getResource(\"\")", this.getClass().getResource("").toString());
        // 当前classpath类层级
        contentMap.put("this.getClass().getResource(\"/\")", this.getClass().getResource("/").toString());
        contentMap.put("this.getClass().getClassLoader().getResource", this.getClass().getClassLoader()
            .getResource("").toString());
        // 当前classpath类层级
        contentMap.put("Thread.currentThread().getContextClassLoader().getResource", Thread.currentThread()
            .getContextClassLoader().getResource("").toString());
        contentMap.put("Thread.currentThread().getContextClassLoader().getResource2", Thread.currentThread()
            .getContextClassLoader().getResource(".").toString());
        contentMap.put("ClassLoader.getSystemResource", ClassLoader.getSystemResource("").toString());
        TestUtils.doPrint(contentMap);
    }
    public void doTestFile2URL() {
        File fileInst = new File("C:\\Agreement");
        try {
            System.out.println("fileInst.toURL()==>" + fileInst.toURL());
            System.out.println("fileInst.toURL()==>" + fileInst.toURL().getPath());
            System.out.println("fileInst.toURL()==>" + fileInst.toURL().getFile());
            System.out.println("fileInst.toURL()==>" + fileInst.toURL().toString());
            System.out.println("fileInst.toURL()==>" + fileInst.toURL().getAuthority());
            System.out.println("fileInst.toURL()==>" + fileInst.toURL().toURI());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        PathTest pathTest = new PathTest();
        pathTest.doTest();
        //pathTest.doTestFile2URL();
    }
}
