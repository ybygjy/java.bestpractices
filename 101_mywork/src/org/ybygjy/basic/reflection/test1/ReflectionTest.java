package org.ybygjy.basic.reflection.test1;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.ybygjy.basic.reflection.test1.entity.UserEntity;

/**
 * ReflectionTest
 * @author WangYanCheng
 * @version 2009-12-16
 */
public class ReflectionTest {
    /**random inst*/
    private static Random randomInst = null;
    /**stepValue*/
    public int stepValue = 0;
    /**
     * constructor
     */
    public ReflectionTest() {
        randomInst = new Random();
    }
    /**
     * test constructor reflection
     */
    public void doTestConstructor() {
        Class[] paramType = new Class[]{String.class, String.class};
        try {
            Constructor[] consInstArray = UserEntity.class.getConstructors();
            for (Object tmpObj : consInstArray) {
                debugInfo.add("getConstructors:" + tmpObj.toString());
            }
            consInstArray = UserEntity.class.getDeclaredConstructors();
            for (Object tmpObj : consInstArray) {
                debugInfo.add("getDeclaredConstructors:" + tmpObj.toString());
            }
            Constructor consInst = UserEntity.class.getConstructor(paramType);
            Object[] argsV = new Object[]{"WangYanCheng", "123456"};
            UserEntity userEntity = (UserEntity) consInst.newInstance(argsV);
            userEntity.toString();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * doTestField
     * @param instObj instObj
     */
    public void doTestField(Object instObj) {
        Class classInst = instObj.getClass();
        Field[] fieldArray = classInst.getDeclaredFields();
        for (Field tmplField : fieldArray) {
            tmplField.setAccessible(true);
            String fieldName = tmplField.getName();
            Object fieldValue = "";
            try {
                fieldValue = tmplField.get(instObj);
                debugInfo.add(classInst.getName() + "-->" + fieldName + ":" + fieldValue);
                String tmplStr = String.valueOf(fieldValue);
                tmplStr += randomInst.nextInt();
                Class typeClass = tmplField.getType();
                if (List.class == typeClass) {
                    ArrayList<String> tmpArray = new ArrayList<String>();
                    tmpArray.add("ArrayTest_1");
                    tmpArray.add("ArrayTest_2");
                    tmplField.set(instObj, tmpArray);
                } else if (String.class == tmplField.getType()) {
                    tmplField.set(instObj, tmplStr);
                }
                debugInfo.add(classInst.getName() + "-->" + fieldName + ":" + tmplStr);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * testComponentType
     * @param objInst objInst
     */
    public void doTestComponentType(Object objInst) {
        Class objClass = objInst.getClass();
        Field[] fieldArray = objClass.getDeclaredFields();
        for (Field tmpField : fieldArray) {
            tmpField.setAccessible(true);
            Class fieldClass = tmpField.getType();
            if (fieldClass.isArray()) {
                Class elementType = fieldClass.getComponentType();
                debugInfo.add("Array of : @AO@".replaceAll("@AO@", elementType.getName()));
                doFillArray(tmpField, objInst, elementType);
            }
        }
    }
    /**
     * doFillArray
     * @param arrayObj arrayObj
     * @param objInst objInst
     * @param elemType elemType
     */
    public void doFillArray(Field arrayObj, Object objInst, Class elemType) {
        Object objArray = null;
        try {
            objArray = arrayObj.get(objInst);
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        if (null == objArray) {
            objArray = Array.newInstance(elemType, 10);
            for (int index = 0; index < 10; index++) {
                Array.set(objArray, index, String.valueOf(index));
            }
            try {
                arrayObj.set(objInst, objArray);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            debugInfo.add("Array of @AR@ IS NULL, SO WE CREATE A NEW ARRAY INSTANCE @ARI@"
                    .replaceAll("@AR@", arrayObj.getName())
                    .replaceAll("@ARI@", arrayObj.toString()));
        } else {
            int len = Array.getLength(objArray);
            debugInfo.add("Array of @AR@'s length @LEN@".replaceAll("@AR@", arrayObj.getName())
                    .replaceAll("@LEN@", String.valueOf(len)));
        }
    }
    /**
     * test method reflection
     * @param objInst objInst
     * @param fieldName methodName
     */
    public void doTestMethodRef(Object objInst, String fieldName) {
        String suffixStr = Character.toUpperCase(fieldName.charAt(0))
            + fieldName.substring(1);
        String[] templArray = {"get", "set"};
        Class instClass = objInst.getClass();
        try {
            Method tmplMethodInst = instClass.getDeclaredMethod(templArray[0].concat(suffixStr), new Class[]{});
            Object methodResult = tmplMethodInst.invoke(objInst, new Object[0]);
            debugInfo.add("The method @MN@ are result @MR@"
                    .replaceAll("@MN@", tmplMethodInst.getName())
                    .replaceAll("@MR@", String.valueOf(methodResult))
                    );
            tmplMethodInst = instClass.getDeclaredMethod(templArray[1].concat(suffixStr), new Class[]{String.class});
            tmplMethodInst.invoke(objInst, new Object[]{"王延成"});
            debugInfo.add("Invoke Method @MN@".replaceAll("@MN@", tmplMethodInst.getName()));
            tmplMethodInst = instClass.getDeclaredMethod(templArray[0].concat(suffixStr), new Class[]{});
            methodResult = tmplMethodInst.invoke(objInst, new Object[0]);
            debugInfo.add("The method @MN@ are result @MR@"
                    .replaceAll("@MN@", tmplMethodInst.getName())
                    .replaceAll("@MR@", String.valueOf(methodResult))
            );
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    /**
     * doShowDebugInfo
     */
    public void doShowDebugInfo() {
        for (Iterator<String> iterator = debugInfo.iterator(); iterator.hasNext();) {
            System.out.println(iterator.next());
        }
    }
    /**debugInfo*/
    private static List<String> debugInfo = new ArrayList<String>();
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        ReflectionTest reflectTestInst = new ReflectionTest();
        reflectTestInst.doTestConstructor();
        reflectTestInst.doTestField(new UserEntity("王延成", "wycPassword"));
        reflectTestInst.doTestComponentType(new UserEntity());
        reflectTestInst.doTestMethodRef(new UserEntity(), "userPassword");
        reflectTestInst.doShowDebugInfo();
    }
}
