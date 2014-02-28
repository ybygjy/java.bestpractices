package org.ybygjy.fileEdit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 负责扫描指定文件的关键词
 * @author WangYanCheng
 * @version 2011-12-27
 */
public class FileScaner {
    private File fileInst;
    private String tokenStr;
    public FileScaner() {
        fileInst = new File("C:\\sqlnet.log");
        tokenStr = "12560";
    }
    public void doWork() {
        Scanner scanInst = null;
        try {
            scanInst = new Scanner(fileInst).useDelimiter("\r?\n$");
            while (scanInst.hasNext()) {
                System.out.println(scanInst.nextLine());
            }
            scanInst.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != scanInst) {
                scanInst.close();
            }
        }
    }
    public static void main(String[] args) {
        new FileScaner().doWork();
    }
}
