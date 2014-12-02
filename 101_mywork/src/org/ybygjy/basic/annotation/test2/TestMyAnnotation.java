package org.ybygjy.basic.annotation.test2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TestMyAnnotation
 * @author WangYanCheng
 * @version 2009-12-15
 */
public class TestMyAnnotation {
    /**调试信息*/
    private List<String> debugInfo = new ArrayList<String>();
    /**
     * AnnotationTest
     * @param classInst classInst
     */
    public void myAnnotationTest(Class classInst) {
        debugInfo.add(classInst.getName().concat(".annotation"));
        if (classInst.isAnnotationPresent(MyAnnotation.class)) {
            printMyAnnotation(classInst.getAnnotation(MyAnnotation.class));
        }
        //fieldAnnotation
        debugInfo.add(classInst.getName().concat(".fieldAnnotation"));
        doGetFieldAnnotation(classInst);
        //methodAnnotation
        debugInfo.add(classInst.getName().concat(".methodAnnotation"));
        doGetMethodAnnotation(classInst);
    }
    /**
     * doGetClassAnnotationField
     * @param gmaInst gmaInst
     */
    public void doGetFieldAnnotation(Class gmaInst) {
        String tmplStr = "[GetMyAnnotation.@FIELD_NAME@].annotation";
        Field[] fields = gmaInst.getDeclaredFields();
        for (Field filedInst : fields) {
            if (filedInst.isAnnotationPresent(MyAnnotation.class)) {
                debugInfo.add(tmplStr.replaceAll("@FILED_NAME@", filedInst.getName()));
                printMyAnnotation(filedInst.getAnnotation(MyAnnotation.class));
            }
        }
    }
    /**
     * doGetClassAnnotationMethod
     * @param gmaInst gmaInst
     */
    public void doGetMethodAnnotation(Class gmaInst) {
        String tmpStr = "[GetMyAnnotation.@METHOD_NAME@].annotation";
        Method[] methodArray = gmaInst.getMethods();
        for (Method methodInst : methodArray) {
            if (methodInst.isAnnotationPresent(MyAnnotation.class)) {
                debugInfo.add(tmpStr.replaceAll("@METHOD_NAME@", methodInst.getName()));
                printMyAnnotation(methodInst.getAnnotation(MyAnnotation.class));
            }
        }
    }
    /**
     * doPrintAnnotationInfo
     * @param annotationInst annotationInst
     */
    public void printMyAnnotation(Annotation annotationInst) {
        if (null == annotationInst) {
            return;
        }
        String tmplMes = "{value=@VALUE@;multiValues=@MULTIVALUE@;number=@NUMBERVALUE@}";
        if (annotationInst instanceof MyAnnotation) {
            MyAnnotation myAnnota = (MyAnnotation) annotationInst;
            String tmplStr = "";
            for (String tmplV : myAnnota.multiValues()) {
                tmplStr += "," + tmplV;
            }
            tmplMes = tmplMes.replaceAll("@VALUE@", myAnnota.value())
            .replaceAll("@NUMBERVALUE@", "" + myAnnota.number())
            .replaceAll("@MULTIVALUE@", tmplStr);
            debugInfo.add(tmplMes);
        }
    }
    /**
     * showDebugInfo
     */
    public void showDebugInfo() {
        for (Iterator iterator = debugInfo.iterator(); iterator.hasNext();) {
            System.out.println(iterator.next());
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        TestMyAnnotation testMyAnnotation = new TestMyAnnotation();
        testMyAnnotation.myAnnotationTest(GetMyAnnotation.class);
        testMyAnnotation.showDebugInfo();
    }
}
