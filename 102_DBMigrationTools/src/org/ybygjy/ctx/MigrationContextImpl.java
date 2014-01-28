package org.ybygjy.ctx;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ybygjy.MigrationContext;

/**
 * 上下文默认实现<strong>非线程安全</strong>
 * @author WangYanCheng
 * @version 2012-10-17
 */
public class MigrationContextImpl implements MigrationContext {
    /**上下文属性集*/
    private static Map<String, Object> attributes = new HashMap<String, Object>();
    @Override
    public Object getAttribute(String attrName) {
        return attributes.get(attrName);
    }

    @Override
    public void setAttribute(String attrName, Object attrValue) {
        attributes.put(attrName, attrValue);
    }

    @Override
    public void appendSortedAttr(String attrName, String attrValue) {
        Object tmpObj = MigrationContextFactory.getInstance().getCtx().getAttribute(attrName);
        SortedSet<String> tmpList = tmpObj == null ? new TreeSet<String>() : (SortedSet<String>) tmpObj;
        tmpList.add(attrValue);
        MigrationContextFactory.getInstance().getCtx().setAttribute(attrName, tmpList);
    }
}
