package org.ybygjy.basic.annotation.test2;
/**
 * GetMyAnnotation test MyAnnotation
 * @author WangYanCheng
 * @version 2009-12-15
 */
@MyAnnotation(
        value = "Class GetMyAnnotation",
        multiValues = { "1", "2" }
)
public class GetMyAnnotation {
    @MyAnnotation(value = "GetMyAnnotation testField", multiValues = { "3" }, number = 1)
    private String testField = null;
    /**
     * testMethod1
     */
    @MyAnnotation(value = "GetMyAnnotation testMethod1", multiValues = { "4" }, number = 2)
    public void testMethod1() {
        System.out.println("testMethod1");
    }
    /**
     * testMethod2
     */
    @Deprecated
    @MyAnnotation(value = "GetMyAnnotation testMethod2", multiValues = { "5" }, number = 3)
    public void testMethod2() {
        System.out.println("testMethod2");
    }
}
