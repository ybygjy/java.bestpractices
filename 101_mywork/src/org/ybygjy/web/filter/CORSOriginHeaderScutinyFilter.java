package org.ybygjy.web.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨站资源访问检查
 * <p>参考资源：http://www.javaarch.net/jiagoushi/995.htm</p>
 * @author WangYanCheng
 * @version 2014-6-25
 */
public class CORSOriginHeaderScutinyFilter implements Filter {
    /** 用于缓存域解析的IP地址*/
    private Map<String, String> domainIPCache = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("org.ybygjy.web.filter.CORSOriginHeaderScutinyFilter.init(FilterConfig)");
        this.domainIPCache = new TreeMap<String, String>();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        System.out.println("org.ybygjy.web.filter.CORSOriginHeaderScutinyFilter.doFilter(ServletRequest, ServletResponse, FilterChain)");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Enumeration<String> headerNames = httpRequest.getHeaderNames();
        boolean isValid = false;
        String headerName = null;
        String origin = null;
        String domainIP = null;
        while (headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            if ("origin".equalsIgnoreCase(headerName)) {
                origin = httpRequest.getHeader(headerName);
                break;
            }
        }
        if (origin != null && !"".equals(origin.trim())) {
            try {
                origin = origin.toLowerCase();
                origin = origin.replaceFirst("http://", "");
                origin = origin.replaceFirst("https://", "");
                if (this.domainIPCache.get(origin) != null) {
                    domainIP = this.domainIPCache.get(origin);
                } else {
                    InetAddress clientAddress = InetAddress.getByName(origin);
                    if (null != clientAddress) {
                        domainIP = clientAddress.getHostAddress();
                        this.domainIPCache.put(origin, domainIP);
                    }
                }
                if (null != domainIP && domainIP.equals(httpRequest.getRemoteAddr())) {
                    isValid = true;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        if (isValid) {
            chain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {
        System.out.println("org.ybygjy.web.filter.CORSOriginHeaderScutinyFilter.destroy()");
    }
}
