package org.ybygjy.jndi.ldap;

import java.util.Iterator;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

/**
 * Attribute操作LDAP实现
 * @author WangYanCheng
 * @version 2011-4-29
 */
public class AttributeMgr4LDAP {
    /** 服务对象 */
    private DirContext dirCtx;

    /**
     * 构造函数
     * @param dirCtx 目录服务对象
     */
    public AttributeMgr4LDAP(DirContext dirCtx) {
        this.dirCtx = dirCtx;
    }
    /**
     * 批量修改给定DN的Attribute
     * <p>在replace操作时会产生多对一的情况，详细见{@linkplain AttributeMgr4LDAPTest#testModifyAttr()}</p>
     * <p><strong>注意：</strong>attribute type undefined问题</p>
     * @param dn 指定DN
     * @param flag 操作标记
     * @param attrMap 属性集
     * @see DirContext.REPLACE_ATTRIBUTE、DirContext.ADD_ATTRIBUTE、DirContext.REMOVE_ATTRIBUTE
     */
    public void modifyAttr(String dn, int flag, Map<String, Object> attrMap) {
        ModificationItem[] modifyArray = new ModificationItem[attrMap.size()];
        int i = 0;
        for (Iterator<Map.Entry<String, Object>> iterator = attrMap.entrySet().iterator(); iterator
            .hasNext();) {
            Map.Entry<String, Object> entry = iterator.next();
            modifyArray[i++] = new ModificationItem(flag, new BasicAttribute(entry.getKey(), entry
                .getValue()));
        }
        modifyAttr(dn, modifyArray);
    }
    /**
     * 批量修改给定DN的Attribute
     * @param dn 指定DN
     * @param flag 操作标记
     * @param attributes 属性集
     */
    public void modifyAttr(String dn, int flag, Attributes attributes) {
        try {
            dirCtx.modifyAttributes(dn, flag, attributes);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    /**
     * 修改指定DN的attribute
     * @param dn 指定DN
     * @param key attribute名称
     * @param value 值
     */
    public void updateAttr(String dn, String key, Object value) {
        ModificationItem[] mods = new ModificationItem[1];
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(key, value));
        modifyAttr(dn, mods);
    }

    /**
     * 给指定dn添加Attribute
     * @param dn 指定dn
     * @param key attributeId
     * @param value attributeValue
     */
    public void addAttr(String dn, String key, Object value) {
        ModificationItem[] mods = new ModificationItem[] {new ModificationItem(DirContext.ADD_ATTRIBUTE,
            new BasicAttribute(key, value))};
        modifyAttr(dn, mods);
    }

    /**
     * 删除指定dn的attribute
     * @param dn 指定dn
     * @param name attribute ID
     * @param value attribute Value可处理name多个的问题，如：
     * <ul>
     *  <li>dn:cn=userName,dc=daowoo,dc=com</li>
     *  <li>sn:Kevin</li>
     *  <li>sn:John</li>
     * </ul>
     */
    public void removeAttr(String dn, String key, String value) {
        BasicAttribute tmpAttr = new BasicAttribute(key);
        if (value != null) {
            tmpAttr.add(value);
        }
        ModificationItem[] mods = new ModificationItem[] {new ModificationItem(DirContext.REMOVE_ATTRIBUTE, tmpAttr)};
        modifyAttr(dn, mods);
    }

    /**
     * 查询指定DN的Attribute，支持过滤 <strong>示例：</strong>
     * <p>
     * Attributes attrArray = new AttributeMgr4LDAP(dirCtx).qryAttr("o=hr,dc=daowoo,dc=com", new String[]{"id","name"});
     * </p>
     * <p>
     * <strong>注意：</strong>对属性值的更改存在中文问题
     * </p>
     * @param dn 指定DN
     * @param filters 过滤器
     */
    public Attributes qryAttr(String dn, String[] filters) {
        Attributes attrList = null;
        try {
            if (null == filters) {
                attrList = dirCtx.getAttributes(dn);
            } else {
                attrList = dirCtx.getAttributes(dn, filters == null ? new String[0] : filters);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return attrList;
    }

    /**
     * 封装Attribute修改{CRUD}
     * @param dn 指定dn
     * @param mods 修改实例
     */
    private void modifyAttr(String dn, ModificationItem[] mods) {
        try {
            dirCtx.modifyAttributes(dn, mods);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
