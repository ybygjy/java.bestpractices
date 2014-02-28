package org.ybygjy.web.json.lib;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * JsonLibTest
 * @author WangYanCheng
 * @version 2009-12-26
 */
public class JsonLibTest {
    /**
     * jsonMap
     * @param mapInst mapInst
     * @return jsonData
     */
    public String jsonMap(Map<String, String> mapInst) {
        String jsonData = "";
        JSONObject jsoInst = JSONObject.fromObject(mapInst);
        jsonData = jsoInst.toString();
        return jsonData;
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Map<String, String> srcMap = new HashMap<String, String>();
        srcMap.put("a", "");
        srcMap.put("b", "\"");
        srcMap.put("c", "!~@!#%&^)&()");
        srcMap.put("d", "''~“'");
        JsonLibTest jltInst = new JsonLibTest();
        doPrint(jltInst.jsonMap(srcMap));
    }
    /**
     * doPrint
     * @param obj srcObj
     */
    public static void doPrint(Object obj) {
        System.out.println(obj);
    }
}
