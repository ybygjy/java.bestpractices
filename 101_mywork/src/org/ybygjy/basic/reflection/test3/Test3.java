package org.ybygjy.basic.reflection.test3;

import java.lang.reflect.Field;

/**
 * 负责验证某实体在没有sett/gett的情况下，设定实体属性
 * @author WangYanCheng
 * @version 2010-8-26
 */
public class Test3 {
    /**
     * doTest
     */
    public void doTest() {
        InnerEntity ie = new InnerEntity();
        Field[] fields = ie.getClass().getDeclaredFields();
        Field field = null;
        for (int i = fields.length - 1; i >= 0; i--) {
            field = fields[i];
            if (field.getType() == String.class) {
                field.setAccessible(true);
                try {
                    field.set(ie, "ABC");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
            }
        }
        System.out.println(ie);
    }
    /**
     * InnerEntity
     * @author WangYanCheng
     * @version 2010-8-26
     */
    class InnerEntity {
        /**code*/
        private String code;
        /**name*/
        private final String name = "It's finall variable.";

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("InnerEntity [code=");
            builder.append(code);
            builder.append(";name=");
            builder.append(name);
            builder.append("]");
            return builder.toString();
        }
    }
}
