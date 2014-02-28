package org.ybygjy.basic.annotation.test1;
/**
 * GroupTODO Annotation
 * @author WangYanCheng
 * @version 2009-12-14
 */
public @interface GroupTODO {
    /**severity*/
    public enum Severity { CRITICAL, IMPORTANT, TRIVIAL, DOCUMENTATION };
    /**
     * severity method
     * @return
     */
    Severity severity() default Severity.CRITICAL;
    /**
     * item
     * @return
     */
    String item();
    /**
     * assignedTo
     * @return
     */
    String assignedTo();
    /**
     * dateAssigned
     * @return
     */
    String dateAssigned();
}
