package org.ybygjy.basic.date;


/**
 * Calendar 使用
 * @author WangYanCheng
 * @version 2009-12-9
 */
public class CalendarApp {
    /**
     * 执行入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
//        Calendar calenInst = Calendar.getInstance();
//        String tmpDate = "2009-10-20 11:22:11";
//        System.out.println(tmpDate.substring(11, 16));
//        System.out.println(calenInst.get(Calendar.DAY_OF_WEEK_IN_MONTH));
        CalendarApp caInst = new CalendarApp();
        System.out.println(caInst.doStrCompare("14:59", "15:00"));
    }
    /**
     * doStrCompare
     * @param str1 str1
     * @param str2 str2
     * @return rtnResult {相等:大于:小于}
     */
    public String doStrCompare(String str1, String str2) {
        int rtnResult = str1.compareTo(str2);
        return rtnResult == 0 ? "相等" : rtnResult < 0 ? str1 + "小于" + str2 : str1 + "大于" + str2;
    }
}
