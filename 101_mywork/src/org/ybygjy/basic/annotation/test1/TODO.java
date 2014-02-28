package org.ybygjy.basic.annotation.test1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation type to indicate a task still needs to be completed
 * @author WangYanCheng
 * @version 2009-12-14
 */
@Target({
       ElementType.TYPE,
       ElementType.METHOD,
       ElementType.CONSTRUCTOR
       }
)
@Retention(RetentionPolicy.CLASS)
public @interface TODO {
    /**
     * value
     * @return
     */
    String value();
}
