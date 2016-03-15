package org.ybygjy.basic.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 封装日期处理
 * @author WangYanCheng
 * @version 2012-9-6
 */
public class DateUtils {
    /**
     * 取两日期相隔天数
     * @param date1 日期1
     * @param date2 日期2
     * @return diffDay 相隔天数
     */
    public static int getDiff(Date date1, Date date2) {
        long t1 = date1.getTime();
        long t2 = date2.getTime();
        long t3 = Math.abs(t1 - t2);
        return (int) (t3 / 1000 / 60 / 60 / 24);
    }

    /**
     * 取两日期相隔的日期组
     * @param date1 日期1
     * @param date2 日期2
     * @return rtnDateArr 日期组
     */
    public static String[] diffDateGroup(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return diffDateGroup(date2, date1);
        }
        Calendar tmpCalendar = Calendar.getInstance();
        tmpCalendar.setTimeInMillis(date1.getTime());
        List<Date> tmpList = new ArrayList<Date>();
        while (tmpCalendar.getTimeInMillis() <= date2.getTime()) {
            tmpList.add(tmpCalendar.getTime());
            tmpCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return convertDateArr2StrArr(tmpList);
    }

    /**
     * 转换日期组
     * @param dateArr 日期集
     * @return 日期串组
     */
    public static String[] convertDateArr2StrArr(List<Date> dateArr) {
        String[] rtnArr = new String[dateArr.size()];
        int i = 0;
        for (Iterator<Date> iterator = dateArr.iterator(); iterator.hasNext();) {
            rtnArr[i++] = dateFormat(iterator.next(), "yyyy-MM-dd");
        }
        return rtnArr;
    }

    /**
     * 将给定日期依据格式字符串进行格式化
     * @param date 日期
     * @param pattern 格式字符串
     * @return rtnStr 日期字符串
     */
    public static String dateFormat(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
    /**
     * 解析给定字符串为日期对象
     * @param dateStr 日期字符串
     * @param pattern 日期字符串格式
     * @return rtnDate
     */
    public static Date str2Date(String dateStr, String pattern) {
    	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    	Date rtnDate = null;
		try {
			sdf.setLenient(false);
			rtnDate = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
System.out.println(DateUtils.dateFormat(rtnDate, "yyyy-MM-dd HH:mm:ss"));
    	return rtnDate;
    }
}
