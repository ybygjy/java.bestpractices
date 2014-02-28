package org.ybygjy.jndi.ldap;

import java.io.UnsupportedEncodingException;

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

import org.ybygjy.basic.security.SecurityUtils;

/**
 * 封装公共操作
 * @author WangYanCheng
 * @version 2011-6-9
 */
public class LDAPUtils {
    /**
     * 取属性值,多个以逗号分割，如userPhone=phone1,phone2,phone3
     * @param attr 条目属性对象
     * @return 条目值
     */
    public static String getAttributeValue(Attribute attr) {
        StringBuffer sbud = new StringBuffer();
        try {
            NamingEnumeration<?> nameEnum = attr.getAll();
            Object obj = null;
            while (nameEnum.hasMore()) {
                obj = nameEnum.next();
                // 注意 obj类型可以是字节等其它类型
                if (obj instanceof String) {
                    sbud.append(obj.toString()).append(",");
                } else if (obj instanceof byte[]) {
                    sbud.append(new String((byte[]) obj)).append(",");
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        if (sbud.length() > 0) {
            sbud.setLength(sbud.length() - 1);
        }
        return sbud.length() == 0 ? null : sbud.toString();
    }

    /**
     * 从条目集中取条目值，多值以逗号分割
     * @param attrs 属性条目集
     * @param attrID 条目ID
     * @return 条目值
     */
    public static String getAttributeValue(Attributes attrs, String attrID) {
        Attribute attr = attrs.get(attrID);
        return attr != null ? (getAttributeValue(attr)) : null;
    }

    /**
     * 构造适用于OpenLDAP的条目密码
     * @param srcPassStr 明文
     * @return cipherStr 密码串
     */
    public static String getOpenLDAPEntryPassword(String srcPassStr) {
        String rtnStr = SecurityUtils.encodeBase64(SecurityUtils.encodeMD5(srcPassStr));
        return "{MD5}".concat(rtnStr);
    }

    /**
     * 工具方法，负责打印检索值集
     * @param resultEnum 结果集
     * @param tmpDN 指定DN
     * @param filterExp 过滤表达式
     */
    public static void printResultEnum(NamingEnumeration<SearchResult> resultEnum, String tmpDN,
                                       String filterExp) {
        try {
            if (resultEnum.hasMore()) {
                printAttr((NamingEnumeration<Attribute>) ((resultEnum.next().getAttributes()).getAll()));
                while (resultEnum.hasMore()) {
                    printAttr((NamingEnumeration<Attribute>) ((resultEnum.next().getAttributes()).getAll()));
                }
            } else {
                System.out.println("未能找到匹配：\n\tDN：".concat(tmpDN).concat("\n\tfilterExp：")
                    .concat(filterExp));
            }
        } catch (SizeLimitExceededException see) {
            System.err.println("超出指定limit范围".concat(see.getMessage()));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 工具方法,负责打印集合中的元素
     * @param resultEnum nameEnum
     */
    public static void printEnum(NamingEnumeration<?> resultEnum) {
        while (resultEnum.hasMoreElements()) {
            NameClassPair ncpInst = (NameClassPair) resultEnum.nextElement();
            System.out.println("\t" + ncpInst.getNameInNamespace());
        }
    }

    /**
     * 工具方法,负责打印属性集合元素
     * <p>
     * <strong>注意：</strong>
     * </p>
     * @param nameArray nameEnum
     */
    public static void printAttr(NamingEnumeration<Attribute> nameArray) {
        Attribute attr = null;
        try {
            while (nameArray.hasMore()) {
                attr = nameArray.next();
                StringBuilder sbud = new StringBuilder();
                sbud.append(attr.getID()).append("=");
                for (NamingEnumeration<?> ne = attr.getAll(); ne.hasMore();) {
                    Object obj = ne.next();
                    sbud.append(obj.toString()).append("[").append(obj.getClass().getSimpleName()).append(
                        "]");
                }
                System.out.println(sbud.toString());
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成适用于OpenLDAPServer存储的MD5密码字符串
     * @param srcPasswdStr 明文
     * @return rtnStr 处理完成的密码串
     */
    public static String getOpenLDAPMD5(String srcPasswdStr) {
        byte[] md5Bytes = SecurityUtils.encodeMD5(srcPasswdStr);
        String rtnStr = SecurityUtils.encodeBase64(md5Bytes);
        rtnStr = "{MD5}".concat(rtnStr);
        return rtnStr;
    }

    /**
     * 将给定源字符串转换为适用于OpenLDAPMD5格式存储的字符串
     * <p>
     * <strong>此方法应用在LDIF文件内容，负责生成MD5加密的字符串用以存储在条目相关属性(如userPassword)中。</strong>
     * </p>
     * <p>
     * OpenLDAPMD5格式基本原理：
     * <ol>
     * <li>OpenLDAP以MD5加密方式存储的密码串是经过Base64编码之后的字符串，而不是单纯的MD5加密后的16进制格式的字符串。</li>
     * <li>转换方式主要有两步：
     * <p>
     * 第一步将源字符串进行MD5转换
     * </p>
     * <p>
     * 第二步Base64对进行MD5转换得到的字节组进行编码
     * </p>
     * <p>
     * 第三步通过JNDI(或其它LDAP Client)将处理完成的字符串存储到特定Entry的Attribute中
     * </p>
     * </li>
     * </ol>
     * </p>
     * @param srcStr 源字符串(密码串)
     * @return rtnStr 转换完成的字符串
     */
    public static String getOpenLDAPMD5LDIF(String srcStr) {
        String rtnStr = getOpenLDAPMD5(srcStr);
        try {
            rtnStr = SecurityUtils.encodeBase64(rtnStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rtnStr;
    }
    /**
     * Finding out of the list of controls that an LDAP server supports.
     * @param ctx DirContext
     */
    public static void showSupportControls(DirContext ctx) {
        Attributes attrs = null;
        try {
            attrs = ctx.getAttributes("", new String[]{"supportedcontrol"});
        } catch (NamingException e) {
            e.printStackTrace();
        }
        if (attrs != null) {
            printAttr((NamingEnumeration<Attribute>) attrs.getAll());
        }
    }
}
