package org.ybygjy.web.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 负责JQuery Ajax
 * <p>
 * 测试地址:http://localhost:8080/org.ybygjy.web.ajax.JQueryAjax?recType=[xml|json]
 * </p>
 * <p>
 * 功能责任描述:
 * </p>
 * <p>
 * 1、提供ajax测试数据的两种格式(xml/json)
 * </p>
 * <p>
 * 2、提供基本的CRUD模拟操作以支持ajax处理流程
 * </p>
 * @author WangYanCheng
 * @version 2011-7-11
 */
public class JQueryAjax extends HttpServlet {
    /** serialUID */
    private static final long serialVersionUID = -5890816541793988156L;
    /** 角色 */
    private static String[] ROLEMGR = {"总教练", "研究员", "队员"};
    /** 初始化测试数据 */
    private static List<JQueryAjaxPersonModel> objArray = new ArrayList<JQueryAjaxPersonModel>();
    /** XML格式 */
    private String xmlTmpl = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><users count=\"@C\">@U</users>";
    /** JSON格式 */
    private String jsonTmpl = "{\"count\":\"@C\",\"users\":[@U]}";
    
    @Override
    public void init() throws ServletException {
        initTestData();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException {
        String act = req.getParameter("act");
        int startRow = 0;
        int endRow = 0;
        if ("qryAll".equals(act)) {
            startRow = Integer.parseInt(req.getParameter("startRow"));
            endRow = Integer.parseInt(req.getParameter("endRow"));
            sendData(req, resp, buildTestData(startRow, endRow));
        } else if ("add".equals(act)) {
            responseData(req, resp, doAddUser(req));
        } else if ("remove".equals(act)) {
        } else if ("modify".equals(act)) {
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
        IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        super.service(req, resp);
    }

    /**
     * 传输数据
     * @param req 请求实例
     * @param resp 响应实例
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    private void sendData(HttpServletRequest req, HttpServletResponse resp,List<JQueryAjaxPersonModel> objArray) throws ServletException,
        IOException {
        String recType = req.getParameter("recType");
        byte[] strBuff = null;
        if ("xml".equals(recType)) {
            // text/xml、application/xml
            resp.setContentType("text/xml");
            strBuff = generalXML(objArray).getBytes("UTF-8");
        } else if ("json".equals(recType)) {
            resp.setContentType("text/json");
            strBuff = generalJSON(objArray).getBytes("UTF-8");
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentLength(strBuff.length);
        resp.getOutputStream().write(strBuff);
    }
    /**
     * 发送响应
     * @param req 请求体
     * @param resp 响应体
     * @param dataContent 响应数据内容
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    private void responseData(HttpServletRequest req, HttpServletResponse resp, String dataContent) throws ServletException, IOException {
        String recType = req.getParameter("recType");
        byte[] strBuff = null;
        if ("xml".equals(recType)) {
            // text/xml、application/xml
            resp.setContentType("text/xml");
            strBuff = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><code>@C</code></response>".replace("@C", dataContent).getBytes("UTF-8");
        } else if ("json".equals(recType)) {
            resp.setContentType("text/json");
            strBuff = ("{\"code\":\"" + dataContent + "\"}").getBytes("UTF-8");
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentLength(strBuff.length);
        resp.getOutputStream().write(strBuff);
    }
    /**
     * 构建XML格式信息
     * @return xml串
     */
    private String generalXML(List<JQueryAjaxPersonModel> objArray) {
        String rtnStr = xmlTmpl.replace("@C", String.valueOf(objArray.size()));
        StringBuilder sbud = new StringBuilder();
        for (JQueryAjaxPersonModel japm : objArray) {
            sbud.append(japm.generalXML());
        }
        return rtnStr.replace("@U", sbud.toString());
    }

    /**
     * 构建JSON格式信息
     * @return json串
     */
    private String generalJSON(List<JQueryAjaxPersonModel> objArray) {
        String rtnStr = jsonTmpl.replace("@C", String.valueOf(objArray.size()));
        StringBuilder sbud = new StringBuilder();
        for (JQueryAjaxPersonModel japm : objArray) {
            sbud.append(japm.generalJSON()).append(",");
        }
        return rtnStr.replace("@U", sbud.substring(0, sbud.length() - 1));
    }
    /**
     * 构建测试数据
     * @param start 起始下标
     * @param end 结束下标
     * @return 实体数据集
     */
    private List<JQueryAjaxPersonModel> buildTestData(int start, int end) {
        List<JQueryAjaxPersonModel> rtnList = new ArrayList<JQueryAjaxPersonModel>();
        int totalSize = objArray.size();
        if (start < end && (end - start) <= totalSize) {
            for (;start < end; start++) {
                rtnList.add(objArray.get(start));
            }
        }
        return rtnList;
    }
    /**
     * 添加
     * @param req 数据对象
     * @return rtnValue pkCode
     */
    private String doAddUser(HttpServletRequest req) {
        String rtnStr = (String.valueOf((int)Math.random() * 100));
        String userRole = req.getParameter("role");
        objArray.add(0, new JQueryAjaxPersonModel(rtnStr, req.getParameter("name"), req.getParameter("age"), userRole));
        return rtnStr;
    }
    /**
     * 初始化测试数据
     */
    private void initTestData() {
        for (int start = 1, end = 101;start <= end; start ++) {
            objArray.add(new JQueryAjaxPersonModel(String.valueOf(start), String.valueOf(start), String.valueOf(start), ROLEMGR[((int)(Math.random()*(ROLEMGR.length)))]));
        }
    }
}

/**
 * 人员实体
 * @author WangYanCheng
 * @version 2011-7-20
 */
class JQueryAjaxPersonModel {
    /** 编码 */
    private String code;
    /** 名称 */
    private String name;
    /** 年龄 */
    private String age;
    /** 角色 */
    private String role;
    /** xml格式 */
    private String xmlTmpl = "<user><code>@C</code><name>@N</name><age>@A</age><role>@R</role></user>";
    /** json格式 */
    private String jsonTmpl = "{\"code\":\"@C\",\"name\":\"@N\",\"age\":\"@A\",\"role\":\"@R\"}";

    /**
     * 人员实体
     * @param code 编码
     * @param name 名称
     * @param age 年龄
     * @param role 角色
     */
    public JQueryAjaxPersonModel(String code, String name, String age, String role) {
        this.code = code;
        this.name = name;
        this.age = age;
        this.role = role;
    }

    /**
     * 构建XML格式信息
     */
    public String generalXML() {
        return xmlTmpl.replaceAll("@C", this.code).replaceAll("@N", this.name).replaceAll("@A", this.age)
            .replaceAll("@R", this.role);
    }

    /**
     * 构建JSON格式信息
     * @return JSON格式
     */
    public String generalJSON() {
        return jsonTmpl.replaceAll("@C", this.code).replaceAll("@N", this.name).replaceAll("@A", this.age)
            .replaceAll("@R", this.role);
    }
}
