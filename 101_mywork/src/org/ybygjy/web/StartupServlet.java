package org.ybygjy.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.ybygjy.web.log.LogMgr;

/**
 * 负责应用服务器启动时的资源加载、初始化
 * @author WangYanCheng
 * @version 2010-8-16
 */
public class StartupServlet extends HttpServlet {
    /**serialNumber*/
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void init() throws ServletException {
        ServletConfig servletConfig = getServletConfig();
        String filePath = getServletContext().getRealPath("/");
        String tmpStr = servletConfig.getInitParameter("logConf");
        LogMgr logMgrInst = new LogMgr();
        logMgrInst.doInitLogProperty(filePath.concat(tmpStr));
        //new InitServlet().init(super.getServletContext());
System.out.println("servletConfig.getServletContext:" + servletConfig.getServletContext());
System.out.println("servletConfig.getServletName:" + servletConfig.getServletName());
    }
}
