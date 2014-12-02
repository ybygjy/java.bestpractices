package org.ybygjy.basic.date;

import static org.junit.Assert.*;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 封装日期处理工具测试
 * @author WangYanCheng
 * @version 2012-9-6
 */
public class DateUtilsTest {
    /** 日期1 */
    private Calendar date1;
    /** 日期2 */
    private Calendar date2;
    /** 年-月-日 */
    private String ymdPattern = "yyyy-MM-dd";

    @Before
    public void setUp() throws Exception {
        date1 = Calendar.getInstance();
        date2 = Calendar.getInstance();
        date1.clear();
        date2.clear();
        date1.set(Calendar.YEAR, 2012);
        date1.set(Calendar.MONTH, 8);
        date1.set(Calendar.DAY_OF_MONTH, 6);
        date2.set(Calendar.YEAR, 2012);
        date2.set(Calendar.MONTH, 8);
        date2.set(Calendar.DAY_OF_MONTH, 6);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetDiff() {
        Assert.assertEquals(DateUtils.getDiff(date1.getTime(), date2.getTime()), 0);
        date2.add(Calendar.YEAR, 1);
        Assert.assertEquals(DateUtils.getDiff(date1.getTime(), date2.getTime()), 365);
        date2.add(Calendar.MONTH, 1);
        Assert.assertEquals(DateUtils.getDiff(date1.getTime(), date2.getTime()), 395);
    }

    @Test
    public void testDiffDateGroup() {
        date2.add(Calendar.DAY_OF_MONTH, 5);
        String[] dateArr = DateUtils.diffDateGroup(date1.getTime(), date2.getTime());
        for (String tmpStr : dateArr) {
            System.out.println(tmpStr);
        }
        Assert.assertEquals(dateArr.length, 6);
    }

    @Test
    public void testDateFormat() {
        Assert.assertEquals(DateUtils.dateFormat(date1.getTime(), "yyyy-MM-dd"), "2012-09-06");
    }

}
