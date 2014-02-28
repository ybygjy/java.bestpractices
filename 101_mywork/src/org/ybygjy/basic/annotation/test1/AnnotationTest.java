package org.ybygjy.basic.annotation.test1;


/**
 * AnnotationTest
 * @author WangYanCheng
 * @version 2009-12-14
 */
public class AnnotationTest {
    /**
     * calculate Interest
     * @param amount amount
     * @param rate rate
     */
    @InProcess
    @TODO("Figure out the amount of interest per month")
    public void calculateInterest(float amount, float rate) {
        System.out.println("Amount" + amount + ":Rate" + rate);
    }
    /**
     * calculate Interest
     * @param amount amount
     * @param rate rate
     * @param flag flag
     */
    @InProcess
    @TODO(value = "Figure out the amount of interest per month")
    public void calculateInterest(float amount, float rate, boolean flag) {
        // Need to finish this method later
        System.out.println("Amount:" + amount + ",Rate:" + rate);
    }
    /**
     * calculate Interest group TODO
     * @param amount amount
     * @param rate rate
     */
    @InProcess
    @GroupTODO(
            severity = GroupTODO.Severity.CRITICAL,
            item = "Figure out the amount of interest per month",
            assignedTo = "yanCheng",
            dateAssigned = "2009-12-14"
    )
    public void calculateInterest4Group(float amount, float rate) {
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        AnnotationTest anotTestInst = new AnnotationTest();
        anotTestInst.calculateInterest(1.0f, 5.0f);
    }
}
