package org.ybygjy.fileEdit;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 删除文件行
 * @author WangYanCheng
 * @version 2011-12-29
 */
public class DeleteLineInFile {
    private File fileInst;
    private int[] delLines;
    private CharsetDecoder decoder;

    /**
     * Constructor
     * @param fileInst 文件实例
     * @param delLines 删除文件行，从0开始超出范围的行将被忽略
     */
    public DeleteLineInFile(File fileInst, int[] delLines) {
        this.fileInst = fileInst;
        Arrays.sort(delLines);
        this.delLines = delLines;
        this.decoder = Charset.forName("UTF-8").newDecoder();
    }

    /**
     * 使用移动文件块的策略删除
     */
    public void delete4Buffer() {
        ByteBuffer byteBuff = ByteBuffer.allocate(1024);
        RandomAccessFile rafInst = null;
        FileChannel fileChannel = null;
        try {
            rafInst = new RandomAccessFile(fileInst, "rw");
            fileChannel = rafInst.getChannel();
            readFileLine(fileChannel, byteBuff, fileInst.length());
        } catch (IOException e) {
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

    /**
     * 负责以块的方式区别文件行
     * @param fileChannel 通道
     * @param byteBuff 缓冲区
     * @param fileSize 文件实际大小
     * @throws IOException 异常
     */
    public void readFileLine(FileChannel fileChannel, ByteBuffer byteBuff, long fileSize) throws IOException {
        long cntTotal = 0L;
        while ((fileChannel.read(byteBuff)) != -1) {
            byteBuff.flip();
            // 验证和定位行分段
            int position = calcPosition(decoder.decode(byteBuff));
            if (position != -1 && (position + cntTotal)< fileSize) {
                fileChannel = fileChannel.position(cntTotal + position);
                cntTotal += position;
            }
            byteBuff.clear();

        }
    }

    /**
     * 取字符串最后匹配的位置，用作文件块的position
     * @param cs {@link CharSequence}
     * @return rtnPosition {-1:未找到匹配/position:具体位置值}
     */
    private int calcPosition(CharSequence cs) {
        Matcher matcherInst = pattern.matcher(cs);
        MatchResult mr = null;
        while (matcherInst.find()) {
            mr = matcherInst.toMatchResult();
        }
        return mr == null ? -1 : mr.end();
    }

    private static Pattern pattern = Pattern.compile(".*\r?\n");

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        String tmpStr = "ABC";
        Matcher matcherInst = pattern.matcher(tmpStr);
        MatchResult mr = null;
        while (matcherInst.find()) {
            mr = (matcherInst.toMatchResult());
            System.out.println(mr.end());
        }
    }
}
