package org.ybygjy.basic.file.cv2Swf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 文件转换swf格式支持
 * @author WangYanCheng
 * @version 2009-12-3
 */
public class FileConvert4SwfImpl {
    /**转换完成后的文件名称*/
    private File fileInst = null;
    /**输出目录*/
    private File outFolder = null;
    /**
     * {@inheritDoc}
     * <strong>需要验证fileInst是否为空</strong>
     */
    public File doConvertFile(File inputFileInst, File outFilePath) {
        new InnerConvertThread(inputFileInst, outFilePath);
        return fileInst;
    }
    /**
     * {@inheritDoc}
     * <strong>需要验证fileInst是否为空</strong>
     */
    public File doConvertFile(File inputFileInst) {
        new InnerConvertThread(inputFileInst, null);
        return fileInst;
    }
    /**
     * setOutFolder
     * @param setOutFolder setOutFolder
     */
    public void setOutFolder(File setOutFolder) {
        this.outFolder = setOutFolder;
    }
    /**
     * {@inheritDoc}
     */
    public String getCommand() {
        return defaultCommand;
    }
    /**
     * {@inheritDoc}
     */
    public void setCommand(String tmpCommand) {
        this.defaultCommand = tmpCommand;
    }
    /**default command*/
    private String defaultCommand = "FlashPrinter.exe";
    /**
     * InnerConvert
     * @author WangYanCheng
     * @version 2009-12-4
     */
    class InnerConvertThread implements Runnable {
        /**inputFile*/
        private File inFile = null;
        /**outFilePath*/
        private File outFile = new File(System.getProperty("java.io.tmpdir"));
        /**
         * Constructor
         * @param inFileObj inFileObj
         * @param outFilePath outFilePath
         */
        public InnerConvertThread(File inFileObj, File outFilePath) {
            this.inFile = inFileObj;
            this.outFile = outFolder;
            Thread th = new Thread(this);
            th.setDaemon(true);
            th.start();
        }
        /**
         * Constructor
         * @param setInFile input file to set
         * @param setOutFolder output Folder to set
         */
        public InnerConvertThread(String setInFile, String setOutFolder) {
            this.inFile = new File(setInFile);
            this.outFile = null == setOutFolder ? outFile : new File(setOutFolder);
            new InnerConvertThread(inFile, outFile);
        }
        /**
         * {@inheritDoc}
         */
        public void run() {
            String tmpOutFile = outFile.getPath()
            .concat(File.separator).concat(inFile.getName().replaceAll("[.]{1}.*$", ".swf"));
            fileInst = new File(tmpOutFile);
            List<String> commandArray = new ArrayList<String>();
            commandArray.add(defaultCommand);
            commandArray.add(inFile.getPath());
            commandArray.add("-o");
            commandArray.add(tmpOutFile);
            ProcessBuilder pbObj = new ProcessBuilder();
            pbObj.command(commandArray);
            Map<String, String> envMap = pbObj.environment();
            envMap.clear();
            envMap.putAll(System.getenv());
            pbObj.directory(outFile);
            pbObj.redirectErrorStream(true);
            try {
                Process proObj = pbObj.start();
                final InputStream ins = proObj.getInputStream();
                final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                Thread th = new Thread() {
                    public void run() {
                        ReadableByteChannel rbcObj = Channels.newChannel(ins);
                        while (true) {
                            try {
                                while (rbcObj.read(byteBuffer) != -1) {
                                    byteBuffer.flip();
                                    System.out.println(java.nio.charset.Charset.defaultCharset().decode(byteBuffer));
                                    byteBuffer.clear();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                th.setDaemon(true);
                th.start();
                try {
                    System.out.println("转换完成:" + proObj.waitFor());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        File inputFileInst = new File(args[0]),
        outputFileInst = new File(args[1]);
        FileConvert4SwfImpl fc = new FileConvert4SwfImpl();
        try {
            fc.setCommand(args[2]);
            fc.doConvertFile(inputFileInst, outputFileInst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
