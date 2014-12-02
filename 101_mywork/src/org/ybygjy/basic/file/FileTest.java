package org.ybygjy.basic.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * FileTest
 * @author WangYanCheng
 * @version 2009-12-4
 */
public class FileTest {
    /** inner DebugInfo */
    private Map<String, Object> contextInfo = new HashMap<String, Object>();

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        InnerTest innerTestInst = new FileTest().getInnerTest();
        innerTestInst.doTestGetFileInfo(new File(".classpath"));
        innerTestInst.doCreateFile(new File(".classpath"));
        innerTestInst.getSysInfo();
        new FileTest().doModifySuffix("E:\\总结\\老子_德经", "htm", "html");
    }

    /**
     * 遍历打印map信息
     */
    public void printMap() {
        for (Iterator iterator = contextInfo.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entryInst = (Map.Entry) iterator.next();
            System.out.println(entryInst.getKey() + ":" + entryInst.getValue());
        }
        contextInfo.clear();
    }

    /**
     * insertInfo
     * @param desName desName
     * @param desObj desObj
     */
    public void insertInfo(String desName, Object desObj) {
        if (contextInfo.containsKey(desName)) {
            throw new RuntimeException("已包含测试名目为@DN@，内容为@DO@".replace("@DN@", desName).replace("@DO@",
                desObj.toString()));
        }
        contextInfo.put(desName, desObj);
    }

    /**
     * inertInfo
     * @param desName desName
     * @param desObj desObj
     */
    public void insertInfo(int desName, String desObj) {
        this.insertInfo(String.valueOf(desName), desObj);
    }

    /**
     * getInnerTest
     * @return innerTest/null
     */
    public FileTest.InnerTest getInnerTest() {
        return new InnerTest();
    }
    /**
     * 修改文件后缀
     * @param fileDir 文件目录
     * @param oldSuffix 原后缀
     * @param newSuffix 新后缀
     */
    public void doModifySuffix(String fileDir, final String oldSuffix, String newSuffix) {
        File fileInst = new File(fileDir);
        if (fileInst.isDirectory()) {
            File[] fileList = fileInst.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(oldSuffix);
                }
            });
            for (File file : fileList) {
                String fileName = file.getName();
                File tmpFile = null;
                int tmpI = fileName.indexOf('.');
                if (tmpI == -1) {
                    fileName = fileName.substring(0, 3);
                    tmpFile = new File(file.getParentFile(), fileName.concat(".").concat(newSuffix));
                } else {
                    fileName = fileName.substring(0, tmpI + 1);
                    tmpFile = new File(file.getParentFile(), fileName.concat(newSuffix));
                }
                file.renameTo(tmpFile);
                System.out.println(file.getAbsolutePath() + "==>" + tmpFile.getAbsolutePath());
            }
        }
    }

    /**
     * 负责具体测试细节的实现
     * @author WangYanCheng
     * @version 2010-8-20
     */
    class InnerTest {
        /**
         * 取得文件信息
         * @param fileInst fileInst
         */
        public void doTestGetFileInfo(File fileInst) {
            insertInfo("fileInst.getAbsolutePath", fileInst.getAbsolutePath());
            try {
                insertInfo("fileInst.getCanonicalPath", fileInst.getCanonicalPath());
                insertInfo("fileInst.getCanonicalFile", fileInst.getCanonicalFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            insertInfo("fileInst.getName", fileInst.getName());
            insertInfo("fileInst.getParent", fileInst.getParent());
            insertInfo("fileInst.getPath", fileInst.getPath());
            insertInfo("fileInst.getAbsoluteFile", fileInst.getAbsoluteFile());
            printMap();
        }

        /**
         * 创建文件
         * @param fileInst fileInst
         * @return rtnFlag true/false
         */
        public boolean doCreateFile(File fileInst) {
            boolean rtnFlag = true;
            try {
                if (!fileInst.exists()) {
                    rtnFlag = fileInst.createNewFile();
                } else {
                    throw new IOException("文件已存在!");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                rtnFlag = false;
            }
            return rtnFlag;
        }

        /**
         * getSysInfo
         */
        public void getSysInfo() {
            insertInfo("user.dir", System.getProperty("user.dir"));
            Enumeration<URL> enumArray = null;
            try {
                enumArray = ClassLoader.getSystemClassLoader().getResources(".");
                innerSysInfo(enumArray);
                URL urlInst = this.getClass().getResource(".");
                insertInfo("this.getClass().getResource(\".\")", urlInst.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            printMap();
        }

        /**
         * innerSysInfo
         * @param enumInst enumInst
         */
        public void innerSysInfo(Enumeration<URL> enumInst) {
            while (enumInst.hasMoreElements()) {
                URL tmpUrl = enumInst.nextElement();
                insertInfo("ENUM_" + tmpUrl.hashCode(), tmpUrl.getPath());
            }
        }
    }
}
