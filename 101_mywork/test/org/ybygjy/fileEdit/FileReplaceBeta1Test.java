package org.ybygjy.fileEdit;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试用例
 * @author WangYanCheng
 * @version 2011-12-29
 */
public class FileReplaceBeta1Test {
    private FileReplaceBeta1 frbInst;
    private File fileInst;
    private String charsetName;
    private CharsetDecoder decoder;
    private String regExpStr;
    private String[] tokenArr;

    @Before
    public void setUp() throws Exception {
        charsetName = "GB18030";
        this.decoder = Charset.forName(charsetName).newDecoder();
    }

    @After
    public void tearDown() throws Exception {
        frbInst = null;
    }

    @Test
    public void testDoWork() {
        String filePath = "F:\\work\\nstc\\2_事务\\022_KettleN6N9数据迁移\\测试环境\\N9-SM-O\\newinstall\\bs\\web";
        try {
            this.regExpStr = "127\\.0\\.0\\.1:7001";
            this.tokenArr = new String[] { regExpStr, "127.0.0.1:6004",
                    ".*(AppServer|applicationContext|configurer).*\\.properties$" };
            this.fileInst = new File(filePath);
            frbInst = new FileReplaceBeta1(fileInst, tokenArr, charsetName);
            frbInst.doWork();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRecursingPFile() {
        try {
            this.fileInst = new File(singleFilePath);
            frbInst.recursingPFile(fileInst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadFile() {
        File fileInst = new File(singleFilePath);
        try {
            frbInst.readFile(fileInst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMatchAndReplace() {
        File fileInst = null;
        RandomAccessFile rafInst = null;
        FileChannel fileChannel = null;
        try {
            fileInst = new File(singleFilePath);
            rafInst = new RandomAccessFile(fileInst, "rw");
            fileChannel = rafInst.getChannel();
            MappedByteBuffer mbbInst = fileChannel.map(MapMode.READ_WRITE, 0, fileChannel.size());
            frbInst.matchAndReplace(decoder.decode(mbbInst), fileChannel);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (null != fileChannel) {
                try {
                    fileChannel.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testRegexp() {
        String fileName = "abcdef.properties";
        Assert.assertTrue(fileName.matches(".*\\.{1}properties$"));
        String filePath = "C:\\FileReplaceTest";
        File fileInst = new File(filePath);
        fileInst.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                String pathStr = pathname.getName();
                System.out.println("pathname.isDirectory()==>" + pathname.isDirectory()
                    + "\tpathname.getName()==>" + pathStr);
                return false;
            }
        });
    }

    private String singleFilePath = "C:\\FileReplaceTest\\applicationContext.properties";
}
