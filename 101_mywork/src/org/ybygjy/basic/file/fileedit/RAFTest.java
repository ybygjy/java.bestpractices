package org.ybygjy.basic.file.fileedit;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Pattern;

/**
 * 负责验证RandomAccessFile的分块读后续覆盖的问题
 * @author WangYanCheng
 * @version 2011-12-29
 */
public class RAFTest {
    private File fileInst;
    private Pattern pattern;

    /**
     * Constructor
     * @param fileInst 文件实例
     */
    public RAFTest(File fileInst) {
        this.fileInst = fileInst;
        this.pattern = Pattern.compile("127\\.0\\.0\\.1");
    }

    public void doWork() {
        RandomAccessFile rafInst = null;
        try {
            rafInst = new RandomAccessFile(fileInst, "rw");
            String tmpStr = null;
            while ((tmpStr = rafInst.readLine()) != null) {
                tmpStr = tmpStr.concat("\r\n");
                byte[] byteArr = null;
                if (this.pattern.matcher(tmpStr).find()) {
                    tmpStr = tmpStr.replaceAll("127\\.0\\.0\\.1", "192.82.3.6");
                    byteArr = tmpStr.getBytes();
                    long seekFlag = rafInst.getFilePointer() - byteArr.length;
                    seekFlag = seekFlag < 0 ? 0 : seekFlag;
                    rafInst.seek(seekFlag);
                    rafInst.write(byteArr);
                }
            }
        } catch (IOException e) {
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

    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        File fileInst = new File("C:\\FileReplaceTest\\applicationContext.properties");
        RAFTest rafInst = new RAFTest(fileInst);
        rafInst.doWork();
    }
}
