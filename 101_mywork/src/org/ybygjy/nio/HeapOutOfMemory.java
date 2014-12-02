package org.ybygjy.nio;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 利用ByteArrayOutputStream构造Java Heap Space Out of Memory
 * @author WangYanCheng
 * @version 2012-8-30
 */
public class HeapOutOfMemory {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        HeapOutOfMemory hofmInst = new HeapOutOfMemory();
        ByteArrayOutputStream baosInst = new ByteArrayOutputStream();
        while (true) {
            hofmInst.doWork(baosInst);
            System.out.println(baosInst.size()/(1024*1024) + "M");
        }
    }

    /**
     * 不停的将数据存放到Output对象中，直接导致空间溢出
     * @param baosInst {@link ByteArrayOutputStream}
     */
    public void doWork(ByteArrayOutputStream baosInst) {
        BufferedInputStream bis = (BufferedInputStream) (this.getClass().getClassLoader().getResourceAsStream(this.getClass().getName().replaceAll("\\.", "/")+".class"));
        byte[] buff = new byte[1024 * 1024];
        try {
            while (bis.read(buff) != -1) {
                baosInst.write(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
