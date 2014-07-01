package org.ybygjy.basic.charset;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * GB2312编码规范
 * @author WangYanCheng
 * @version 2014-6-21
 */
public class CharsetGB2312 {
    /**
     * 输出GB2312字符集
     * @param fileInst {@link File}
     */
    public void doPrint(HTMLOutput htmlOutput) {
        //首先定义GB2312字符集范围
        String[][] charsetData = new String[0xFF][0xFF];
        Charset charset = Charset.forName("GB2312");
        int i = 0;
        //填充数据
        for (; i < 0xFF; i++) {
            for (int j = 0; j < 0xFF; j++) {
                charsetData[i][j] = charset.decode(ByteBuffer.wrap(new byte[]{(byte) i, (byte) j})).toString();
            }
        }
        //输出处理
        doPrintArray(charsetData, htmlOutput);
    }
    /**
     * 字符集输出逻辑处理
     * @param charsetData 字符集
     * @param htmlOutput {@link HTMLOutput}
     */
    private void doPrintArray(String[][] charsetData, HTMLOutput htmlOutput) {
        int i = 0;
        int item = 0;
        String tmpStr = null;
        for (; i < 0xFF; i++) {
            List<String> tmpList = new ArrayList<String>();
            for (int j = 0x00; j < 0xFF; j++) {
                if (item == 0) {
                    tmpList.add((checkBit(Integer.toHexString((byte) i & 0xFF)) + checkBit(Integer.toHexString((byte) j & 0xFF))).toUpperCase());
                }
                tmpStr = charsetData[i][j];
                tmpList.add(tmpStr);
                item++;
                if (item%16==0) {
                    htmlOutput.doOutput(tmpList.toArray(new String[16]));
                    item = 0;
                    tmpList.clear();
                }
            }
            item = 0;
            htmlOutput.doOutput(new String[16]);
        }
        htmlOutput.writeFinished();
    }
    private String checkBit(String str) {
        if (str.length() < 2) {
            return "0" + str;
        }
        return str;
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        CharsetGB2312 charsetInst = new CharsetGB2312();
        File outputFile = new File("GB2312.html");
        charsetInst.doPrint(new HTMLOutput(outputFile));
    }
}
/**
 * HTML格式输出
 * @author WangYanCheng
 * @version 2014-6-21
 */
class HTMLOutput {
    private FileChannel fileChannel = null;
    public HTMLOutput(File outputFile) {
        try {
            fileChannel = new FileOutputStream(outputFile, false).getChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        beforeOutput(fileChannel);
    }
    public void writeFinished() {
        afterOutput(fileChannel);
        if (fileChannel != null) {
            try {
                fileChannel.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 输出
     * @param dataArr
     */
    public void doOutput(String[] dataArr) {
        StringBuilder sbud = new StringBuilder();
        for (String tmpStr : dataArr) {
            sbud
                .append(tmpStr == null ? "  " : tmpStr)
                .append(" ");
        }
        sbud.append("\r\n");
        innerWriteData(fileChannel, sbud.toString().getBytes());
    }
    private void afterOutput(FileChannel fileChannel) {
        StringBuilder sbud = new StringBuilder();
        sbud.append("</pre>")
        .append("</body></html>");
    }
    private void beforeOutput(FileChannel fileChannel) {
        StringBuilder sbud = new StringBuilder();
        sbud.append("<html><head>")
            .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\">")
            .append("</head><body>")
            .append("<pre>")
            .append("0")
            .append("")
            .append("1")
            .append("")
            .append("2")
            .append("")
            .append("3")
            .append("")
            .append("4")
            .append("")
            .append("5")
            .append("")
            .append("6")
            .append("")
            .append("7")
            .append("")
            .append("8")
            .append("")
            .append("9")
            .append("")
            .append("A")
            .append("")
            .append("B")
            .append("")
            .append("C")
            .append("")
            .append("D")
            .append("")
            .append("E")
            .append("")
            .append("F")
            .append("")
            .append("G")
            .append("")
            .append("H")
            .append(" ")
            .append("\r\n");
        innerWriteData(fileChannel, sbud.toString().getBytes());
    }
    private void innerWriteData(FileChannel fileChannel, byte[] bytes) {
        try {
            fileChannel.write(ByteBuffer.wrap(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
