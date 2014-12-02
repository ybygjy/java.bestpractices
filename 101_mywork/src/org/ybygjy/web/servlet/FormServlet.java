package org.ybygjy.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 封装表单提交
 * @author WangYanCheng
 * @version 2012-9-19
 */
public class FormServlet extends HttpServlet {

    /**
     * serial number
     */
    private static final long serialVersionUID = -4968424249154415420L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
        IOException {
        /*Map<String, Object> paramMap = request.getParameterMap();
        for (Iterator<String> iterator = paramMap.keySet().iterator(); iterator.hasNext();) {
            Object key = iterator.next();
            System.out.println(key + ":" + paramMap.get(key));
        }*/
        doPrintStream(request);
    }
    private void doPrintStream(HttpServletRequest request) {
        InputStream ins = null;
        PrintStream ous = null;
        byte[] buff = new byte[1024];
        try {
            ins = request.getInputStream();
            ous = System.out;
            int flag = -1;
            while ((flag = ins.read(buff))!=-1) {
                ous.write(buff, 0 , flag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != ins) {
                try {
                    ins.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
