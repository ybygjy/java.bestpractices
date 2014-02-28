package org.ybygjy.basic.reflection.test1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ybygjy.basic.reflection.test1.entity.UserEntity;

/**
 * reflectionCapabilityTest
 * @author WangYanCheng
 * @version 2009-12-18
 */
public class ReflectionCapabilityTest {
    /**debugInfo*/
    private List<String> debugInfo = null;
    /**
     * constructor
     */
    public ReflectionCapabilityTest() {
        debugInfo = new ArrayList<String>();
    }
    /**
     * constructor
     * @param debugInfo debugInfo
     */
    public ReflectionCapabilityTest(List<String> debugInfo) {
        this.debugInfo = debugInfo;
    }
    /**
     * capability at same context
     * @param loopStep loopStep
     */
    public void capabilitySameContext(int loopStep) {
        long innerValue = 0;
        long start = System.currentTimeMillis();
        for (int index = 0; index < loopStep; index++) {
            innerValue += index;
        }
        debugInfo.add("capabilitySameContext intervalTime{@IT@},innerValue{@IV@}"
                .replace("@IT@", String.valueOf(System.currentTimeMillis() - start))
                .replaceAll("@IV@", "" + (int) innerValue));
    }
    /**
     * capabilityReference
     * @param loopStep loopStep
     */
    public void capabilityReference(int loopStep) {
        ReflectionTest rtInst = new ReflectionTest();
        long start = System.currentTimeMillis();
        for (int index = 0; index < loopStep; index++) {
            rtInst.stepValue += index;
        }
        debugInfo.add("capabilityReference intervalTime{@IT@}, referenceValue{@RV@}"
                .replaceAll("@IT@", String.valueOf(System.currentTimeMillis() - start))
                .replaceAll("@RV@", "" + rtInst.stepValue));
    }
    /**
     * capabilityReflection
     * @param loops loops
     */
    public void capabilityReflection(int loops) {
        UserEntity userEntity = new UserEntity();
        Field fieldInst;
        int value = 0;
        long start = System.currentTimeMillis();
        try {
            fieldInst = UserEntity.class.getDeclaredField("stepValue");
            for (int index = 0; index < loops; index++) {
                value = fieldInst.getInt(userEntity) + index;
                fieldInst.setInt(userEntity, value);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        debugInfo.add("capabilityReflection intervalTime{@IT@} reflectionValue{@RV@}"
                .replaceAll("@IT@", String.valueOf(System.currentTimeMillis() - start))
                .replaceAll("@RV@", "" + value));
    }
    /**
     * doShowDebugInfo
     */
    public void doShowDebugInfo() {
        for (Iterator iterator = debugInfo.iterator(); iterator.hasNext();) {
            System.out.println(iterator.next());
        }
    }
    /**
     * getInnerClass
     * @return innerClassObj
     */
    public InnerClass4TestMethod doGetInnerClass() {
        return new InnerClass4TestMethod();
    }
    /**
     * InnerCompiler do TestUser capability of invoke method
     * @author WangYanCheng
     * @version 2009-12-21
     */
    class InnerClass4TestMethod {
        /**
         * doTestCapability4SameContext
         * @param loops loops
         */
        public void doTestCapability4SameContext(int loops) {
            long beginTime = System.currentTimeMillis();
            int value = 0;
            for (int step = 0; step < loops; step++) {
                value = doTestInnerMethod(value);
            }
            debugInfo.add("doTestCapability4SameContext intervalTime{@IT@},value{@V@}"
                    .replaceAll("@IT@", String.valueOf(System.currentTimeMillis() - beginTime))
                    .replaceAll("@V@", String.valueOf(value)));
        }
        /**
         * doTestCapability4RefContext
         * @param loops loops
         */
        public void doTestCapability4RefContext(int loops) {
            long beginTime = System.currentTimeMillis();
            UserEntity userEntity = new UserEntity();
            int value = 0;
            for (int step = 0; step < loops; step++) {
                value = userEntity.doIncrementV(value);
            }
            debugInfo.add("doTestCapability4RefContext intervalTime{@IT@}, value @V@"
                    .replaceAll("@IT@", String.valueOf(System.currentTimeMillis() - beginTime))
                    .replaceAll("@V@", String.valueOf(value)));
        }
        /**
         * doTestCapability4ReflectContext
         * @param loops loops
         */
        public void doTestCapability4ReflectContext(int loops) {
            long beginTime = System.currentTimeMillis();
            UserEntity userEntity = new UserEntity();
            int value = 0;
            for (int step = 0; step < loops; step++) {
                Method methodInst;
                try {
                    methodInst = UserEntity.class.getMethod("doIncrementV", new Class[]{int.class});
                    value = (Integer) (methodInst.invoke(userEntity, new Object[]{Integer.valueOf(value)}));
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            debugInfo.add("doTestCapability4ReflectContext intervalTime{@IT@}, value @V@"
                    .replaceAll("@IT@", String.valueOf(System.currentTimeMillis() - beginTime))
                    .replaceAll("@V@", String.valueOf(value)));
        }
        /**
         * doTestCapability
         * @param value value
         * @return resultValue
         */
        private int doTestInnerMethod(int value) {
            return (++value);
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        ReflectionCapabilityTest rctInst = new ReflectionCapabilityTest();
        int loopStep = 100000000;
//        rctInst.capabilitySameContext(loopStep);
//        rctInst.capabilityReference(loopStep);
//        rctInst.capabilityReflection(loopStep);
        ReflectionCapabilityTest.InnerClass4TestMethod innerClass = rctInst.doGetInnerClass();
        innerClass.doTestCapability4SameContext(loopStep);
        innerClass.doTestCapability4RefContext(loopStep);
        innerClass.doTestCapability4ReflectContext(loopStep);
        rctInst.doShowDebugInfo();
    }
}
