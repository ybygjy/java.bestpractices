package org.ybygjy.basic.thinking;
/**
 * 接口常量
 * @author WangYanCheng
 * @version 2010-5-30
 */
public class InterfaceStaticConstant {
    /**
     * 测试入口
     * @param args arguments list
     */
    public static void main(String[] args) {
        Month monthInst = Month.JUN;
        System.out.println(monthInst);
        monthInst = Month.number(12);
        System.out.println(monthInst);
    }
}
/**
 * 自定义安全的日期枚举
 * @author WangYanCheng
 * @version 2010-5-30
 */
final class Month {
    /**name*/
    private String name;
    /**
     * constructor
     * @param name name
     */
    private Month(String name) {
        this.name = name;
    }
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return this.name;
    }
    /**Month Definition*/
    public static final Month
        JAN = new Month("January"),
        FEB = new Month("February"),
        MAR = new Month("March"),
        APR = new Month("April"),
        MAY = new Month("May"),
        JUN = new Month("June"),
        JUL = new Month("July"),
        AUG = new Month("August"),
        SEP = new Month("September"),
        OCT = new Month("October"),
        NOV = new Month("November"),
        DEC = new Month("December");
    /**month array*/
    public static final Month[] MONTH =
    {JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC};
    /**
     * 取得指定月份
     * @param ord ord
     * @return month
     */
    public static Month number(int ord) {
        return MONTH[ord - 1];
    }
}
