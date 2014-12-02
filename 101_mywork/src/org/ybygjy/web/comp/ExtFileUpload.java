package org.ybygjy.web.comp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 使用第三方库构建文件上传组件
 * @author WangYanCheng
 * @version 2010-12-16
 */
public class ExtFileUpload {
    /**
     * 解析流提取文件内容
     * @param request 请求头
     */
    public void doParseContent(HttpServletRequest request) {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = null;
        InputStream ins = null;
        try {
            System.out.println(request.getParameter("dbc") + "-->");
            ins = request.getInputStream();
            byte[] buff = new byte[1024];
            ins.read(buff, 0, buff.length);
            System.out.println(new String(buff));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            items = upload.parseRequest(request);
            for (FileItem item : items) {
                System.out.println((item.isFormField() ? "FormField:" : "FileData:") + item.getString());
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }
}
