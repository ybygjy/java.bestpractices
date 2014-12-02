package org.ybygjy.web.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现ThreadLocal功能扩展
 * @author WangYanCheng
 * @version 2011-4-23
 */
public final class ThreadLocalMap {
    private static final ThreadLocal<Map<String, Object>> threadLocal = new InnerThreadLocal();
    /**
     * 工具类不允许实例化
     */
    private ThreadLocalMap(){}
    public static Map<String, Object> getContext() {
        return (Map<String, Object>) threadLocal.get();
    }
    public static void put(String key, Object value) {
        getContext().put(key, value);
    }
    public static Object get(String key) {
        return getContext().get(key);
    }
    public static Object remove(String key) {
        return getContext().remove(key);
    }
    public static void clear() {
        getContext().clear();
    }
    private static class InnerThreadLocal extends ThreadLocal<Map<String, Object>> {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>() {
                /**
                 * serialSeq
                 */
                private static final long serialVersionUID = 8112923165659709210L;

                @Override
                public Object put(String key, Object value) {
                    if (this.containsKey(key)) {
                        System.out.println("Overwritten attribute to thread context: ".concat(key).concat("=").concat(value == null ? "null" : value.toString()));
                    } else {
                        System.out.println("Added attribute to thread context: ".concat(key).concat("=").concat(value == null ? "null" : value.toString()));
                    }
                    return super.put(key, value);
                }
            };
        }
    }
}
