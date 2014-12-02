package org.ybygjy.web.comp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.ybygjy.web.utils.FileUtils;

/**
 * 负责文件的存储解析
 * @author WangYanCheng
 * @version 2010-08-31
 */
public class FileItem {
    /** 源文件路径 */
    private String srcFilePath;
    /** 源文件全名称 */
    private String srcFileFullName;
    /** 源文件名称 */
    private String srcFileName;
    /** 文件转储路径 */
    private String filePath;
    /** 文件名称 */
    private String fileName;
    /** 文件全名称 */
    private String fileFullName;
    /** 上传文件参数名称 */
    private String paramName;
    /** MIME Type */
    private String mimeType;
    /** 分割串 */
    private String boundaryStr;

    /**
     * Constructor
     * @param paramStr 参数名称
     * @param fileStr 源文件地址串
     * @param mimeType MIMEType
     * @param boundaryStr 分割约束
     */
    public FileItem(String paramStr, String fileStr, String mimeType, String boundaryStr) {
        String[] tmpStrArr = paramStr.split("=");
        this.setParamName(tmpStrArr[1].substring(1, tmpStrArr[1].length() - 1));
        tmpStrArr = fileStr.split("=");
        this.setSrcFilePath(tmpStrArr[1].substring(1, tmpStrArr[1].length() - 1));
        this.setMIME(mimeType);
        this.setBoundaryStr(boundaryStr);
    }

    /**
     * setFilePath
     * @param filePath filePath
     */
    public void setSrcFilePath(String filePath) {
        this.srcFilePath = filePath;
        if (this.srcFilePath != null && filePath.length() > 0) {
            this.srcFileFullName = new File(this.srcFilePath).getName();
            this.srcFileName = this.srcFileFullName.substring(0, this.srcFileFullName.indexOf('.'));
        }
    }

    /**
     * setMIME
     * @param mimeType mimeType
     */
    public void setMIME(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * getMIME
     * @return mimeType mimeType
     */
    public String getMIME() {
        return this.mimeType;
    }

    /**
     * setBoundary
     * @param boundaryStr the boundaryStr to set
     */
    public void setBoundaryStr(String boundaryStr) {
        this.boundaryStr = boundaryStr;
    }

    /**
     * setParamName
     * @param paramName paramName
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * getParamName
     * @return paramName
     */
    public String getParamName() {
        return this.paramName;
    }

    /**
     * 源上传文件全名称
     * @return the srcFileName
     */
    public String getSrcFileFullName() {
        return srcFileFullName == null ? null : srcFileFullName.length() == 0 ? null : this.srcFileFullName;
    }

    /**
     * 源上传文件名称
     * @return the srcFileName
     */
    public String getSrcFileName() {
        return srcFileName;
    }

    /**
     * 文件存储路径
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 取文件名称
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 取文件全名称
     * @return the fileFullName
     */
    public String getFileFullName() {
        return fileFullName;
    }

    /**
     * 转储文件
     * @param ins ins
     * @throws IOException IOException
     */
    public void doStoreFile(InputStream ins) throws IOException {
        String id = String.valueOf(System.currentTimeMillis());
        File tmpFile = new File(FileUtils.getTmpFilePath(), id);
        this.filePath = tmpFile.getPath();
        this.fileFullName = tmpFile.getName();
        this.fileName = id;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(tmpFile));
            this.doStoreFile(ins, bos);
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            try {
                bos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * 存储文件
     * @param ins ins
     * @param bos bos
     * @throws IOException IOException
     */
    private void doStoreFile(InputStream ins, OutputStream bos) throws IOException {
        byte[] byteArr = new byte[this.boundaryStr.getBytes().length];
        try {
            int tmpI = -1;
            int tmpL = -1;
            ins.skip(2);
            while (((tmpI = ins.read()) != -1)) {
                if (13 == tmpI) {
                    tmpL = ins.read();
                    if (10 == tmpL && isBoundary(ins, byteArr)) {
                        break;
                    } else {
                        bos.write(tmpI);
                        bos.write(tmpL);
                        if (10 == tmpL) {
                            bos.write(byteArr);
                        }
                        continue;
                    }
                }
                bos.write(tmpI);
            }
            bos.flush();
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    /**
     * 检验是否边界
     * @param ins ins
     * @param byteArr byteArr
     * @return true/false
     * @throws IOException IOException
     */
    private boolean isBoundary(InputStream ins, byte[] byteArr) throws IOException {
        if (null == this.boundaryStr) {
            return false;
        }
        boolean rtnFlag = false;
        int count = ins.read(byteArr);
        if (count != -1) {
            String str = new String(byteArr, 0, count);
            if (this.boundaryStr.equals(str)) {
                rtnFlag = true;
            }
            byteArr = str.getBytes();
        }
        return rtnFlag;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FileItem [boundaryStr=");
        builder.append(boundaryStr);
        builder.append(", fileFullName=");
        builder.append(fileFullName);
        builder.append(", fileName=");
        builder.append(fileName);
        builder.append(", filePath=");
        builder.append(filePath);
        builder.append(", mimeType=");
        builder.append(mimeType);
        builder.append(", paramName=");
        builder.append(paramName);
        builder.append(", srcFileName=");
        builder.append(srcFileFullName);
        builder.append(", srcFilePath=");
        builder.append(srcFilePath);
        builder.append("]");
        return builder.toString();
    }
}
