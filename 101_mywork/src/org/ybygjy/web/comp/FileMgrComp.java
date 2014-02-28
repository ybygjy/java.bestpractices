package org.ybygjy.web.comp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ybygjy.web.utils.WrapperRequest;

/**
 * 负责对文件实体进行的操作
 * @author WangYanCheng
 * @version 2010-8-27
 */
public class FileMgrComp {

    /** serialNumber */
    private static final long serialVersionUID = 5563862601527965192L;
    /** boundaryStr */
    private String boundaryStr;
    /** ifcArray */
    private List<FileItem> ifcArray;

    /**
     * Constructor
     */
    public FileMgrComp() {
        ifcArray = new ArrayList<FileItem>();
    }

    /**
     * doService
     * @param wrRequest wrRequest
     * @param response response
     * @return rtnList rtnList/null
     * @throws IOException IOException
     */
    public List<FileItem> doAnalyse(WrapperRequest wrRequest, HttpServletResponse response) throws IOException {
doInnerTest(wrRequest.getRequest());
//doPrintRequestData(wrRequest.getRequest());
        List<FileItem> fileArr = null;
        if (wrRequest.isBinaryData()) {
            this.boundaryStr = wrRequest.getBoundary();
            if (null != boundaryStr) {
                fileArr = doAnalyseBinaryData(wrRequest);
            }
        }
        return fileArr;
    }

    /**
     * 分析存储二进制数据
     * @param wrRequest wrRequest
     * @return fileItemArr fileItemArr
     */
    private List<FileItem> doAnalyseBinaryData(WrapperRequest wrRequest) {
        BufferedInputStream bins = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            bins = new BufferedInputStream(wrRequest.getRequest().getInputStream());
            int tmpI = -1;
            int tmpL = -1;
            while ((tmpI = bins.read()) != -1) {
                if (tmpI == 13) {
                    tmpL = (bins.read());
                    if (tmpL == 10) {
                        if (baos.size() == 0) {
                            continue;
                        }
                        FileItem fi = analyseFileInput(baos, bins);
                        if (fi != null) {
                            ifcArray.add(fi);
                        }
                        baos.reset();
                        continue;
                    }
                    baos.write(tmpI);
                    baos.write(tmpL);
                }
                baos.write(tmpI);
            }
            System.out.print(baos.toString("UTF-8"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (null != bins) {
                try {
                    baos.close();
                    bins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.getIfcArray();
    }

    /**
     * 解析验证上传内容是否文件类型
     * @param os outStream
     * @param ins insStream
     * @return ifcInst ifcInst/null
     */
    private FileItem analyseFileInput(ByteArrayOutputStream os, InputStream ins) {
        String tmpStr = null;
        try {
            tmpStr = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        FileItem ifcIns = null;
        if (tmpStr.indexOf("filename") != -1) {
            String[] tmpStrArr = tmpStr.split(";");
            if (tmpStrArr.length > 2) {
                // 取MIME文件类型
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    doRead(ins, baos);
                    tmpStr = baos.toString();
                    if (tmpStr.startsWith("Content-Type:")) {
                        ifcIns = new FileItem(tmpStrArr[1].trim(), tmpStrArr[2].trim(),
                            tmpStr.split(":")[1].trim(), this.boundaryStr);
                        if (ifcIns.getSrcFileFullName() != null) {
                            ifcIns.doStoreFile(ins);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        baos.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }
        return ifcIns;
    }

    /**
     * doRead
     * @param ins ins
     * @param baos baos
     * @throws IOException IOException
     */
    private void doRead(InputStream ins, ByteArrayOutputStream baos) throws IOException {
        int tmpI = -1;
        while ((tmpI = ins.read()) != -1) {
            if (tmpI == 13) {
                tmpI = ins.read();
                if (tmpI == 10) {
                    break;
                } else {
                    baos.write(13);
                    baos.write(tmpI);
                    continue;
                }
            }
            baos.write(tmpI);
        }
    }

    /**
     * getIfcArray
     * @return the ifcArray
     */
    public List<FileItem> getIfcArray() {
        return ifcArray;
    }

    /**
     * innerTest
     * @param request request
     */
    private void doInnerTest(HttpServletRequest request) {
        Map<String, Object> testInfo = new HashMap<String, Object>();
        testInfo.put("AuthType", request.getAuthType());
        testInfo.put("CharacterEncoding", request.getCharacterEncoding());
        testInfo.put("ContentLength", request.getContentLength());
        testInfo.put("ContentType", request.getContentType());
        testInfo.put("ContextPath", request.getContextPath());
        testInfo.put("HeaderNames", request.getHeaderNames());
        testInfo.put("LocalAddr", request.getLocalAddr());
        testInfo.put("LocalName", request.getLocalName());
        testInfo.put("PathInfo", request.getPathInfo());
        testInfo.put("RequestedSessionId", request.getRequestedSessionId());
        testInfo.put("UserPrincipal", request.getUserPrincipal());
        org.ybygjy.test.TestUtils.doPrint(testInfo);
    }
    /**
     * 以io流的形式打印给定request存储的数据
     * @param request 请求头
     */
    public static void doPrintRequestData(HttpServletRequest request) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ReadableByteChannel rbc = null;
        Charset charset = Charset.forName("UTF-8");
        try {
            rbc = Channels.newChannel(request.getInputStream());
            while (rbc.read(buffer) != -1) {
                buffer.flip();
                System.out.println((charset.decode(buffer)).toString());
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != rbc) {
                try {
                    rbc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
