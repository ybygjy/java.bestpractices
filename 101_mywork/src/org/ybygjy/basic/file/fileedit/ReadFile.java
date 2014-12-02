package org.ybygjy.basic.file.fileedit;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * 读取文件内容的多种方式
 * @author WangYanCheng
 * @version 2011-12-27
 */
public class ReadFile {
    /**
     * 按行读取
     * @param fileInst 文件实例
     * @throws IOException 异常
     */
    public void readFile4EachRow(File fileInst) throws IOException {
        BufferedReader brInst = null;
        String tmpStr = null;
        try {
            brInst = new BufferedReader(new FileReader(fileInst));
            while ((tmpStr = brInst.readLine()) != null) {
                System.out.println(tmpStr);
            }
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (null != brInst) {
                try {
                    brInst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 流转字符
     */
    public void streamReader() {
        File fileInst = new File("C://test.xsl");
        BufferedReader brInst = null;
        try {
            brInst = new BufferedReader(new InputStreamReader(new FileInputStream(fileInst), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != brInst) {
                try {
                    brInst.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 随机读文件的某一块
     * @param fileInst 文件实例
     * @throws IOException 异常
     */
    public void readFile4Random(File fileInst) throws IOException {
        RandomAccessFile rafInst = null;
        try {
            rafInst = new RandomAccessFile(fileInst, "r");
            rafInst.seek(16);
            int cnt = -1;
            byte[] byteArr = new byte[1024];
            while ((cnt = rafInst.read(byteArr)) != -1) {
                System.out.println(new String(byteArr, 0, cnt));
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != rafInst) {
                rafInst.close();
            }
        }
    }

    /**
     * 按字符组读
     * @param fileInst 文件实例
     * @throws IOException 异常
     */
    public void readFile4CharArr(File fileInst) throws IOException {
        Reader reader = null;
        char[] charArr = new char[1024];
        try {
            // reader = new FileReader(fileInst);
            reader = new InputStreamReader(new FileInputStream(fileInst));
            int cnt = -1;
            while ((cnt = reader.read(charArr)) != -1) {
                System.out.println(new String(charArr, 0, cnt));
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 按单个字符读
     * @param fileInst 文件实例
     * @throws IOException 异常
     */
    public void readFile4Char(File fileInst) throws IOException {
        Reader reader = null;
        try {
            // reader = new FileReader(fileInst);
            reader = new InputStreamReader(new FileInputStream(fileInst));
            int cnt = -1;
            while ((cnt = reader.read()) != -1) {
                System.out.println(cnt);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 按字节组读取
     * @param fileInst 文件实例
     * @throws IOException 异常
     */
    public void readFile4ByteArr(File fileInst) throws IOException {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(fileInst));
            byte[] byteArr = new byte[1024];
            int cnt = -1;
            while ((cnt = bis.read(byteArr)) != -1) {
                System.out.println(new String(byteArr, 0, cnt));
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 按字节读取
     * @param fileInst 文件实例
     * @throws IOException 异常
     */
    public void readFile4Byte(File fileInst) throws IOException {
        FileInputStream bis = null;
        try {
            bis = new FileInputStream(fileInst);
            int tmpC = -1;
            while ((tmpC = bis.read()) != -1) {
                System.out.println(tmpC);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != bis) {
                try {
                    bis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 入口
     * @param args 参数列表
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
        String filePath = "C:\\sqlnet_.log";
        File fileInst = new File(filePath);
        ReadFile rfInst = new ReadFile();
        rfInst.readFile4Byte(fileInst);
        rfInst.readFile4ByteArr(fileInst);
        rfInst.readFile4Char(fileInst);
        rfInst.readFile4CharArr(fileInst);
        rfInst.readFile4EachRow(fileInst);
        rfInst.readFile4Random(fileInst);
    }
}
