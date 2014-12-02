package org.ybygjy.basic.bit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * 字节数据写入到文件
 * @author WangYanCheng
 * @version 2010-4-25
 */
public class BitIO {
    /**
     * 文件读取字节
     */
    public void doReadFile4Byte() {
        try {
            InputStream ins = new FileInputStream(new File("C:\\BitIO.txt"));
            int tmpInt = -1;
            while ((tmpInt = ins.read()) != -1) {
                System.out.println(tmpInt + ":" + Integer.toBinaryString(tmpInt));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    /**
     * 文件写入字节
     */
    public void doWriteFile4Byte() {
        byte[] buffer = {127, 127, 127};
        int[] intBuff = {97, 65, 5632};
        File fileInst = new File("C:\\BitIO.txt");
        try {
            FileWriter fw = new FileWriter(fileInst);
            for (byte i : buffer) {
                fw.write(String.valueOf((char) i));
            }
            buffer = String.valueOf(5632).getBytes();
            for (byte i : buffer) {
                System.out.println(Integer.toBinaryString(i));
            }
            for (int i : intBuff) {
                fw.write(i);
            }
            fw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    /**
     * 测试入口
     * @param args arguments
     */
    public static void main(String[] args) {
        BitIO bioInst = new BitIO();
        bioInst.doWriteFile4Byte();
        bioInst.doReadFile4Byte();
        System.out.println(Integer.toBinaryString(5632));
    }
}
