package org.ybygjy.test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 负责封装系统测试公共行为
 * @author WangYanCheng
 * @version 2010-7-30
 */
public class TestUtils {
    /**
     * 创建URL
     * 
     * <pre>
     * String domainStr = "http://localhost:8080";
     * String sourceStr = "/org.ybygjy.Servlet
     * Map<String,String> paramCollect = new HashMap<String,String>();
     * paramCollect.push("USER_NAME","Anonymous");
     * paramCollect.push("USER_PASS", "Anonymous");
     * URL urlInst = TestUtils.doCreateURL(domainStr, sourceStr, paramCollect);
     * </pre>
     * @param domainStr 域名
     * @param sourceDesc 请求资源描述
     * @param paramCollect 参数集
     * @return rtnURL rtnURL
     */
    public static URL doCreateURL(String domainStr, String sourceDesc, Map<String, Object> paramCollect) {
        URL rtnURL = null;
        StringBuilder sbud = new StringBuilder("?");
        if (null != paramCollect) {
            for (Iterator iterator = paramCollect.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry<String, String> entry = (Map.Entry) iterator.next();
                sbud.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        sbud.setLength(sbud.length() == 1 ? 1 : sbud.length() - 1);
        try {
            domainStr = domainStr.concat(
                domainStr.matches(".*/$") ? sourceDesc.matches("^/.*") ? sourceDesc : "/"
                    .concat(sourceDesc) : sourceDesc).concat(sbud.toString());
            rtnURL = new URL(domainStr);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return rtnURL;
    }

    /**
     * 创建URLConnection,{@link URLConnection}
     * @param urlInst urlInst
     * @param rmeEnum HTTP method
     */
    public static HttpURLConnection doCreateURLConn(URL urlInst, RequestMethodEnum rmeEnum) {
        HttpURLConnection rtnURL = null;
        try {
            rtnURL = (HttpURLConnection) urlInst.openConnection();
            rtnURL.setRequestMethod(rmeEnum.name());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return rtnURL;
    }

    /**
     * 负责将二维数组转换成Map
     * @param mapInst 外部mapInst如果为空则会自动创建实例
     * @param arrayInst
     * @return
     */
    public static Map<String, Object> doConvertArray2Map(Map<String, Object> mapInst, String[][] arrayInst) {
        mapInst = null == mapInst ? new HashMap<String, Object>() : mapInst;
        for (int i = arrayInst.length - 1; i >= 0; i--) {
            mapInst.put(arrayInst[i][0], arrayInst[i][1]);
        }
        return mapInst;
    }

    /**
     * 负责打印Map实例信息
     * @param paramMap map实例
     */
    public static final void doPrint(Map<? extends Object, ? extends Object> paramMap) {
        doPrint("Begin TestUtils#doPrint...");
        /*for (Iterator iterator = paramMap.keySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            doPrint(entry.getKey() + ":" + entry.getValue());
        }*/
        for (Iterator iterator = paramMap.keySet().iterator(); iterator.hasNext();) {
            Object keyObj = iterator.next();
            doPrint(keyObj + ":\t" + paramMap.get(keyObj));
        }
        doPrint("End TestUtils#doPrint...");
    }

    /**
     * doPrint
     * @param arrInst arrInst
     */
    public static final void doPrint(String[] arrInst) {
        StringBuilder sbuInst = new StringBuilder("[");
        sbuInst.append(arrInst.getClass().getSimpleName()).append(":");
        for (String str : arrInst) {
            sbuInst.append(str).append(";");
        }
        sbuInst.append("]");
        System.out.println(sbuInst);
    }

    /**
     * doPrint
     * @param obj obj
     */
    public static final void doPrint(Object obj) {
        System.out.println(obj);
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        RequestMethodEnum[] rmeArray = RequestMethodEnum.values();
        for (RequestMethodEnum rmeStr : rmeArray) {
            System.out.println(rmeStr.name());
        }
    }
}
