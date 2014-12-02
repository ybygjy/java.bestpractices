package org.ybygjy.jndi.ldap;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

/**
 * LDAP Schema操作
 * <p>
 *  <ol>
 *      <li>请使用Directory管理员角色</li>
 *      <li><strong>OpenLDAP: </strong>OpenLDAP does not support the modification of the RFC 2252 schema descriptions. Instead, you must add any new schema or updated schema descriptions statically to the server's schema files. See the OpenLDAP documentation for details. </li>
 *      <li><strong>Windows Active Directory: </strong>Active Directory does not support the modification of the RFC 2252 schema descriptions. Instead, you must update Active Directory's internal schema representation. See the Windows 2000 Planning Guide for details on how to enable and perform schema updates. </li>
 *  </ol>
 * </p>
 * @author WangYanCheng
 * @version 2011-5-13
 */
public class LDAPSchemaWrapper {
    /**目录服务*/
    private DirContext dirCtx;
    /**
     * 构造方法
     * @param dirCtx dirCtx
     */
    public LDAPSchemaWrapper(DirContext dirCtx) {
        this.dirCtx = dirCtx;
    }
    /**
     * 取与指定DN关联的Schema
     * @param dn 给定DN
     */
    public void getSchema(String dn) {
        DirContext tmpCtx = null;
        try {
            tmpCtx = this.dirCtx.getSchema(dn);
            NamingEnumeration<NameClassPair> nameResult = tmpCtx.list("");
            NameClassPair ncpTmp = null;
            while (nameResult.hasMore()) {
                ncpTmp = nameResult.next();
                System.out.println(ncpTmp.getName());
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    /**
     * 取给定DN下的，schema对象详细信息描述，如objectClass值为top，即对top的描述
     * <p>这里使用的是遍历Attributes方式，此方法要比LDAPSchemaWrapper#getSchemaClassDef4Lookup(String)效率高。</p>
     * @param dn 给定DN
     * @see LDAPSchemaWrapper#getSchemaClassDef4Lookup(String)
     */
    public void getSchemaClassDef(String dn) {
        DirContext tmpDir = null;
        try {
            tmpDir = dirCtx.getSchemaClassDefinition(dn);
            NamingEnumeration<NameClassPair> nameResult = tmpDir.list("");
            String tmpName = null;
            while (nameResult.hasMore()) {
                tmpName = nameResult.next().getName();
                Attributes attrs = tmpDir.getAttributes(tmpName);
                NamingEnumeration<Attribute> attrEnum = (NamingEnumeration<Attribute>) attrs.getAll();
                StringBuilder sbud = new StringBuilder();
                Attribute attr = null;
                while (attrEnum.hasMore()) {
                    attr = attrEnum.next();
                    sbud.append(attr.getID()).append("\t");
                    NamingEnumeration valueEnum = attr.getAll();
                    while (valueEnum.hasMore()) {
                        sbud.append(valueEnum.next()).append(",");
                    }
                    sbud.append("\n");
                }
                System.out.println(tmpName + "\n".concat(sbud.toString()));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (null != tmpDir) {
                try {
                    tmpDir.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 取给定DN的SchemaClass对象明细定义
     * <p><strong>注意：</strong>此方法的作用是查询并打印出给定DN(条目)所有使用到的objectclass，示例：
     * <pre>
     * dn:cn=WangYanCheng,ou=HR,dc=ybygjy,dc=com
     * objectclass:organizationalPerson
     * objectclass:inetOrgPerson
     * objectclass:person
     * objectclass:top
     * </pre>
     * 若以上条目作测试，输入内容如下
     * <table border="1">
     *  <tr>
     *      <th>类标记</th>
     *      <th>值</th>
     *  </tr>
     *  <tr>
     *      <td>NAME</td>
     *      <td>inetOrgPerson</td>
     *  </tr>
     *  <tr>
     *      <td>SUP</td>
     *      <td>organizationalPerson</td>
     *  </tr>
     *  <tr>
     *      <td>NAME</td>
     *      <td>organizationalPerson</td>
     *  </tr>
     *  <tr>
     *      <td>SUP</td>
     *      <td>person</td>
     *  </tr>
     * </table>
     * <tr>。。。。此表内容很多，就不一一列出。</tr>
     * </p>
     * @param dn 指定DN
     * @see LDAPSchemaWrapper#getSchemaClassDef(String)
     */
    public void getSchemaClassDef4Lookup(String dn) {
        DirContext tmpCtx = null;
        try {
            tmpCtx = dirCtx.getSchemaClassDefinition(dn);
            NamingEnumeration<NameClassPair> nameEnum = tmpCtx.list("");
            NameClassPair ncpInst = null;
            while (nameEnum.hasMore()) {
                ncpInst = nameEnum.next();
                DirContext tmpCtxx = (DirContext) tmpCtx.lookup(ncpInst.getName());
                Attributes attrs = tmpCtxx.getAttributes("");
                NamingEnumeration<Attribute> attrEnum = (NamingEnumeration<Attribute>) attrs.getAll();
                Attribute attr = null;
                StringBuilder sbud = new StringBuilder();
                while (attrEnum.hasMore()) {
                    attr = attrEnum.next();
                    sbud.append(attr.getID()).append("\t");
                    NamingEnumeration valueEnum = attr.getAll();
                    while (valueEnum.hasMore()) {
                        sbud.append(valueEnum.next()).append(",");
                    }
                    sbud.append("\n");
                }
                System.out.println(sbud.toString());
                tmpCtxx.close();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (tmpCtx != null) {
                try {
                    tmpCtx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 取给定DN下的所有ObjectClass对象定义
     * @param dn 指定DN
     */
    public void getSchemaClassDef4Search(String dn) {
        DirContext tmpCtx = null;
        try {
            tmpCtx = dirCtx.getSchemaClassDefinition(dn);
            NamingEnumeration<SearchResult> resultEnum = tmpCtx.search("", null);
            SearchResult tmpResult = null;
            Attributes attrs = null;
            StringBuilder sbud = new StringBuilder();
            while (resultEnum.hasMore()) {
                tmpResult = resultEnum.next();
                attrs = tmpResult.getAttributes();
                NamingEnumeration<Attribute> attrEnum = (NamingEnumeration<Attribute>) attrs.getAll();
                Attribute attr = null;
                while (attrEnum.hasMore()) {
                    attr = attrEnum.next();
                    sbud.append(attr.getID()).append("\t");
                    NamingEnumeration valueEnum = attr.getAll();
                    while (valueEnum.hasMore()) {
                        sbud.append(valueEnum.next()).append(",");
                    }
                    sbud.append("\n");
                }
                sbud.append("\n");
            }
            System.out.println(dn + "\n" + sbud.toString());
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (null != tmpCtx) {
                try {
                    tmpCtx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 查找给定DN下的指定objectClass结构定义
     * @param dn 给定DN
     * @param objectClass 给定objectClass
     */
    public void lookUp4ClassDef(String dn, String objectClass) {
        String regStr = "ClassDefinition/@OBJ@";
        DirContext rtnCtx = null, schemaCtx = null;
        try {
            schemaCtx = dirCtx.getSchema(dn);
            rtnCtx = (DirContext) schemaCtx.lookup(regStr.replaceAll("@OBJ@", objectClass));
            Attributes attrs = rtnCtx.getAttributes("");
            NamingEnumeration<Attribute> attrEnum = (NamingEnumeration<Attribute>) attrs.getAll();
            Attribute attr = null;
            while (attrEnum.hasMore()) {
                attr = attrEnum.next();
                NamingEnumeration<String> nameEnum = (NamingEnumeration<String>) attr.getAll();
                StringBuilder sbud = new StringBuilder();
                while (nameEnum.hasMore()) {
                    sbud.append(nameEnum.next()).append(",");
                }
                System.out.println(attr.getID() + "\t" + sbud.toString());
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (null == schemaCtx) {
                try {
                    schemaCtx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
            if (null == rtnCtx) {
                try {
                    rtnCtx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 给指定DN添加objectclass
     * <p>
     *  <strong>注意：</strong>
     *  1、请检查目录服务器相应版本，以确认对目录规范<a href="http://www.ietf.org/rfc/rfc2252.txt">RFC 2252</a>的支持
     * </p>
     * @param dn 指定DN
     * @param className objectClass结构名称
     * @param attrs objectClass结构数据
     * @return rtnFlag {true:成功添加;false:未成功}
     */
    public boolean addNewObjectClass(String dn, String className, Attributes attrs) {
        DirContext tmpCtx = null;
        boolean rtnFlag = false;
        try {
            tmpCtx = dirCtx.getSchema(dn);
            DirContext schemaRef = tmpCtx.createSubcontext("ClassDefinition/".concat(className), attrs);
            if (null != schemaRef) {
                rtnFlag = true;
                schemaRef.close();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (null != tmpCtx) {
                try {
                    tmpCtx.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnFlag;
    }
    /**
     * 删除给定DN下的相应objectClass
     * @param dn 给定DN
     * @param className 要删除的objectClass名称
     * @return true/false
     * @see LDAPSchemaWrapper#addNewObjectClass(String, String, Attributes)
     */
    public boolean removeObjectClass(String dn, String className) {
        boolean rtnFlag = false;
        DirContext dirCtx = null;
        try {
            dirCtx = this.dirCtx.getSchema(dn);
            dirCtx.destroySubcontext("ClassDefinition/".concat(className));
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (null != dirCtx) {
                try {
                    dirCtx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnFlag;
    }
    /**
     * 修改给定DN下的objectClass
     * 
     * @param dn 给定DN
     * @param objectClassName 要修改的objectclass名称
     * @param opFlag 操作标记(ADD、REMOVE、REPLACE、etc..)
     * @param attrs 结构信息
     * @return true/false
     */
    public boolean modifyObjectClass(String dn, String objectClassName, int opFlag, Attributes attrs) {
        boolean rtnFlag = false;
        DirContext tmpCtx = null;
        try {
            tmpCtx = dirCtx.getSchema(dn);
            tmpCtx.modifyAttributes("ClassDefinition/".concat(objectClassName), opFlag, attrs);
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (null != tmpCtx) {
                try {
                    tmpCtx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnFlag;
    }
    /**
     * 查询给定DN下的Attribute结构定义
     * @param dn 指定DN
     * @param attrName 要查询的Attribute结构名称
     */
    public void getSchemaAttributeDef4Lookup(String dn, String attrName) {
        DirContext tmpCtx = null;
        try {
            tmpCtx = dirCtx.getSchema(dn);
            DirContext tmpCtxx = (DirContext) tmpCtx.lookup("AttributeDefinition/".concat(attrName));
            Attributes attrs = tmpCtxx.getAttributes("");
            NamingEnumeration<Attribute> nameEnum = (NamingEnumeration<Attribute>) attrs.getAll();
            Attribute attr = null;
            StringBuilder sbud = new StringBuilder();
            while (nameEnum.hasMore()) {
                attr = nameEnum.next();
                sbud.append(attr.getID()).append("\t");
                NamingEnumeration valueEnum = attr.getAll();
                while (valueEnum.hasMore()) {
                    sbud.append(valueEnum.next()).append(",");
                }
                sbud.append("\n");
            }
            System.out.println(sbud.toString());
            tmpCtxx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (null != tmpCtx) {
                try {
                    tmpCtx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 取Attribute Schema
     * <p><strong>操作流程：</strong>
     * 定位DN条目==>取条目属性集==>遍历属性集/取属性==>取属性结构信息==>取结构信息集(结构的属性集)==>遍历属性集/取属性==>遍历取属性值
     * </p>
     * @param dn 给定DN
     */
    public void getSchemaAttributeDef(String dn) {
        DirContext tmpDir = null;
        try {
            tmpDir = (DirContext) dirCtx.lookup(dn);
            Attributes attrs = tmpDir.getAttributes("");
            NamingEnumeration<Attribute> attrEnum = (NamingEnumeration<Attribute>) attrs.getAll();
            Attribute attr = null;
            StringBuilder sbud = new StringBuilder();
            while (attrEnum.hasMore()) {
                attr = attrEnum.next();
                sbud.append(attr.getID()).append("\n");
                DirContext tmpDirr = attr.getAttributeDefinition();
//                attrs = tmpDirr.getAttributes("", new String[]{"NAME", "NUMERICOID"});
                attrs = tmpDirr.getAttributes("");
                NamingEnumeration<Attribute> attrEnumm = (NamingEnumeration<Attribute>) attrs.getAll();
                while (attrEnumm.hasMore()) {
                    attr = attrEnumm.next();
                    //这里为表达意思明确，增加了获取值的内层循环完成可以使用，其实可以sbud.append(attr).append("\n");
                    NamingEnumeration<Attribute> attrValueEnum = (NamingEnumeration<Attribute>) attr.getAll();
                    sbud.append(attr.getID()).append("\t");
                    while (attrValueEnum.hasMore()) {
                        sbud.append(attrValueEnum.next()).append(",");
                    }
                    sbud.append("\n");
                }
                sbud.append("\n");
                tmpDirr.close();
            }
            System.out.println(sbud.toString());
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (tmpDir != null) {
                try {
                    tmpDir.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 删除给定DN下的属性结构
     * <p><strong>注意：</strong>
     * 1、请确保LDAP服务器支持对Schema结构的删除，OpenLDAP 2.4 是可以支持的但2.3不可以
     * 2、请参考LDAP服务器具体说明文档
     * </p>
     * @param dn 指定DN
     * @param attrName 属性结构名称
     */
    public void removeAttributeSchema(String dn, String attrName) {
        DirContext tmpCtx = null;
        try {
            tmpCtx = dirCtx.getSchema(dn);
            tmpCtx.destroySubcontext("AttributeDefinition/".concat(attrName));
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (null != tmpCtx) {
                try {
                    tmpCtx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 查询syntaxDefinition定义
     * @return true/false
     */
    public boolean getSchemaSyntaxDef(String syntaxId) {
        DirContext tmpDir = null;
        boolean rtnFlag = false;
        try {
            tmpDir = dirCtx.getSchema("");
            DirContext tmpDirr = (DirContext) tmpDir.lookup("SyntaxDefinition/".concat(syntaxId));
            Attributes attrs = tmpDirr.getAttributes("");
            NamingEnumeration<Attribute> attrEnum = (NamingEnumeration<Attribute>) attrs.getAll();
            Attribute attr = null;
            StringBuilder sbud = new StringBuilder();
            while (attrEnum.hasMore()) {
                attr = attrEnum.next();
                sbud.append(attr.getID()).append("\t");
                NamingEnumeration valueEnum = attr.getAll();
                while (valueEnum.hasMore()) {
                    sbud.append(valueEnum.next()).append(",");
                }
                sbud.append("\n");
            }
            System.out.println(sbud.toString());
            tmpDirr.close();
            rtnFlag = true;
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (null != tmpDir) {
                try {
                    tmpDir.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnFlag;
    }
    /**
     * 取得给定DN下的所有属性用到的语法
     * @param dn
     * @return
     */
    public boolean getSchemaSyntaxDefByDN(String dn) {
        return false;
    }
    /**
     * 创建测试用objectClass
     * @return rtnAttrs objectClass结构数据
     */
    public Attributes createTestObjectClass() {
        Attributes rtnAttrs = new BasicAttributes(true);
        rtnAttrs.put("NUMERICOID", "1.3.6.1.4.1.4754.2.99.1");
        rtnAttrs.put("NAME", "daowooUser");
        rtnAttrs.put("DESC", "helloWorld");
        rtnAttrs.put("SUP", "top");
        rtnAttrs.put("STRUCTURAL", "true");
        Attribute mustAttr = new BasicAttribute("MUST", "cn");
        mustAttr.add("objectclass");
        rtnAttrs.put(mustAttr);
        Attribute mayAttr = new BasicAttribute("MAY", "uid");
        rtnAttrs.put(mayAttr);
        return rtnAttrs;
    }
    /**
     * 创建测试修改objectClass
     * @return rtnAttrs objectClass结构数据
     */
    public Attributes createModifyObjectClass() {
        Attributes rtnAttrs = new BasicAttributes(false);
        Attribute attr = new BasicAttribute("MAY", "description");
        rtnAttrs.put(attr);
        return rtnAttrs;
    }
}
