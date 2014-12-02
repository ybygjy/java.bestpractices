package org.ybygjy.basic.collect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ybygjy.test.TestUtils;

/**
 * List TestUser
 * @author WangYanCheng
 * @version 2009-12-13
 */
public class ListTest {
    /**
     * 测试List Size变化、remove
     */
    public void doTestListSize() {
        List<String> listInst = new ArrayList<String>();
        listInst.add("1");
        listInst.add("2");
        listInst.add("3");
        for (int index = 0, len = listInst.size(); index < len;) {
            System.out.println(index + ":" + listInst.get(index));
            listInst.remove(index);
            // index--;
            len--;
        }
    }

    /**
     * 测试toArray(T[] tInst) 特性
     * <p>1、toArray()是复制实例内存引用地址,所以外部对数据的修改会反应到List内的实例中</p>
     * <p>2、toArray(T[] a) 的实现机制，当a数组的长度小于源Array长度时使用源Array长度</p>
     */
    public void doTestToArray() {
        List<InnerEntity> tmpListChild = new ArrayList<InnerEntity>();
        InnerEntity rootEntity = new InnerEntity("A", null, null);
        tmpListChild.add(rootEntity);
        InnerEntity ieA = new InnerEntity("A.1", null, rootEntity);

        List<InnerEntity> tmpChildList = new ArrayList<InnerEntity>();
        tmpChildList.add(new InnerEntity("A.1.1", null, ieA));
        tmpChildList.add(new InnerEntity("A.1.2", null, ieA));
        tmpChildList.add(new InnerEntity("A.1.3", null, ieA));
        ieA.setItems(tmpChildList);
        tmpListChild.add(new InnerEntity("A.2", null, rootEntity));
        tmpListChild.add(new InnerEntity("A.3", null, rootEntity));
        InnerEntity[] ieArr = new InnerEntity[0];
        ieArr = tmpChildList.toArray(ieArr);
        System.out.println(ieArr[0].getName() + ":" + ieArr.length);
        ieArr[0].setName("HelloWorld");
        System.out.println(ieArr[0].getName());
    }

    /**
     * 验证toArray与toArray(obj[] obj)区别<br>
     * 结论： <li>不能直接使用((String[])arrayList.toArray())强制类型转换</li> <li>
     * toArray执行的是System.arrayCopy内存copy机制</li> <li>
     * 可以使用填充数组的方式，完成toArray(Object[] obj)类型转换</li>
     */
    public void doTestToArray2() {
        List<String> arrayList = new ArrayList<String>();
        arrayList.add("A");
        arrayList.add("B");
        Object[] strArr = arrayList.toArray();
        System.out.println(strArr.toString());
        String[] strArr2 = new String[arrayList.size()];
        arrayList.toArray(strArr2);
        TestUtils.doPrint(strArr2);
    }

    /**
     * 测试List#size为零时get(0)状态。结论：<br>
     * NullPointException
     */
    public void doTestListSizeE() {
        List<String> rtnList = new ArrayList<String>();
        System.out.println(rtnList.get(0));
    }

    /**
     * 测试binarySearch，结论：<br>
     * 非常量字段数组不增加额外代码如实现compare也可直接参与查找
     */
    public void doTestBinarySearch() {
        String[] strArr = {"A", "B"};
        int flag = Arrays.binarySearch(strArr, "B");
        System.out.println(flag);
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
//        ListTest listTestInst = new ListTest();
        // listTestInst.doTestListSize();
        // listTestInst.doTestListSizeE();
        // listTestInst.doTestToArray();
        // listTestInst.doTestToArray2();
//      listTestInst.doTestBinarySearch();
        List<Integer> tmpList = new ArrayList<Integer>();
        tmpList.add(13);
        tmpList.add(23);
        Integer[] intArr = tmpList.toArray(new Integer[0]);
        System.out.println(intArr.length);
    }
}
