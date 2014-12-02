package org.ybygjy.basic.annotation.brfw;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ybygjy.basic.annotation.brfw.anno.Exportable;
import org.ybygjy.basic.annotation.brfw.anno.Persistent;
import org.ybygjy.basic.annotation.brfw.entity.Address;
import org.ybygjy.basic.annotation.brfw.entity.FriendList;

/**
 * Export2Xml
 * @author WangYanCheng
 * @version 2009-12-16
 */
public class Export2Xml {
    /**
     * doExportObj
     * @param obj obj
     * @return tmplXml
     */
    public String doExportObj(Object obj) {
        if (obj instanceof List) {
            List tmpList = (List) obj;
            for (Iterator iterator = tmpList.iterator(); iterator.hasNext();) {
                Object objInst = iterator.next();
                if (objInst instanceof Address) {
                    doExportObj(objInst);
                }
            }
        }
        Exportable expAnnoInst = obj.getClass().getAnnotation(Exportable.class);
        String tmplStr = "";
        if (expAnnoInst != null) {
            String instName = expAnnoInst.name();
            String instDesc = expAnnoInst.description();
            String value = expAnnoInst.value();
            tmplStr = tmpl4Class
                    .replaceAll("@CLASS_NAME@", "".equals(value) ? instName : value)
                    .replaceAll("@CLASS_DESC@", instDesc);
            String tmplXml = doExportField(obj.getClass().getDeclaredFields(), obj);
            tmplStr = tmplStr.replaceAll("@FIELD@", "".equals(tmplXml)
                    ? "" : "<FIELDS>".concat(tmplXml).concat("</FIELDS>"));
            tmplXml = doExportMethod(obj.getClass().getDeclaredMethods(), obj);
            tmplStr = tmplStr.replaceAll("@METHOD@", "".equals(tmplXml)
                    ? "" : "<METHODS>@METHOD@</METHODS>".replaceAll("@METHOD@", tmplXml));
            System.out.println(tmplStr);
        }
        return tmplStr;
    }
    /**
     * doExportField
     * @param fieldArray 字段集
     * @param targetObj targetObj
     * @return fieldXml
     */
    public String doExportField(Field[] fieldArray, Object targetObj) {
        StringBuilder sbuInst = new StringBuilder();
        for (Field tmplField : fieldArray) {
            tmplField.setAccessible(true);
            Persistent perInst = tmplField.getAnnotation(Persistent.class);
            if (null != perInst) {
                Class typeObj = tmplField.getType();
                String name = tmplField.getName();
                String value = getFieldValue(tmplField, typeObj, targetObj);
                sbuInst.append(tmpl4Field.replaceAll("@FIELD_NAME@", name)
                        .replaceAll("@FIELD_TYPE@", typeObj.getSimpleName())
                        .replaceAll("@FIELD_VALUE@", tmpl4FieldV.replaceAll("@FIELD_VALUE@", value))
                        );
            }
        }
        return sbuInst.toString();
    }
    /**
     * doExportMethod
     * @param methodArray methodArray
     * @param targetObj targetObj
     * @return methodXml
     */
    public String doExportMethod(Method[] methodArray, Object targetObj) {
        StringBuilder sbud = new StringBuilder();
        for (Method methodInst : methodArray) {
            methodInst.setAccessible(true);
            if (methodInst.isAnnotationPresent(Persistent.class)) {
                String methodName = methodInst.getName();
                Class rtnClassType = methodInst.getReturnType();
                Class[] paramArray = methodInst.getParameterTypes();
                String tmpArgu = doExportArgument(paramArray);
                sbud.append(tmpl4Method.replaceAll("@NAME@", methodName)
                        .replaceAll("@RTN_TYPE@", rtnClassType.getSimpleName())
                        .replaceAll("@METHOD_ARG@", "".equals(tmpArgu) ? ""
                                : "<ARGUMENTS>@CHILDEN@</ARGUMENTS>".replaceAll("@CHILDEN@", tmpArgu))
                        );
            }
        }
        return sbud.toString();
    }
    /**
     * doExportArgument
     * @param paramArray paramArray
     * @return argumentXml
     */
    public String doExportArgument(Class[] paramArray) {
        StringBuilder xmlResult = new StringBuilder();
        int step = 0;
        for (Class classInst : paramArray) {
            xmlResult.append(tmpl4Argu.replaceAll("@TYPE@", classInst.getSimpleName())
                    .replaceAll("@NAME@", "arg".concat("" + step++)));
        }
        return xmlResult.toString();
    }
    /**
     * getFieldValue
     * @param fieldInst fieldInst
     * @param fieldType fieldType
     * @param fieldTarget fieldTarget
     * @return fieldValue
     */
    public String getFieldValue(Field fieldInst, Class fieldType, Object fieldTarget) {
        String tmpStr = "";
        try {
            if (fieldType == String.class) {
                tmpStr = (String) fieldInst.get(fieldTarget);
            } else if (fieldType == short.class) {
                tmpStr = String.valueOf(fieldInst.getShort(fieldTarget));
            } else if (fieldType == int.class) {
                tmpStr = String.valueOf(fieldInst.getInt(fieldTarget));
            } else if (fieldType == long.class) {
                tmpStr = String.valueOf(fieldInst.getLong(fieldTarget));
            } else if (fieldType == float.class) {
                tmpStr = String.valueOf(fieldInst.getFloat(fieldTarget));
            } else if (fieldType == double.class) {
                tmpStr = String.valueOf(fieldInst.getDouble(fieldTarget));
            } else if (fieldType == char.class) {
                tmpStr = String.valueOf(fieldInst.getChar(fieldTarget));
            } else if (fieldType == boolean.class) {
                tmpStr = String.valueOf(fieldInst.getBoolean(fieldTarget));
            } else if (fieldType == byte.class) {
                tmpStr = String.valueOf(fieldInst.getByte(fieldTarget));
            } else if (fieldType == List.class) {
                tmpStr = doExportObj(fieldInst.get(fieldTarget));
            } else {
                tmpStr = "";
            }
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        return tmpStr;
    }
    /**tmpl4Class*/
    private String tmpl4Class = "<CLASSE><NAME>@CLASS_NAME@</NAME><DESC>@CLASS_DESC@</DESC>@FIELD@@METHOD@</CLASSE>";
    /**tmpl4Field*/
    private String tmpl4Field = "<FIELD>"
        + "<FIELD_NAME>@FIELD_NAME@</FIELD_NAME>"
        + "<FIELD_TYPE>@FIELD_TYPE@</FIELD_TYPE>"
        + "@FIELD_VALUE@"
        + "</FIELD>";
    /**tmpl4FieldValue*/
    private String tmpl4FieldV = "<FIELD_VALUE>@FIELD_VALUE@</FIELD_VALUE>";
    /**tmpl4Method*/
    private String tmpl4Method = "<METHOD>"
        + "<NAME>@NAME@</NAME>"
        + "<RETURN_TYPE>@RTN_TYPE@</RETURN_TYPE>"
        + "@METHOD_ARG@"
        + "</METHOD>";
    /**tmpl4Argu*/
    private String tmpl4Argu = "<ARGUMENT><TYPE>@TYPE@</TYPE><NAME>@NAME@</NAME></ARGUMENT>";
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        List<Address> addressArray = new ArrayList<Address>();
        addressArray.add(new Address("China", "Shandong", "LiaoCheng", "lingQing", "3"));
        addressArray.add(new Address("China", "BeiJing", "WangJing", "hjd", "306"));
        FriendList addressListInst = new FriendList("wyc", "10", "15810779839", addressArray, "none");
        Export2Xml ex2Xml = new Export2Xml();
        ex2Xml.doExportObj(addressListInst);
    }
}
