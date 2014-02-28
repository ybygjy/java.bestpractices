package org.ybygjy.web.json;

import java.util.HashMap;
import java.util.Map;

import org.ybygjy.web.json.jsoninjava.JSONObject;

/**
 * Json ²âÊÔ
 * @author WangYanCheng
 * @version 2009-12-26
 */
public class JsonTest {
    /**
     * Èë¿Ú
     * @param args args
     */
    public static void main(String[] args) {
        Map<String, String> mapStr = new HashMap<String, String>();
        for (int index = 0; index < 10; index++) {
            mapStr.put(String.valueOf(index), String.valueOf(index));
        }
        JSONObject jsoInst = new JSONObject(mapStr);
        System.out.println(jsoInst);
    }
}
