package org.ybygjy.basic.file.fileedit;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分析Class类路径，自动填充包名称
 * @author WangYanCheng
 * @version 2014-4-22
 */
public class AutomaticFillPackage4Class {
    /**基目录*/
    private File sourceDir;
    private String fileEncode = "UTF-8";
    public AutomaticFillPackage4Class() {
        sourceDir = new File("D:\\DEV\\02_work\\02_financial_server_v1\\src\\main\\java");
        if (!sourceDir.isDirectory()) {
            throw new RuntimeException("必须指定文件夹！");
        }
    }
    public void doWork() {
        innerDoWork(sourceDir);
    }
    /**
     * 遍历目录取.java文件
     * @param file
     */
    private void innerDoWork(File file) {
        //1、取目录下子文件
        File[] tmpFiles = file.listFiles(new CustomerFileFilter());
        //2、验证文件是否可读/写，是否是.java文件
        for (File tmpFile : tmpFiles) {
            if (tmpFile.isDirectory()) {
                innerDoWork(tmpFile);
            } else if (tmpFile.canWrite()) {
                doUpdateFilePackage(tmpFile);
            }
        }
    }
    /**
     * 更新文件package
     * @param tmpFile
     */
    private void doUpdateFilePackage(File tmpFile) {
        //2.1、取.java文件内容匹配package一行
        //2.2、分析.java文件路径，取package包名称
        //2.3、利用正则表达式进行替换
        //2.4、写入文件，刷新缓冲流
        BufferedInputStream rafInst = null;
        ByteArrayOutputStream baos = null;
        FileOutputStream fout = null;
        try {
            rafInst = new BufferedInputStream(new FileInputStream(tmpFile));
            baos = new ByteArrayOutputStream();
            byte[] cbuf = new byte[2048];
            int flag = -1;
            //加载文件内容
            while ((flag = rafInst.read(cbuf)) != -1) {
                baos.write(cbuf, 0, flag);
            }
            String fileContent = baos.toString(fileEncode);
            rafInst.close();
            baos.close();
            //分析文件路径提取包路径
            String pagepath = "package " + analysisPackagePath(tmpFile) + ";";
            //替换/写入
            fileContent = fileContent.replaceFirst("package([^\r\n]+)", pagepath);
            fout = new FileOutputStream(tmpFile, false);
            fout.write(fileContent.getBytes(fileEncode));
            fout.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 分析文件路径取package路径
     * @param tmpFile
     * @return
     */
    private String analysisPackagePath(File tmpFile) {
        String filePath = tmpFile.getParentFile().getPath();
        filePath =
            Pattern.compile(File.separator, Pattern.LITERAL).matcher(filePath).replaceAll(Matcher.quoteReplacement("."));
        Matcher matcher = Pattern.compile(".*(com(\\.\\w+)+)").matcher(filePath);
        String rtnpackagePath = filePath;
        if (matcher.find()) {
            rtnpackagePath = matcher.group(1);
        }
        return rtnpackagePath;
    }
    public static void main(String[] args) {
        new AutomaticFillPackage4Class().doWork();
    }
}
/**
 * 文件过滤
 * @author WangYanCheng
 * @version 2014-4-22
 */
class CustomerFileFilter implements FileFilter {
    @Override
    public boolean accept(File fileInst) {
        return fileInst.isDirectory() || fileInst.getName().matches(".*\\.java$");
    }
    
}
