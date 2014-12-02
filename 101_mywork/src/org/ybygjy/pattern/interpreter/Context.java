package org.ybygjy.pattern.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * 环境类
 * @author WangYanCheng
 * @version 2013-2-18
 */
public class Context {
    private Map map = new HashMap();
    public void assign(Variable var, boolean value) {
        map.put(var, new Boolean(value));
    }
    public boolean lookup(Variable var) throws IllegalArgumentException {
        Boolean value = (Boolean) map.get(var);
        if (null == value) {
            throw new IllegalArgumentException();
        }
        return value.booleanValue();
    }
}
