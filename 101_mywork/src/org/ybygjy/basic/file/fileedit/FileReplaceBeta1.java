package org.ybygjy.basic.file.fileedit;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 第一版文件替换
 * @author WangYanCheng
 * @version 2011-12-29
 */
public class FileReplaceBeta1 {
    private File fileInst;
    private String[] tokenArr;
    private CharsetDecoder decoder;
    private CharsetEncoder encoder;
    private Pattern ctxPattern;
    private Pattern fileFilter;

    /**
     * Constructor
     * @param fileInst 文件实例
     * @param tokenArr 参数列表，{0:用于内容匹配的表达式;1:用于匹配替换的表达式;2:用于过滤匹配文件的表达式}
     * @param charsetName 字符集
     */
    public FileReplaceBeta1(File fileInst, String[] tokenArr, String charsetName) {
        this.fileInst = fileInst;
        this.tokenArr = tokenArr;
        ctxPattern = Pattern.compile(this.tokenArr[0]);
        fileFilter = Pattern.compile(this.tokenArr[2] == null ? ".*" : this.tokenArr[2]);
        this.decoder = Charset.forName(charsetName).newDecoder();
        this.encoder = Charset.forName(charsetName).newEncoder();
    }

    /**
     * doWork
     * @throws IOException IOException
     */
    public void doWork() throws IOException {
        if (this.fileInst == null) {
            throw new RuntimeException("目标文件必须非空！");
        }
        recursingPFile(fileInst);
        try {
            OpenFile4OS.doOpenFile(LoggerUtils.getLogFile());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归处理文件
     * @param fileInst 文件/文件夹实例
     * @throws IOException IO异常
     */
    public void recursingPFile(File fileInst) throws IOException {
        if (fileInst.isFile()) {
            readFile(fileInst);
            return;
        }
        if (fileInst.isDirectory()) {
            File[] files = fileInst.listFiles(new FileFilter() {
                public boolean accept(File dir) {
                    String fileName = dir.getName();
                    return dir.isDirectory() ? true : fileFilter.matcher(fileName).matches();
                }
            });
            if (null != files) {
                for (File tmpFile : files) {
                    recursingPFile(tmpFile);
                }
            }
        }
    }

    /**
     * 读文件内容并配合匹配/替换逻辑
     * @param fileInst 文件实例
     * @throws IOException 异常
     */
    public void readFile(File fileInst) throws IOException {
        RandomAccessFile rafInst = null;
        FileChannel fileChannel = null;
        try {
            rafInst = new RandomAccessFile(fileInst, "rw");
            fileChannel = rafInst.getChannel();
            MappedByteBuffer mbbInst = fileChannel.map(MapMode.READ_WRITE, 0, fileChannel.size());
            if (matchAndReplace(decoder.decode(mbbInst), fileChannel)) {
                LoggerUtils.logger(fileInst.getAbsolutePath());
            }
        } catch (IOException ioe) {
            LoggerUtils.error("文件读取错误 ".concat(fileInst.getAbsolutePath()), ioe);
            throw ioe;
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

    /**
     * 负责匹配\替换\回写文件内容
     * @param charSeq 源内容
     * @param fileChannel 通道
     * @return rtnFlag {true:匹配且替换;false:未匹配}
     * @throws IOException IOException
     */
    public boolean matchAndReplace(CharSequence charSeq, FileChannel fileChannel) throws IOException {
        Matcher matcher = ctxPattern.matcher(charSeq);
        if (matcher.find()) {
            try {
                long tmpL = fileChannel.write(
                    encoder.encode(CharBuffer.wrap(matcher.replaceAll(tokenArr[1]))), 0);
                if (fileChannel.size() != tmpL) {
                    fileChannel = fileChannel.truncate(tmpL);
                }
            } catch (CharacterCodingException e) {
                LoggerUtils.error("读取文件时的字符串转换异常！", e);
            } catch (IOException e) {
                LoggerUtils.error("读取文件时的I/O异常！", e);
            }
            return true;
        }
        return false;
    }

    /**
     * 日志记录器
     * @author WangYanCheng
     * @version 2011-12-29
     */
    static class LoggerUtils {
        private Logger logger = Logger.getLogger("文件内容替换");
        private File logFile;
        private static LoggerUtils luInst = new LoggerUtils();
        public LoggerUtils() {
            logFile = new File(this.getClass().getResource(".").getFile(), "FileReplace".concat(
                String.valueOf((long) Math.random())).concat(".log"));
            try {
                FileHandler fhInst = new FileHandler(logFile.getAbsolutePath());
                fhInst.setFormatter(new java.util.logging.Formatter() {
                    @Override
                    public String format(LogRecord record) {
                        StringBuilder sbud = new StringBuilder();
                        sbud.append(record.getMessage())
                        .append((record.getThrown() != null ? "\r\n\t".concat(record.getThrown().toString()) : ""))
                        .append("\r\n");
                        return sbud.toString();
                    }
                });
                logger.addHandler(fhInst);
                logger.info("Log文件地址".concat(logFile.getAbsolutePath()));
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public static void logger(String msg) {
            luInst.logger.info(msg);
        }
        public static void error(String msg, Throwable th) {
            luInst.logger.log(Level.WARNING, msg, th);
        }
        public static File getLogFile() {
            return luInst.logFile;
        }
    }
}
