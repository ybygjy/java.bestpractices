package org.ybygjy.basic.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 负责实现基于正则的文件内容查找
 * <ol>
 * <li>利用NIO机制处理文件流的读取工作</li>
 * <li>利用Java正则支持处理匹配</li>
 * </ol>
 * @author WangYanCheng
 * @version 2011-11-1
 */
public class FileGrep {
    /** 文件字符编码 */
    private CharsetDecoder decoder;
    /** 文件行 */
    private static Pattern pattern = Pattern.compile(".*\r?\n");

    /**
     * Constructor
     * @param decoder CharsetDecoder
     */
    public FileGrep(CharsetDecoder decoder) {
        this.decoder = decoder;
    }

    /**
     * 取实例
     * @param charsetInst 字符集对象
     * @return fileGrepInst
     */
    public static FileGrep getInst(Charset charsetInst) {
        return new FileGrep(charsetInst.newDecoder());
    }
    public static void doConnURL(String url) {
    	try {
			URL urlObj = new URL(url);
			URLConnection urlConn = urlObj.openConnection();
			urlConn.setDoOutput(true);
			urlConn.connect();
			InputStream ins = urlConn.getInputStream();
			byte[] buff = new byte[1024];
			int flag = -1;
			while ((flag = ins.read(buff)) != -1) {
				System.out.println(new String(buff, 0, flag));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * 文件内容匹配入口
     * @param pattern 匹配串
     * @param fileInst 文件对象
     * @return rtnStrArr/null 文件内容匹配结果
     * @throws IOException 异常信息
     */
    public String[] findResult4Regexp(final String pattern, File fileInst, final int groupIndex)
        throws IOException {
        final Pattern tmpPattern = Pattern.compile(pattern);
        final List<String> tmpArr = new ArrayList<String>();
        readFile(fileInst, new InnerCallback() {
            public void callBack(CharBuffer charBuff) {
                Matcher matcherInst = FileGrep.pattern.matcher(charBuff);
                while (matcherInst.find()) {
                    String tmpStrArr = matchesResult4Single(matcherInst.group(), tmpPattern, groupIndex);
                    if (null != tmpStrArr) {
                        tmpArr.add(tmpStrArr);
                    }
                }
            }
        });
        if (tmpArr.size() == 0) {
            return null;
        }
        String[] tmpStrArr = new String[tmpArr.size()];
        return tmpArr.toArray(tmpStrArr);
    }

    /**
     * 按行取文件内容
     * @param fileInst 文件
     * @return rtnArr/null
     * @throws IOException IOException
     */
    public String[] readFileContent(File fileInst) throws IOException {
        FileInputStream fins = null;
        try {
            fins = new FileInputStream(fileInst);
            FileChannel fileCInst = fins.getChannel();
            MappedByteBuffer mbbInst = fileCInst.map(MapMode.READ_ONLY, 0, fileInst.length());
            return (decoder.decode(mbbInst).toString().split("\\n"));
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            if (null != fins) {
                fins.close();
            }
        }
    }

    /**
     * 内容匹配返回第一次匹配结果
     * <p>
     * 示例:
     * <pre>
     * Matcher matcherInst = Pattern.compile("@author\s+((\w+\.?)+\w+)\r?\n?").matcher("@author org.ybygjy.FileGrep");
     * </pre>
     * </p>
     * <p>
     * 注意匹配分组标记参数，分组标记参数与参与匹配的正则表达式相关
     * <ol>
     * <li>0,表示取当前匹配串</li>
     * <li>1,表示取当前匹配串的第一个组</li>
     * <li>2,表示取当前匹配串的第二个组</li>
     * <li>3...依次类推</li>
     * </ol>
     * </p>
     * @param charBuff 字符序列
     * @param pattern 匹配模式
     * @param groupIndex 在匹配成功时返回的正则模式匹配分组标记
     * @return rtnArr/null
     */
    private String matchesResult4Single(CharSequence charBuff, Pattern pattern, int groupIndex) {
        Matcher matcherInst = pattern.matcher(charBuff);
        if (matcherInst.find()) {
            return matcherInst.group(groupIndex);
        }
        return null;
    }

    /**
     * 读文件内容
     * @param fileInst
     * @throws IOException
     */
    private void readFile(File fileInst, InnerCallback innerCallback) throws IOException {
        FileInputStream fins = new FileInputStream(fileInst);
        FileChannel fileChannel = fins.getChannel();
        MappedByteBuffer mbbInst = fileChannel.map(MapMode.READ_ONLY, 0, fileInst.length());
        innerCallback.callBack(decoder.decode(mbbInst));
        fileChannel.close();
    }

    /**
     * 定义回调规则
     * @author WangYanCheng
     * @version 2011-11-1
     */
    interface InnerCallback {
        /**
         * 回调方法
         * @param charBuff 字符序列对象
         */
        void callBack(CharBuffer charBuff);
    }

    /**
     * 测试入口
     * @param args
     */
    public static void main(String[] args) {
        //String filePath = "D:\\work\\workspace\\mywork\\src\\org\\ybygjy\\basic\\file\\OpenFile4OS.java";
        String filePath = "/Users/MLS/cancel_order.sql.txt";
        FileGrep fgInst = FileGrep.getInst(Charset.forName("UTF-8"));
        try {
            String[] tmpArr = fgInst.readFileContent(new File(filePath));
            for (String str : tmpArr) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
