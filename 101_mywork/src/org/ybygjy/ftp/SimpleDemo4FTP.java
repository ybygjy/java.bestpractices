package org.ybygjy.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;

/**
 * FTP Demo
 * @author WangYanCheng
 * @version 2011-5-27
 */
public class SimpleDemo4FTP {
    private FTPClient ftpClient;;
    public SimpleDemo4FTP() {
        //ftpClient = FTPServerMgr.createFTPClient();
    }
    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
    /**
     * 向远程服务器上传资源
     * @param remoteDir 远程目录，为空则默认当前活动目录
     * @param destResource 上传文件对象
     * @return {true：成功;false：失败}
     */
    public boolean uploadFile(String remoteDir, File destResource) {
        boolean rtnFlag = false;
        InputStream ins = null;
        try {
            ftpClient.changeWorkingDirectory("tmp");
            if (null != remoteDir) {
                ftpClient.changeToParentDirectory();
                //需要注意相对路径与决对路径
                ftpClient.changeWorkingDirectory(remoteDir);
            }
            ins = (new FileInputStream(destResource));
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
            rtnFlag = ftpClient.storeFile(destResource.getName(), ins);
            ins.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return rtnFlag;
    }
    /**
     * 获取远程目录的结构树
     * @param rootPath 目录路径
     * @return ftpFiles 文件项目对象/null
     */
    public FTPFile[] listDir(String rootPath){
        FTPFile[] ftpFiles = null;
        try {
            ftpFiles = ftpClient.listFiles(rootPath);
            printFTPFiles(ftpFiles);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return ftpFiles;
    }
    public void list4ListParse(String dirPath) {
        FTPListParseEngine ftpParseEngine = null;
        try {
            ftpParseEngine = ftpClient.initiateListParsing(dirPath);
            while (ftpParseEngine.hasNext()) {
                FTPFile[] ftpFiles = ftpParseEngine.getNext(2);
                printFTPFiles(ftpFiles);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 下载资源
     * @param remoteFile 资源路径
     * @param storeDir 文件存储目录
     * @return refFileInst 本地文件对象/null
     */
    public File download(String remoteFile, File storeDir) {
        if (remoteFile == null || !remoteFile.matches(".*[^\\\\]+$")) {
            return null;
        }
        File tmpFile = new File(storeDir, remoteFile.indexOf('\\') != -1 ? remoteFile.substring(remoteFile.lastIndexOf('\\')) : "tmpFtpFile");
        ReadableByteChannel readChannel = null;
        FileChannel fileChannel = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Charset charset = Charset.forName("GBK");
        try {
            readChannel = Channels.newChannel(ftpClient.retrieveFileStream(remoteFile));
            fileChannel = new FileOutputStream(tmpFile).getChannel();
//            fileChannel.transferFrom(readChannel, 0, 1024);
            while (readChannel.read(byteBuffer)!= -1) {
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                byteBuffer.flip();
                System.out.println(charset.decode(byteBuffer).toString());
                byteBuffer.clear();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (readChannel != null) {
                try {
                    readChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return tmpFile;
    }
    /**
     * 删除文件
     * @param filePath 文件全路径
     * @return {true：成功；false：失败}
     */
    public boolean delete(String filePath) {
        try {
            return ftpClient.deleteFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void destroy() {
        FTPServerMgr.destroyFTPClient(ftpClient);
    }
    /**
     * 打印FTPFile数组内容
     * @param ftpFiles ftpFiles
     */
    static void printFTPFiles(FTPFile[] ftpFiles) {
        StringBuilder sbud = new StringBuilder();
        for (FTPFile ftpFile : ftpFiles) {
            sbud.append(ftpFile.getName())
            .append("\n").append(ftpFile.getGroup())
            .append("\t").append(ftpFile.getHardLinkCount())
            .append("\t").append(ftpFile.getLink())
            .append("\t").append(ftpFile.getSize())
            .append("\t").append(ftpFile.getType())
            .append("\t").append(ftpFile.getUser())
            //.append("\t\n").append(ftpFile.getRawListing())
            //.append("\t").append(ftpFile.toFormattedString())
            .append("\n\n");
        }
        System.out.println(sbud.toString());
    }
}
