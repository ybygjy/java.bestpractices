package org.ybygjy.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 关于操作系统文件管理主题的工具类
 * @author WangYanCheng
 * @version 2012-4-10
 */
public class FileUtils {
    /**
     * 提取指定目录下的文件名称
     * <p>
     * 1、不进行子目录遍历
     * <p>
     * 2、忽略文件扩展名
     * <p>
     * 3、文件名称全部转换成大写
     * @param dirInst 目录
     * @return rtnFileNameArray 文件名称集
     */
    public static String[] fetchFileName(File dirInst) {
        List<String> rtnFileNames = new ArrayList<String>();
        File[] rtnFileArray = dirInst.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() ? false : true;
            }
        });
        for (File fileInst : rtnFileArray) {
            String fileName = fileInst.getName();
            fileName = fileName.replaceAll("\\..*$", "");
            rtnFileNames.add(fileName.toUpperCase());
        }
        String[] rtnFileNameArr = new String[rtnFileNames.size()];
        rtnFileNameArr = rtnFileNames.toArray(rtnFileNameArr);
        return rtnFileNameArr;
    }

    /**
     * 给定文件目录下的文件，文件名添加后缀
     * @param fileDir 文件目录
     * @param prefix 后缀
     */
    public static void addedSuffix4FileName(String fileDir, String prefix) {
        File fileDirInst = new File(fileDir);
        File[] fileInstArray = fileDirInst.listFiles(new FileFilter() {
            @Override
            public boolean accept(File fileInst) {
                return !fileInst.isDirectory();
            }
        });
        for (File tmpFile : fileInstArray) {
            String tmpName = tmpFile.getName();
            tmpName = getFileName(tmpName, prefix);
            boolean rtnFlag = tmpFile.renameTo(new File(tmpFile.getParentFile(), tmpName));
            System.out.println(rtnFlag);
        }
    }

    /**
     * 给文件名格式的字符串添加后缀
     * <p>示例
     * <p>输入：原文件名abc.txt，后缀字符串exe
     * <p>输出：abcexe.txt
     * <p>思路：
     * <p>1、分解文件名取文件名前缀、后缀
     * <p>2、添加中缀字符串
     * <p>3、组合最终结果=前缀+中缀+后缀
     * @param fileName 文件名称可带扩展名
     * @param extStr 加入的字符串
     * @return rtnFlag fileName
     */
    public static String getFileName(String fileName, String extStr) {
        fileName = fileName.toUpperCase();
        String prefixStr = fileName.replaceAll("\\..*$", "");
        if (prefixStr.endsWith(extStr)) {
            System.out.println("名称：".concat(fileName).concat("已经包含指定后缀：".concat(extStr)));
        }
        String suffixStr = fileName.lastIndexOf('.') != -1 ? fileName.substring(fileName.lastIndexOf('.')) : "";
        String tmplStr = prefixStr.concat(extStr).concat(suffixStr);
        return tmplStr;
    }

    /**
     * 从给定类名称和类加载器中取访问该字节码文件的IO流对象
     * <p>1、将给定的className需要转换成相对路径
     * <p>2、利用{@linkplain ClassLoader#getResourceAsStream(String)}
     * <p>示例：
     * <p><blockquote>取得FileUtils.class文件流<pre>
     * FileUtils.getClassStream("org.ybygjy.FileUtils", FileUtils.class.getClassLoader());
     * </pre></blockquote>
     * @param className 类名称
     * @param classLoader 类加载器
     * @return rtnStream 流对象/null
     */
    public static InputStream getClassStream(String className, ClassLoader classLoader) {
        className = className.replaceAll("\\.", "/").concat(".class");
        return classLoader.getResourceAsStream(className);
    }
    
    /**
     * 将输入流转储到文件系统
     * @param dir 指定目录
     * @param ins 输入流{@link InputStream}
     * @return rtnFile 转储到文件系统的文件实例
     * @throws IOException {@link IOException}
     */
    public static File restoreInputStream(String dir, String fileName, InputStream ins) throws IOException {
        File fileInst = new File(dir, fileName);
        if (fileInst.exists()) {
            fileInst.delete();
        }
        BufferedOutputStream bos = null;
        byte[] buff = new byte[1024*1024];
        try {
            bos = new BufferedOutputStream(new FileOutputStream(fileInst, false));
            int flag = -1;
            while ((flag = ins.read(buff)) != -1) {
                bos.write(buff, 0, flag);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                bos.close();
            }
        }
        return fileInst;
    }
    /**
     * 取文件名，不包含路径
     * @param fileInst {@link File}
     * @return fileName
     */
    public static String getFileName(File fileInst) {
        return fileInst.getName();
    }
}
