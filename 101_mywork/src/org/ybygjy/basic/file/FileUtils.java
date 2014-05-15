package org.ybygjy.basic.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    /**
     * 文件内容匹配输出
     * <p>对文件内容进行预定的正则匹配，并输出匹配结果</p>
     * @param fileInst {@link File}
     * @param pattern {@link Pattern}
     * @param fileEncoding 文件编码
     */
    public static void findAndOutput(File fileInst, Pattern pattern, String fileEncoding, FileUtilsCallback fucbInst) {
        //读文件
        //文件解码
        String fileContent = readFile(fileInst, fileEncoding);
        //正则匹配
        Matcher matcher = pattern.matcher(fileContent);
        //输出结果
        while (matcher.find()) {
            int groupCount = matcher.groupCount();
            groupCount = groupCount + 1;
            String[] groupArr = new String[groupCount];
            for (int i = 0; i < groupCount; i++) {
                groupArr[i] = (matcher.group(i));
            }
            if (null != fucbInst) {
                fucbInst.doFindAndOutput(groupArr);
            }
        }
    }
    /**
     * 读文件内容
     * @param fileInst {@link File}
     * @param fileEncoding 文件编码
     * @return fileContent
     */
    private static String readFile(File fileInst, String fileEncoding) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        byte[] buff = new byte[1024];
        try {
            fis = new FileInputStream(fileInst);
            baos = new ByteArrayOutputStream();
            int flag = -1;
            while ((flag = fis.read(buff)) != -1) {
                baos.write(buff, 0, flag);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String fileContent = null;
        if (null != baos && baos.size() > 0) {
            try {
                fileContent = baos.toString(fileEncoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return fileContent;
    }
    public static void main(String[] args) throws IOException {
        File matcherResultFile = new File("D:\\MatcherResultFile.txt");
        final FileWriter fileWriter = new FileWriter(matcherResultFile);
        FileUtilsCallback fucall = new FileUtilsCallback(){
            @Override
            public void doFindAndOutput(String[] matcherGroups) {
                try {
                    fileWriter.write(matcherGroups[0]);
                    fileWriter.write('\r');
                    fileWriter.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        String regexpStr = "\\({2}@SNAME@\\)([^\"]+(\"[^\"]+\").*\\)\\))\\.([^;]+);";
        File baseDir = new File("D:\\DEV\\03_project\\20140414_财务系统拆分\\1007_整理前端Service_方法");
        File[] serviceFiles = baseDir.listFiles();
        for (File tmpFile : serviceFiles) {
            String fileName = tmpFile.getName();
            String[] tmpArr = fileName.split("_");
            String serviceName = tmpArr[tmpArr.length - 1].replaceAll("\\.[^\\.]+", "");
            serviceName = regexpStr.replaceFirst("@SNAME@", serviceName);
            Pattern pattern = Pattern.compile(serviceName);
            fileWriter.write("FileMatcher Begin=>>" + fileName);
            fileWriter.write('\r');
            fileWriter.write('\n');
            FileUtils.findAndOutput(tmpFile, pattern, "GBK", fucall);
            fileWriter.write("FileMatcher End=>>" + tmpFile.getName());
            fileWriter.write('\r');
            fileWriter.write('\n');
        }
        fileWriter.flush();
        fileWriter.close();
    }
}
interface FileUtilsCallback {
    /**
     * 匹配成功回调
     * @param matcherGroups 匹配成功的数据组
     */
    public void doFindAndOutput(String[] matcherGroups);
}