package org.ybygjy.jndi.ldap;

import java.util.Iterator;
import java.util.Map;

import javax.naming.Binding;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * 封装JNDI LDAP实现
 * @author WangYanCheng
 * @version 2010-12-31
 */
public class ContextMgr4LDAP {
    /** Context */
    private DirContext ctx;
    /**
     * InnerCompiler
     */
    static class InnerClass {
        /** Singleton Pattern */
        public static ContextMgr4LDAP cm4LDAP = new ContextMgr4LDAP();
    }

    /**
     * 单例
     * Constructor
     */
    private ContextMgr4LDAP() {
        this.ctx = Constant.createCtx();
    }

    /**
     * 修改给定DN条目的密码
     * <p><strong>注意：条目必须支持userPassword属性</strong></p>
     * @param dn 给定DN
     * @param passwd 密码
     * @param type 密码存储方式{MD5,SHA,etc}
     * @return rtnFlag {true：成功；false：失败}
     */
    public boolean modifyPassword(String dn, String passwd, int type) {
        Attributes attrs = new BasicAttributes();
        attrs.put("userPassword", LDAPUtils.getOpenLDAPMD5(passwd));
        boolean rtnFlag = false;
        try {
            this.ctx.modifyAttributes(dn, DirContext.REPLACE_ATTRIBUTE, attrs);
            rtnFlag = true;
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return rtnFlag;
    }

    /**
     * doRenaming
     * @param oldName oldName
     * @param newName newName
     * @return true/false
     */
    public boolean doRenaming(String oldName, String newName) {
        boolean rtnBool = false;
        try {
            ctx.rename(oldName, newName);
            rtnBool = true;
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return rtnBool;
    }

    /**
     * doLookup
     * @param name name
     * @return rtnObj/null
     */
    public Object lookup(String name) {
        Object rtnObj = null;
        try {
            rtnObj = ctx.lookup(name);
        } catch (NamingException e) {
            System.err.println("Exception ContextMgr4LDAP#lookup\t".concat(e.getMessage()));
        }
        return rtnObj;
    }
    /**
     * 在指定dn下查询包含指定attribute的对象
     * @param dn 指定dn
     * @param param 属性集合
     * @return resultEnum {@link SearchResult}
     */
    public NamingEnumeration<SearchResult> search(String dn, Map<String, Object> param) {
        Attributes matchAttrs = new BasicAttributes(true);
        if (param != null) {
            Map.Entry<String, Object> tmpEntry = null;
            for (Iterator<Map.Entry<String, Object>> iterator = param.entrySet().iterator(); iterator.hasNext();) {
                tmpEntry = iterator.next();
                matchAttrs.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        NamingEnumeration<SearchResult> resultEnum = null;
        try {
            resultEnum = this.ctx.search(dn, matchAttrs);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return resultEnum;
    }

    /**
     * 在指定DN下查询符合过滤表达式过滤的对象
     * @param dn 指定DN
     * @param filterExp 过滤表达式
     * @return resultEnum {@link SearchResult}
     */
    public NamingEnumeration<SearchResult> search(String dn, String filterExp) {
        return search(dn, filterExp, null, SearchControls.SUBTREE_SCOPE);
    }

    /**
     * 查询指定DN，且经过滤表达式过滤的对象，
     * @param dn 指定DN
     * @param filterExp 过滤表达式
     * @param filterAttr 指定提取的对象属性
     * @param searchScope 查询范围
     * @return resultEnum {@link SearchResult}
     */
    public NamingEnumeration<SearchResult> search(String dn, String filterExp, String[] filterAttr, int searchScope) {
        SearchControls sctls = new SearchControls();
        if (filterAttr != null) {
            sctls.setReturningAttributes(filterAttr);
        }
        sctls.setSearchScope(searchScope);
        return search(dn, filterExp, sctls);
    }

    /**
     * 查询指定DN，且经过滤表达式过滤的对象
     * @param dn 指定DN
     * @param filterExp 过滤表达式
     * @param searchControl 配置对象
     * @return resultEnum {@link SearchResult}
     */
    public NamingEnumeration<SearchResult> search(String dn, String filterExp, SearchControls searchControl) {
        NamingEnumeration<SearchResult> resultEnum = null;
        try {
            resultEnum = this.ctx.search(dn, filterExp, searchControl);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return resultEnum;
    }
    /**
     * 取得指定对象的所有属性
     * @param name 对象地址
     * @return 属性集
     */
    public Attributes getAttributes(String name) {
        Attributes rtnAttr = null;
        try {
            rtnAttr = this.ctx.getAttributes(name);
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        return rtnAttr;
    }

    /**
     * 取得指定目录的下的对象
     * <p><strong>注意：</strong>对ctx.list()参数在subCtx情况下可指定""，但在baseCtx情况下不可以</p>
     * @param name 目录
     * @return nameEnum 目录对象集合
     * @throws NamingException NamingException
     */
    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        NamingEnumeration<NameClassPair> nameEnum = null;
        try {
            nameEnum = ctx.list(name);
        } catch (NameNotFoundException e) {
            System.err.println(e.getMessage().concat("\t").concat(name));
        }
        return nameEnum;
    }

    /**
     * 取当前上下文环境的目录下的对象
     * <p>可理解为找全局对象的适用自己的那部分</p>
     * <p><strong>注意：</strong>该方法会比较耗时</p>
     * @param name 目录
     * @return 目录对象集合
     */
    public NamingEnumeration<Binding> listBinding(String name) {
        NamingEnumeration<Binding> nameEnum = null;
        try {
            nameEnum = ctx.listBindings(name);
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        return nameEnum;
    }

    /**
     * 绑定对象
     * @param key key
     * @param obj value
     * @return true/false
     */
    public boolean bind(String key, Object obj) {
        boolean rtnBool = false;
        if (null == this.ctx) {
            return rtnBool;
        }
        try {
            this.ctx.rebind(key, obj);
            rtnBool = true;
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return rtnBool;
    }

    /**
     * doUnBind
     * @param name name
     * @return true/false
     */
    public boolean unBind(String name) {
        boolean rtnBol = false;
        try {
            ctx.unbind(name);
            rtnBol = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rtnBol;
    }
    /**
     * 删除指定DN
     * @param dn 指定DN
     * @throws NamingException NamingException
     */
    public void destroyCtx(String dn) throws NamingException {
        //查dn下的子dn
        NamingEnumeration<NameClassPair> resultList = list(dn);
        if (resultList == null) {
            return;
        }
        NameClassPair ncpInst = null;
        String tmpName = null;
        while (resultList.hasMore()) {
            ncpInst = resultList.next();
            tmpName = ncpInst.getNameInNamespace();
            destroyCtx(tmpName);
            this.ctx.destroySubcontext(tmpName);
        }
        this.ctx.destroySubcontext(dn);
    }

    /**
     * 删除DN，递归查询删除
     * <p><strong>注意：</strong>支持删除叶子节点</p>
     * @param dn 指定DN
     * @throws NamingException NamingException
     */
    public void deleteDN(String dn) throws NamingException {
        NamingEnumeration<SearchResult> nameEnum = ctx.search(dn, "(objectclass=*)", new SearchControls());
        SearchResult searchResult = null;
        while (nameEnum.hasMore()) {
            searchResult = nameEnum.next();
            deleteDN(searchResult.getNameInNamespace());
        }
        System.out.println(dn);
        ctx.destroySubcontext(dn);
    }

    /**
     * 取得服务实例
     * @return instance
     */
    public static ContextMgr4LDAP getInstance() {
        return InnerClass.cm4LDAP;
    }
    /**
     * 取当前目录上下文实例
     * @return ctx {@link DirContext}
     */
    public DirContext getCtx() {
        return this.ctx;
    }
}
