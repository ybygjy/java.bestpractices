package org.ybygjy.util.file.convert;

import java.io.File;
import java.io.FileFilter;

/**
 *@author Hj, Date: 2011-5-17
 * Email: Hj-545@qq.com
 */
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Console console = new Console();
        FrmMain frm = new FrmMain(console);
        console.setFrm(frm);
        console.show();
//        File fileInst = new File("D:\\DEV\\02_work\\03_lefeng\\src");
//        doDelete(fileInst);
    }
    public static void doDelete(File fileInst) {
        if (fileInst.isDirectory()) {
            File[] fileArray = fileInst.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    System.out.println(pathname.getAbsolutePath());
                    if (pathname.getName().matches(".*\\.bak$")) {
                        return true;
                    }
                    if (pathname.isDirectory()) {
                        return true;
                    }
                    return false;
                }
            });
            for (File file : fileArray) {
                System.out.println(file.getAbsolutePath());
            }
        }
    }
    public static void innerDoDel(File fileInst) {
        fileInst.delete();
    }
}
