package org.ybygjy.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 验证JDBC日期与JDK日期的转换
 * @author WangYanCheng
 * @version 2012-06-08
 */
public class DateTest {
    /**日期*/
    private static SimpleDateFormat sdfInst = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 测试入口
     * @param args 参数列表
     */
	public static void main(String[] args) {
		long currTime = 0;
		try {
			currTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-05-18 01:01:01").getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(currTime);
		System.out.println(sdfInst.format(sqlDate));
	}
}
