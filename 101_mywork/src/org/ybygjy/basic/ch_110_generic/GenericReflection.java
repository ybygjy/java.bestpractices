package org.ybygjy.basic.ch_110_generic;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * 利用反射机制分析泛型类
 * @author WangYanCheng
 * @version 2014-6-16
 */
public class GenericReflection {
    /** 类名称*/
    private String className;

    public GenericReflection(String className) {
        super();
        this.className = className;
    }
    public void doWork() {
        //加载类
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //处理类结构
        parseClass(clazz);
        //取类方法集合
        //处理方法
    }
    private void parseClass(Class clazz) {
        StringBuilder sbud = new StringBuilder();
        sbud.append(Modifier.toString(clazz.getModifiers())).append(" ")
            .append((clazz.isInterface() ? "interface " : (clazz.isPrimitive() ? "" : "class ")))
            .append(clazz.getName()).append(buildTypes(clazz.getTypeParameters(), "<", ",", ">"));
        
        System.out.println(sbud.toString());
    }
    /**
     * 构造类型字串
     * @param types 类型组
     * @param prefix 前缀
     * @param seprator 分隔
     * @param suffix 后缀
     * @return rtnStr
     */
    private String buildTypes(Type[] types, String prefix, String seprator, String suffix) {
        if (types.length == 0) {
            return "";
        }
        StringBuilder sbud = new StringBuilder(prefix);
        for (Type type : types) {
            sbud.append(buildType(type)).append(seprator);
        }
        sbud.append(suffix);
        return sbud.toString();
    }
    /**
     * 构造具体类型字串
     * @param type
     * @return
     */
    private String buildType(Type type) {
        StringBuilder sbud = new StringBuilder();
        if (type instanceof TypeVariable) {
            TypeVariable tv = (TypeVariable) type;
            sbud.append(((TypeVariable) type).getName());
        }
        return sbud.toString();
    }
    public static void main(String[] args) {
        new GenericReflection(Pair.class.getName()).doWork();
    }
}
