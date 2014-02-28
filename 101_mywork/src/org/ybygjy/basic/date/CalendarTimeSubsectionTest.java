package org.ybygjy.basic.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 时间段测试
 * @author WangYanCheng
 * @version 2009-11-20
 */
public class CalendarTimeSubsectionTest {
    /**
     * doPrint
     * @param obj the printed info
     */
    public static void doPrint(Object obj) {
        System.out.println(obj.toString());
    }

    /**
     * 计算时间段
     */
    public void doCalcSubSect() {
        String[] arg1 = timeSubsection[0], arg2 = timeSubsection[1], arg3 = timeSubsection[2], tmpArg;
        Calendar currObj = Calendar.getInstance();
        //当前时间
        long currTime = currObj.getTimeInMillis(), startTime = 0L, stopTime = 0L;
        tmpArg = arg1[0].split(":");
        System.out.println("当前时间:" + currObj.getTimeInMillis());
        currObj.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmpArg[0]));
        currObj.set(Calendar.MINUTE, Integer.parseInt(tmpArg[1]));
        startTime = currObj.getTimeInMillis();
        tmpArg = arg1[1].split(":");
        currObj.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmpArg[0]));
        currObj.set(Calendar.MINUTE, Integer.parseInt(tmpArg[1]));
        stopTime = currObj.getTimeInMillis();
        if (startTime < currTime && currTime <= stopTime) {
            currObj.setTimeInMillis(currTime);
            System.out.println("大白班" + doParseDate(currObj, "yyyy-MM-dd HH:mm:ss"));
        }
        tmpArg = arg2[0].split(":");
        currObj.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmpArg[0]));
        currObj.set(Calendar.MINUTE, Integer.parseInt(tmpArg[1]));
        startTime = currObj.getTimeInMillis();
        tmpArg = arg2[1].split(":");
        currObj.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmpArg[0]));
        currObj.set(Calendar.MINUTE, Integer.parseInt(tmpArg[1]));
        stopTime = currObj.getTimeInMillis();
        if (startTime < currTime && currTime <= stopTime) {
            currObj.setTimeInMillis(currTime);
            System.out.println("小夜班:" + doParseDate(currObj, "yyyy-MM-dd HH:mm:ss"));
        } else {
            System.out.println("[\n@START@\n@STOP@\n[@CURR@]非小夜班]"
                    .replaceAll("@START@", String.valueOf(startTime))
                    .replaceAll("@STOP@", String.valueOf(stopTime))
                    .replaceAll("@CURR@", String.valueOf(currTime))
            );
        }
        tmpArg = arg3[0].split(":");
        currObj.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmpArg[0]));
        currObj.set(Calendar.MINUTE, Integer.parseInt(tmpArg[1]));
        startTime = currObj.getTimeInMillis();
        tmpArg = arg3[1].split(":");
        currObj.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmpArg[0]));
        currObj.set(Calendar.MINUTE, Integer.parseInt(tmpArg[1]));
        stopTime = currObj.getTimeInMillis();
        if (startTime < currTime && currTime <= stopTime) {
            currObj.setTimeInMillis(currTime);
            System.out.println("大夜班:" + doParseDate(currObj, "yyyy-MM-dd HH:mi:ss"));
        } else {
            System.out.println("[\n@START@\n@STOP@\n[@CURR@]非大夜班]"
                    .replaceAll("@START@", String.valueOf(startTime))
                    .replaceAll("@STOP@", String.valueOf(stopTime))
                    .replaceAll("@CURR@", String.valueOf(currTime))
            );
        }
    }
    /**
     * 测试入口
     * @param args arguments lists
     */
    public static void main(String[] args) {
        Calendar currObj = Calendar.getInstance();
        String dateTemplate = "@HOUR@时@MINUTE@分@SECOND@秒";
        System.out.println(dateTemplate.replaceAll("@HOUR@", String.valueOf(currObj.get(Calendar.HOUR_OF_DAY)))
                .replaceAll("@MINUTE@", String.valueOf(currObj.get(Calendar.MINUTE)))
                .replaceAll("@SECOND@", String.valueOf(currObj.get(Calendar.SECOND))));
        CalendarTimeSubsectionTest ctstObj = new CalendarTimeSubsectionTest();
        long currTime = currObj.getTimeInMillis();
        System.out.println(ctstObj.isShift(currTime, new String[]{"8:00", "15:16"}));
        System.out.println(ctstObj.isShift(currTime, new String[]{"8:00", "16:00"}));
        DateFormat dateFObj = new SimpleDateFormat("yyyy-MM-dd");
        try {
            currObj = Calendar.getInstance();
            doPrint("当前时间在{8:00,00:00}" + ctstObj.isShift(currTime, new String[]{"8:00", "00:00"}));
            currObj.setTime(dateFObj.parse("2009-12-04 "));
            System.out.println(ctstObj.isShift(currTime, new String[]{"16:00", "00:00"}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int resultFlag = ctstObj.doGetShift("");
        System.out.println(resultFlag);
    }
    /**
     * 验证某一时间是否在某一时间段
     * @param currTime 某一时间
     * @param timeSlot 某一时间段
     * @return true/false
     */
    public boolean isShift(final long currTime, String[] timeSlot) {
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTimeInMillis(currTime);
        String[] tmpArray = timeSlot[0].split(":");
        long startTime, stopTime;
        tempCalendar.clear(Calendar.HOUR_OF_DAY);
        tempCalendar.clear(Calendar.MINUTE);
        tempCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmpArray[0]));
        tempCalendar.set(Calendar.MINUTE, Integer.parseInt(tmpArray[1]));
        startTime = tempCalendar.getTimeInMillis();
        tmpArray = timeSlot[1].split(":");
        int stopHour  = Integer.parseInt(tmpArray[0]), stopMinute = Integer.parseInt(tmpArray[1]);
        if (stopHour == 0) {
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        tempCalendar.clear(Calendar.HOUR_OF_DAY);
        tempCalendar.clear(Calendar.MINUTE);
        tempCalendar.set(Calendar.HOUR_OF_DAY, stopHour);
        tempCalendar.set(Calendar.MINUTE, stopMinute);
        stopTime = tempCalendar.getTimeInMillis();
        return ((startTime < currTime && currTime <= stopTime) ? true : false);
    }
    /**
     * 班次计算
     * @param orgCode 所属单位
     * @return result {1:大夜;2:白班;3:小夜;4:夜班;0:特殊处理}
     */
    public int doGetShift(String orgCode) {
        int result = 0;
        Calendar currCalen = Calendar.getInstance();
        long currTime = currCalen.getTimeInMillis();
        if (isShift(currTime, timeSubsection[2])) {
            result = 1;
        } else if (isShift(currTime, timeSubsection[0])) {
            result = 2;
        } else if (isShift(currTime, timeSubsection[1])) {
            result = 3;
        }
        return result;
    }
    //时间段 0:白班;1:小夜班;2:大夜班*/
    private static String[][] timeSubsection = {{"8:00", "16:00"}, {"16:00", "00:00"}, {"00:00", "08:00"}};
    /**
     * 日期格式化
     * @param calenObj 日期实例
     * @param formatStr 格式化串
     * @return result 格式完成的串
     */
    public String doParseDate(Calendar calenObj, String formatStr) {
        DateFormat df = new SimpleDateFormat(formatStr);
        String result = null;
        result = df.format(calenObj.getTime());
        return result;
    }
}
