package org.ybygjy.web.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于测试HttpClient的Servlet
 * @author WangYanCheng
 * @version 2014-2-25
 */
public class HttpClientServlet implements Servlet {
    private ServletConfig servletConfig;
    @Override
    public void destroy() {
        System.out.println("org.ybygjy.web.servlet.HttpClientServlet#销毁");
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    @Override
    public String getServletInfo() {
        return "org.ybygjy.web.servlet.HttpClientServlet";
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        Enumeration enumInst = httpRequest.getHeaderNames();
        while (enumInst.hasMoreElements()) {
            String itemKey = String.valueOf(enumInst.nextElement());
            System.out.println(itemKey + ":" + httpRequest.getHeader(itemKey));
        }
//        System.out.println("org.ybygjy.web.servlet.HttpClientServlet");
        Map tmpMap = servletRequest.getParameterMap();
        for (Iterator iterator = tmpMap.keySet().iterator(); iterator.hasNext();) {
            System.out.println(iterator.next());
        }
        BufferedInputStream bisInst = new BufferedInputStream(servletRequest.getInputStream());
        byte[] buff = new byte[1024];
        int flag = -1;
        while ((flag = bisInst.read(buff)) != -1) {
            System.out.println(new String(buff, 0, flag));
        }
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.addHeader("Transfer-Encoding", "gzip");
        httpResponse.getOutputStream().write(new String("HelloWorld").getBytes());
    }
}
