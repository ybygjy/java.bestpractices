package org.ybygjy.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet所谓的单实例多线程
 * <p>1、首先Servlet生命周期是由Servlet Container负责<p>
 * <p>2、Servlet实例在同一个Container中只有一个，该实例被多线程并发访问</p>
 * <p>3、关键词：线程池、分派线程、工作线程</p>
 * <p>4、当容器收到多个同一servlet请求时，该servlet的services方法将被多个线程并发访问</p>
 * @author WangYanCheng
 * @version 2011-4-7
 */
@SuppressWarnings("serial")
public class SingleThreadServlet extends HttpServlet {
    /** 非线程安全*/
    private PrintWriter pw;
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException {
        String userName = req.getParameter("userName");
        pw = resp.getWriter();
        PrintWriter pww = pw;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t" + pw + "\t" + pww);
        pww.write(new String(userName.getBytes(), "ISO8859-1"));
    }
}
