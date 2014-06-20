package org.ybygjy.basic.ch_110_generic;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

/**
 * 泛型反射
 * @author WangYanCheng
 * @version 2014-6-16
 */
public class GenericReflectionTest {
    public void doReflectClass(String className) {
        try {
            Class clazz = Class.forName(className);
            printClass(clazz);
            for (Method m : clazz.getDeclaredMethods()) {
                printMethod(m);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void printClass(Class clazz) {
        System.out.println(clazz);
        printTypes(clazz.getTypeParameters(), "<", ", ", ">", true);
        Type type = clazz.getGenericSuperclass();
        if (type != null) {
            System.out.print(" extends ");
            printType(type, false);
        }
        printTypes(clazz.getGenericInterfaces(), " implements ", ",", "", false);
        System.out.println();
    }
    private void printMethod(Method m) {
        String name = m.getName();
        System.out.print(Modifier.toString(m.getModifiers()));
        System.out.print(" ");
        printTypes(m.getTypeParameters(), "<", ", ", "> ", true);
        
        printType(m.getGenericReturnType(), false);
        System.out.print(" ");
        System.out.print(name);
        System.out.print("(");
        printTypes(m.getGenericParameterTypes(), "", ", ", "", false);
        System.out.println(")");
    }
    private void printTypes(Type[] types, String pre, String sep, String suf, boolean isDefinition) {
        if (pre.equals(" extends ") && Arrays.equals(types, new Type[]{Object.class})) {
            return;
        }
        if (types.length > 0) {
            System.out.print(pre);
        }
        for (int i = 0; i < types.length; i++) {
            if (i > 0) {
                System.out.print(sep);
            }
            printType(types[i], isDefinition);
        }
        if (types.length > 0) {
            System.out.print(suf);
        }
    }
    private void printType(Type type, boolean isDefinition) {
        if (type instanceof Class) {
            Class t = (Class) type;
            System.out.print(t.getName());
        } else if (type instanceof TypeVariable) {
            TypeVariable t = (TypeVariable) type;
            System.out.print(t.getName());
            if (isDefinition) {
                printTypes(t.getBounds(), " extends ", " & ", "", false);
            }
        } else if (type instanceof WildcardType) {
            WildcardType t = (WildcardType) type;
            System.out.print("?");
            printTypes(t.getUpperBounds(), " extends ", " & ", "", false);
            printTypes(t.getLowerBounds(), " super ", " & ", "", false);
        } else if (type instanceof ParameterizedType) {
            ParameterizedType t = (ParameterizedType) type;
            Type owner = t.getOwnerType();
            if (owner != null) {
                printType(owner, false);
                System.out.print(".");
            }
            printType(t.getRawType(), false);
            printTypes(t.getActualTypeArguments(), "<", ", ", ">", false);
        } else if (type instanceof GenericArrayType) {
            GenericArrayType t = (GenericArrayType) type;
            System.out.print("");
            printType(t.getGenericComponentType(), isDefinition);
            System.out.print("[]");
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        String className = Pair.class.getName();
        GenericReflectionTest grtInst = new GenericReflectionTest();
        grtInst.doReflectClass(className);
    }
}
