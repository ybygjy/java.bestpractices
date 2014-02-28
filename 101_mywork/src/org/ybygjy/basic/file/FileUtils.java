package org.ybygjy.basic.file;

import java.io.File;
import java.net.URL;

/**
 * 封装常用文件相关操作
 * @author WangYanCheng
 * @version 2011-11-1
 */
public class FileUtils {
    /***
     * 从指定类路径下加载同级文件，如当前路径是org.ybygjy.HelloWorld，则要找HelloWorld.class同级的资源
     * @param classInst 类加载器
     * @param fileName 文件名
     * @return fileInst/null
     */
    public static File lookup(Class classInst, String fileName) {
        classInst = classInst == null ? FileUtils.class : classInst;
System.out.println("加载器路径：" + classInst);
        URL urlInst = classInst.getResource(fileName);
System.out.println("查找文件：\n 加载器路径：" + classInst.getResource("") + "\n 文件路径：" + urlInst);
        return new File(urlInst.getFile());
    }
}
