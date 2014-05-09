package org.ybygjy.basic.file.fileedit;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ybygjy.basic.file.fileedit.DeleteLineInFile;

/**
 * µ¥Ôª²âÊÔ
 * @author WangYanCheng
 * @version 2011-12-29
 */
public class DeleteLineInFileTest {
    private DeleteLineInFile dlifInst;
    private int[] delLines;
    private File fileInst;

    @Before
    public void setUp() throws Exception {
        fileInst = new File("C:\\FileReplaceTest\\simple.txt");
        delLines = new int[] { 1 };
        dlifInst = new DeleteLineInFile(fileInst, delLines);
    }

    @After
    public void tearDown() throws Exception {
        dlifInst = null;
    }

    @Test
    public void testReadFileLine() {
        RandomAccessFile rafInst = null;
        FileChannel fileChannel = null;
        ByteBuffer byteBuff = ByteBuffer.allocate(1024);
        try {
            rafInst = new RandomAccessFile(fileInst, "rw");
            fileChannel = rafInst.getChannel();
            dlifInst.readFileLine(fileChannel, byteBuff, fileInst.length());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fileChannel) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void writeFile() {
        RandomAccessFile rafInst = null;
        File fileInst = new File("C:\\FileReplaceTest\\tt.txt");
        try {
            rafInst = new RandomAccessFile(fileInst, "rw");
            rafInst.write(new byte[] { -17, -69, -65 });
            rafInst.write("I'm a software enginner.".getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != rafInst) {
                try {
                    rafInst.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
