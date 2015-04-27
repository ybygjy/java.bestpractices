package org.ybygjy.basic.collect.maptest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.ybygjy.basic.TestInterface;

/**
 * 对顶层接口为Map的相关测试
 * @author WangYanCheng
 * @version 2010-8-23
 */
public class MapTest {
    /** innerClass */
    private static InnerClass[] ic = new InnerClass[10];

    /**
     * Constructor
     */
    public MapTest() {
        Random rand = new Random();
        for (int index = 9; index >= 0; index--) {
            ic[index] = new InnerClass(String.valueOf(index), String.valueOf(rand.nextLong()));
        }
    }

    /**
     * doBuildTestInstArray
     * @return testInstArray
     */
    public TestInterface[] doBuildTestInst() {
    	List<TestInterface> rtnList = new ArrayList<TestInterface>();
    	rtnList.add(new TreeMapTest());
    	rtnList.add(new LinkedHashMapTest());
    	TestInterface[] tiInst = rtnList.toArray(new TestInterface[rtnList.size()]);
        return tiInst;
    }

    /**
     * doFillMap
     * @param mapInst targetMapInst
     */
    private void doFillMap(Map<String, String> mapInst) {
        for (int index = ic.length - 1; index >= 0; index--) {
            InnerClass ict = ic[index];
            mapInst.put(ict.id, ict.label);
        }
    }

    /**
     * 测试Map#collection()方法，结论：<br>
     * <li>通过Collection#toArray(Type[] type)可实现将Map内容转成数组</li>
     */
    public void doTestCollection() {
        Map<String, String> mapInst = new HashMap<String, String>();
        mapInst.put("A", "AA");
        mapInst.put("B", "BB");
        String[] tmpStr = new String[mapInst.size()];
        tmpStr = mapInst.values().toArray(tmpStr);
        for (String str : tmpStr) {
            System.out.println(str);
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        /*
         * MapTest mtIns = new MapTest(); TestInterface[] tiArray =
         * mtIns.doBuildTestInst(); for (int i = 0; i < tiArray.length; i++) {
         * if (tiArray[i] != null) { tiArray[i].doTest(); } }
         */
        MapTest mtInst = new MapTest();
//        mtInst.doTestCollection();
        TestInterface[] testInterface = mtInst.doBuildTestInst();
        for(int i = 0; i < testInterface.length; i++) {
        	testInterface[i].doTest();
        }
    }

    /**
     * InnerCompiler
     * @author WangYanCheng
     * @version 2010-8-23
     */
    private class InnerClass {
        /** id */
        String id;
        /** label */
        String label;

        /**
         * Constructor
         * @param id id
         * @param label label
         */
        public InnerClass(String id, String label) {
            this.id = id;
            this.label = label;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("InnerCompiler [id=");
            builder.append(id);
            builder.append(", label=");
            builder.append(label);
            builder.append("]");
            return builder.toString();
        }
    }

    /**
     * doPrint MapInst Content
     * @param tmInst tmInst
     */
    private void doPrint(Map<String, String> tmInst) {
        doPrint(tmInst, true);
    }

    /**
     * doPrint Map Entry
     * @param tmInst targetMap
     * @param usedIter can used fail-fast iterator
     */
    private void doPrint(Map<String, String>  tmInst, boolean usedIter) {
        if (usedIter) {
            for (Iterator<String> iterator = tmInst.keySet().iterator(); iterator.hasNext();) {
                Object key = iterator.next();
                System.out.println(key + ":" + tmInst.get(key));
            }
        } else {
            Object[] keys = tmInst.keySet().toArray();
            for (int index = 0; index < keys.length; index++) {
                Object value = tmInst.get(keys[index]);
                System.out.println(keys[index] + ":" + value);
            }
        }
        System.out.println();
    }

    /**
     * 支持Key排序的map实现测试
     * @author WangYanCheng
     * @version 2010-8-23
     */
    protected class TreeMapTest implements TestInterface {
        /**
         * {@inheritDoc}
         */
        public void doTest() {
            TreeMap<String, String> treeMap = new TreeMap<String, String> ();
            doFillMap(treeMap);
            doPrint(treeMap);
            for(int i = 0; i < 10; i++) {
            	treeMap.put("Key_" + i, "Value_" + i);
            }
            Map<String, String> tmpMap = treeMap.tailMap("Key_");
            System.out.println(tmpMap.toString());
        }
    }

    /**
     * 有点像链表的List,可以传递一些类似LRU(最近最少使用)算法,来实现定期清理元素
     * @author WangYanCheng
     * @version 2010-8-23
     */
    protected class LinkedHashMapTest implements TestInterface {
        /**
         * {@inheritDoc}
         */
        public void doTest() {
            LinkedHashMap lhmInst = new LinkedHashMap(16, 0.75f, true);
            doFillMap(lhmInst);
            System.out.println(lhmInst);
            for (int index = 0; index < 7; index++) {
                lhmInst.get(String.valueOf(index));
            }
            doPrint(lhmInst, false);
        }
    }
}
