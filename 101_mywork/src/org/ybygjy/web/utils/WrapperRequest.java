package org.ybygjy.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestUtils
 * @author WangYanCheng
 * @version 2010-1-10
 */
public class WrapperRequest {
    /** ContentType */
    public enum ContentType {
        /** MIME类型-二进制数据标记 */
        FORM_DATA("multipart/form-data"),
        /** MIME类型-标准编码格式标记 */
        FORM_URLENCODE("application/x-www-form-urlencoded"),
        /** MIME类型-文本格式标记 */
        FORM_TEXT("text/plain");
        /** inner type */
        private final String type;

        /**
         * Constructor
         * @param str type
         */
        private ContentType(String str) {
            this.type = str;
        }

        /**
         * getter Type
         * @return type
         */
        public String getType() {
            return this.type;
        }
    }

    /** ContentType */
    private String contentType;
    /** request */
    private HttpServletRequest request;

    /**
     * Constructor
     * @param request request
     */
    private WrapperRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * getInstance
     * @param request request
     * @return wrapperRequest
     */
    public static final WrapperRequest getInstance(HttpServletRequest request) {
        return new WrapperRequest(request);
    }

    /**
     * get no wrapper Request
     * @return request request/null
     */
    public HttpServletRequest getRequest() {
        return this.request;
    }

    /**
     * getContentType
     * @return contentTypeStr/null
     */
    public String getContentType() {
        if (null == this.contentType) {
            this.contentType = null == this.request ? null : this.request.getContentType();
        }
        return this.contentType;
    }

    /**
     * 匹配流标记头
     * @param contentType 头信息
     * @return true/false
     */
    public static boolean matchFileContentType(String contentType) {
        boolean rtnBool = false;
        rtnBool = contentType.startsWith(ContentType.FORM_DATA.getType())
            || contentType.startsWith(ContentType.FORM_URLENCODE.getType());
        return rtnBool;
    }

    /**
     * 是否二制数据格式
     * @return true/false
     */
    public boolean isBinaryData() {
        boolean rtnBool = false;
        String tmpStr = getContentType();
        if (tmpStr.contains(ContentType.FORM_DATA.getType())) {
            rtnBool = true;
        }
        return rtnBool;
    }

    /**
     * 取得内容界定符
     * @return rtnStr/null
     */
    public String getBoundary() {
        String rtnStr = null;
        String tmpType = getContentType();
        if (null != tmpType) {
            rtnStr = tmpType.matches("^[\\s\\S]*boundary=[\\s\\S]*$") ? tmpType.split("boundary=")[1]
                : null;
        }
        return null == rtnStr ? null : "--".concat(rtnStr);
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        // WrapperRequest.ContentType[] cts =
        // WrapperRequest.ContentType.values();
        // for (WrapperRequest.ContentType ct : cts) {
        // System.out.println(ct.getType());
        // }
        // System.out.println(WrapperRequest.getInstance(null).getBoundary());
        /*
         * Matcher matcher = Pattern.compile("(\\s)+").matcher("王\n\r延成"); while
         * (matcher.find()) { int count = matcher.groupCount(); for (int i = 0;
         * i < count; i++) { byte[] tmpByte = matcher.group(i).getBytes(); for
         * (int tmpI : tmpByte) { System.out.print(tmpI); } } }
         */
        WrapperRequest wpInst = WrapperRequest.getInstance(null);
        wpInst.getBoundary();
    }
}
