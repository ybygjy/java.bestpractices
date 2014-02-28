package org.ybygjy.web.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.ybygjy.web.json.jsoninjava.JSONObject;

/**
 * ResponseUtils
 * @author WangYanCheng
 * @version 2010-1-10
 */
public class ResponseUtils {
    /**instance*/
    private static ResponseUtils ru = null;
    /**
     * singleton pattern
     */
    private ResponseUtils() {
    }
    /**
     * do get responseUtils instance
     * @return ruInst
     */
    public static final ResponseUtils getInstance() {
        if (null == ru) {
            ru = new ResponseUtils();
        }
        return ru;
    }
    /**
     * doResponse
     * @param response res
     * @param str str
     */
    public void doResponse(HttpServletResponse response, String str) {
        try {
            response.setContentType("text/html; charset=UTF-8");
            OutputStream osInst = response.getOutputStream();
            osInst.write(str.getBytes("UTF-8"));
            osInst.flush();
            osInst.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * doResponse
     * @param response resInst
     * @param dataMap dataMap
     */
    public void doResponse(HttpServletResponse response, Map<String, Object> dataMap) {
        doResponse(response, new JSONObject(dataMap).toString());
    }
}
