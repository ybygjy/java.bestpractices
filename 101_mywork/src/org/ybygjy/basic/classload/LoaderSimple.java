package org.ybygjy.basic.classload;

/**
 * Java#ClassLoader实践
 * @author WangYanCheng
 * @version 2011-12-27
 */
public class LoaderSimple {
    public static void main(String[] args) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        System.out.println(cl);
        while (cl != null) {
            cl = cl.getParent();
            System.out.println(cl);
        }
        printBootLoader();
    }
    public static void printBootLoader() {
        try {
            String tmpStr = String.valueOf(Class.forName("java.lang.Object").getClassLoader());
            System.out.println("Object类的ClassLoader==>".concat(tmpStr));
            tmpStr = String.valueOf(Class.forName("org.ybygjy.basic.classload.LoaderSimple").getClassLoader());
            System.out.println("自定义类的ClassLoader==>".concat(tmpStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
