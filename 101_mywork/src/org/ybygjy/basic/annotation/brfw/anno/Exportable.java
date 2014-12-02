package org.ybygjy.basic.annotation.brfw.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 修饰class类型
 * @author WangYanCheng
 * @version 2009-12-15
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Exportable {
    /**
     * name
     * @return
     */
    String name() default "";
    /**
     * description
     * @return
     */
    String description() default "";
    /**
     * value
     * @return
     */
    String value() default "";
}
