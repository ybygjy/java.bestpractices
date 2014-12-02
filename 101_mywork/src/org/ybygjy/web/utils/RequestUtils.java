package org.ybygjy.web.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 封装请求头处理公共行为
 * @author WangYanCheng
 * @version 2011-3-25
 */
public class RequestUtils {
    /**
     * 打印对象属性
     * @param request request
     */
    public static final void doPrintParam(HttpServletRequest request) {
        Enumeration enumInst = request.getParameterNames();
        while (enumInst.hasMoreElements()) {
            String paramName = (String) enumInst.nextElement();
            System.out.println(paramName + "\t:" + request.getParameter(paramName));
        }
    }
}
