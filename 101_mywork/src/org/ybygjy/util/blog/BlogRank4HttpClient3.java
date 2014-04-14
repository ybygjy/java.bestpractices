package org.ybygjy.util.blog;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Blog排名#HttpClient3版本
 * <p>技术原理：</p>
 * <p>
 * 1、使用Get请求取主页内容
 * 2、解析主页内容取各主题内容
 * 3、解析分页内容进行内容翻页
 * </p>
 * @author WangYanCheng
 * @version 2014-4-10
 */
public class BlogRank4HttpClient3 {
    private String webSit = "http://blog.csdn.net/ybygjy";
    private HttpClient httpClient;
    public BlogRank4HttpClient3() {
        httpClient = new HttpClient(new SimpleHttpConnectionManager());
    }
    public String[] doAnalysisURI(String htmlContent) {
        List<String> urlArray = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<a\\shref=\"([^\"]+)\">");
        Matcher matcher = pattern.matcher(htmlContent);
        while (matcher.find()) {
            urlArray.add(matcher.group(1));
        }
        return urlArray.toArray(new String[urlArray.size()]);
    }
    public void doRequest(String host, String[] urls) throws URIException, NullPointerException {
        for (String uri : urls) {
            System.out.println(uri);
            GetMethod getMethod = new GetMethod();
            addRequestHeader(getMethod);
            getMethod.setPath(uri);
            getMethod.setURI(new URI(host + uri, true));
            innerRequest(getMethod);
        }
    }
    private void innerRequest(GetMethod method) {
        try {
            int responseCode = httpClient.executeMethod(method);
            System.out.println(method.getURI().toString() + ":responseCode=>" + responseCode);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
    }
    public void doWork() {
        GetMethod httpMethod = new GetMethod(webSit);
        addRequestParameter(httpMethod);
        addRequestHeader(httpMethod);
        try {
            int responseCode = httpClient.executeMethod(httpMethod);
            if (HttpStatus.SC_OK == responseCode) {
                //取内容
                String charset = httpMethod.getResponseCharSet();
                BufferedInputStream inst = new BufferedInputStream(httpMethod.getResponseBodyAsStream());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buff = new byte[1024];
                int flag = -1;
                while ((flag = inst.read(buff)) != -1) {
                    baos.write(buff, 0, flag);
                }
                String htmlContent = baos.toString(charset);
                String[] uris = doAnalysisURI(htmlContent);
                doRequest("http://blog.csdn.net", uris);
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addRequestParameter(GetMethod httpMethod) {
        httpMethod.setQueryString("viewmode=contents");
    }
    /**
     * 添加请求头
     * @param httpMethod
     */
    private void addRequestHeader(GetMethod httpMethod) {
        httpMethod.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml");
        httpMethod.addRequestHeader("Accept-Language", "zh-CN,zh");
        httpMethod.addRequestHeader(new Header("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31"));
    }
    public static void main(String[] args) {
        new BlogRank4HttpClient3().doWork();
    }
}
