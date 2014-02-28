package org.ybygjy.basic.annotation.test2;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * MyAnnotation
 * @author WangYanCheng
 * @version 2009-12-15
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    /**
     * value
     * @return
     */
    String value();
    /**
     * multiValues
     * @return
     */
    String[] multiValues();
    /**
     * number
     * @return
     */
    int number() default 0;
}
