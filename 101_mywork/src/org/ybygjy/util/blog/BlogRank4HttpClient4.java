package org.ybygjy.util.blog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 利用HttpClient下载Blog内容
 * @author WangYanCheng
 * @version 2014-4-22
 */
public class BlogRank4HttpClient4 {
    private String blogSit = "http://blog.csdn.net";
    private Map<String, String> requestParam = new HashMap<String, String>();
    private Map<String, String> requestHeaderParam = new HashMap<String, String>();
    private AtomicInteger pageCount;
    private AtomicInteger articleCount;
    private HttpClient httpClient;
    private List<String> cachePage;
    public BlogRank4HttpClient4() {
        httpClient = new HttpClient(new SimpleHttpConnectionManager(false));
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        cachePage = new ArrayList<String>();
        pageCount = new AtomicInteger(0);
        articleCount = new AtomicInteger(0);
    }
    public void doWork() {
        doRequest(blogSit + "/ybygjy");
        System.out.println("共处理\r\n分页：" + pageCount.get() + "\t文章：" + articleCount.get());
    }
    /**
     * 请求指定URI
     * @param uri
     */
    public void doRequest(String uri) {
        HttpMethodBase httpMethod = createMethod(uri);
        try {
            int respCode = httpClient.executeMethod(httpMethod);
System.out.println(httpMethod.getURI() + "=>" + respCode);
            String responseCharset = httpMethod.getResponseCharSet();
            String htmlContent = new String(httpMethod.getResponseBody(), 0, (int) httpMethod.getResponseContentLength(), responseCharset);
            new HTMLParser(htmlContent, new HTMLParserCallback() {
                @Override
                public void afterParseUri(String[] pageUrls, String[] articleUrls) {
                    //访问分页，需要记录当前是第几页，防止出现递归，把内容存储在HttpHeader中
                    if (null != pageUrls) {
                        for (String pageUrl : pageUrls) {
                            String hashCode = String.valueOf(pageUrl.hashCode());
                            int flag = cachePage.indexOf(hashCode);
                            if (flag == -1) {
                                cachePage.add(hashCode);
                                pageCount.incrementAndGet();
                                doRequest(blogSit + pageUrl);
                            }
                        }
                    }
                    if (null != articleUrls) {
                        for (String articleUrl : articleUrls) {
                            String hashCode = String.valueOf(articleUrl.hashCode());
                            int flag = cachePage.indexOf(hashCode);
                            if (flag == -1) {
                                cachePage.add(hashCode);
                                articleCount.incrementAndGet();
                                doRequest(blogSit + articleUrl);
                            }
                        }
                    }
                }
            }).doParseHTML();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建请求实体
     * @return
     */
    private HttpMethodBase createMethod(String uri) {
        HttpMethodBase httpMethod = new PostMethod(uri);
        addRequestHeaderParam(httpMethod);
        addRequestParam(httpMethod);
        return httpMethod;
    }
    /**
     * 添加请求参数
     * @param httpMethod
     */
    private void addRequestParam(HttpMethodBase httpMethod) {
        List<NameValuePair> valuePairCollection = new ArrayList<NameValuePair>(); 
        for (Map.Entry<String, String> entry : requestParam.entrySet()) {
            valuePairCollection.add(new NameValuePair(entry.getKey(), entry.getValue()));
        }
        httpMethod.setQueryString(valuePairCollection.toArray(new NameValuePair[valuePairCollection.size()]));
    }
    /**
     * 添加请求头参数
     * @param httpMethod
     */
    private void addRequestHeaderParam(HttpMethodBase httpMethod) {
        for (Map.Entry<String, String> entry : requestHeaderParam.entrySet()) {
            httpMethod.addRequestHeader(entry.getKey(), entry.getValue());
        }
    }
    /**
     * 添加参数
     * @param key
     * @param value
     */
    public void addParam(String key, String value) {
        requestParam.put(key, value);
    }
    /**
     * 删除参数
     * @param key
     * @return
     */
    public String removeParam(String key) {
        return requestParam.remove(key);
    }
    /**
     * 添加请求头参数
     * @param key
     * @param value
     */
    public void addRequestHeaderParam(String key, String value) {
        requestHeaderParam.put(key, value);
    }
    /**
     * 移除请求头参数
     * @param key
     * @return
     */
    public String removeRequestHeaderParam(String key) {
        return requestHeaderParam.remove(key);
    }
    /**
     * 程序入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        BlogRank4HttpClient4 blogRank = new BlogRank4HttpClient4();
        blogRank.addRequestHeaderParam("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        blogRank.addRequestHeaderParam("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        blogRank.addRequestHeaderParam("Cache-Control", "no-cache");
        blogRank.addRequestHeaderParam("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36");
        blogRank.addParam("viewmode", "contents");
        blogRank.doWork();
    }
}
/**
 * 此类负责解析HTML提取特定HTML内容
 * @author WangYanCheng
 * @version 2014-4-22
 */
class HTMLParser {
    private String htmlContent;
    private List<String> pageUrl;
    private List<String> articleUrl;
    private HTMLParserCallback htmlParseCallback;
    public HTMLParser(String htmlContent, HTMLParserCallback htmlParseCallback) {
        this.htmlContent = htmlContent;
        this.pageUrl = new ArrayList<String>();
        this.articleUrl = new ArrayList<String>();
        this.htmlParseCallback = htmlParseCallback;
    }
    /**
     * 负责解析、转储HTML特定内容
     * @param htmlContent
     */
    public void doParseHTML() {
        parseURI(htmlContent);
    }
    /**
     * 解析逻辑
     * @param htmlContent
     */
    private void parseURI(String htmlContent) {
        String regexStr = "<a\\shref=\"(/[^\"]+)\">";
        Matcher matcher = Pattern.compile(regexStr).matcher(htmlContent);
        while (matcher.find()) {
            //验证是否是分页数据
            String articleUri = matcher.group(1);
            if (articleUri.matches(".*list/\\d+")) {
                pageUrl.add(articleUri);
            } else {
                articleUrl.add(articleUri);
            }
        }
        if (null != htmlParseCallback) {
            htmlParseCallback.afterParseUri(pageUrl.toArray(new String[pageUrl.size()]), articleUrl.toArray(new String[articleUrl.size()]));
        }
    }
    public List<String> getPageUrl() {
        return pageUrl;
    }
    public List<String> getArticleUrl() {
        return articleUrl;
    }
}
/**
 * HTML解析回调接口
 * @author WangYanCheng
 * @version 2014-4-22
 */
interface HTMLParserCallback {
    public void afterParseUri(String[] pageUrls, String [] articleUrls);
}
