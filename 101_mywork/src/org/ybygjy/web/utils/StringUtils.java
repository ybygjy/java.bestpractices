package org.ybygjy.web.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对字符串的公共操作
 * @author WangYanCheng
 * @version 2010-8-25
 */
public class StringUtils {
    /**singleton*/
    private static StringUtils suInst = new StringUtils();
    /**
     * singleton pattern
     */
    private StringUtils() {
    }
    /**
     * 取得字符串操作实例
     * @return strUtil 字符串公共操作实例
     */
    public static final StringUtils getInstance() {
        return suInst;
    }
    /**
     * 负责字符串占位符的分析替换
     * @param targetStr 源字符串
     * @param paramArray 替换规则设定,如{{"@STR@", "替换_1"}, {"@STR2@", "替换_2"}}
     * @return rtnStr 替换完成的字符串
     */
    public String doReplace(String targetStr, Map<String, String> paramArray) {
        Pattern pattern = Pattern.compile("(@(\\w+)@)", Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(targetStr);
        String rtnStr = targetStr;
        String key = null;
        String value = null;
        while (matcher.find()) {
            key = matcher.group(2);
            value = paramArray.get(key);
            rtnStr = rtnStr.replace(matcher.group(1), value);
        }
        return rtnStr;
    }
}
