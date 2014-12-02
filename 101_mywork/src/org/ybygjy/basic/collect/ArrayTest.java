package org.ybygjy.basic.collect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.ybygjy.test.TestUtils;

/**
 * 数组相关主题的测试
 * @author WangYanCheng
 * @version 2010-9-7
 */
public class ArrayTest {
    /**
     * 自定义comparator接口实现<br>
     * <strong>注意sort的使用</strong>
     */
    public void customCompare() {
        String[] srcArray = {"ABCCCCCC", "ABC", "ABCD", "ABCDE"};
        Arrays.sort(srcArray);
        String targetStr = "ABCCCCCC";
        int rtI = Arrays.binarySearch(srcArray, targetStr, new Comparator<String>() {
            public int compare(String o1, String o2) {
                System.out.println(o1 + ":" + o2 + "=" + o1.equals(o2));
                return (o1.equals(o2) ? 0 : -1);
            }
        });
        System.out.println(rtI + (rtI != -1 ? "匹配" : "不匹配"));
    }

    /**
     * 测试System#arrayCopy函数
     */
    public void doTestSysArrCopy() {
        String[] srcArr = {"1", "2", "3"};
        String[] desArr = new String[srcArr.length + 1];
        System.out.println("srcArr:" + srcArr.length + ";desArr:" + desArr.length);
        System.arraycopy(srcArr, 0, desArr, 0, srcArr.length);
        TestUtils.doPrint(srcArr);
        TestUtils.doPrint(desArr);
    }

    /**
     * 测试System#arrayCopy函数 <li>模拟多个源数组参与copy操作</li><br>
     * 结论 <li>System#arrayCopy()</li> <li>System#arrayCopy(T[] a)</li> <li>
     * 前者返回Object[]数组,并且不支持转换为特定类型</li> <li>后都返回参数类型数组,可支持转换</li>
     */
    public void doTestSysArrCopy2() {
        List<InnerEntity> arrA = new ArrayList<InnerEntity>();
        arrA.add(new InnerEntity("A", null, null));
        arrA.add(new InnerEntity("A.1", null, null));
        List<InnerEntity> arrB = new ArrayList<InnerEntity>();
        arrB.add(new InnerEntity("A", null, null));
        arrB.add(new InnerEntity("A.1", null, null));
        InnerEntity[] ieArr = new InnerEntity[0];
        Object[] obj = arrA.toArray(ieArr);
        ieArr = (InnerEntity[]) obj;
        InnerEntity[] tmpIeArr = new InnerEntity[ieArr.length + arrB.size()];
        System.arraycopy(ieArr, 0, tmpIeArr, 0, ieArr.length - 1);
        ieArr = arrB.toArray(ieArr);
        System.arraycopy(ieArr, 0, tmpIeArr, ieArr.length, ieArr.length - 1);
        System.out.println(tmpIeArr.length);
    }
    /**
     * 二维数组测试
     */
    public void doTestTwoDimensionTest() {
        String[][] twoDimTest = new String[1][7];
        twoDimTest[0] = new String[] {"313371", "44", "2013-03-33", "1000.00", "0", "6"};
        System.out.println(twoDimTest[0][0]);
    }
}
