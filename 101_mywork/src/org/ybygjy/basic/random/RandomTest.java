package org.ybygjy.basic.random;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;


/**
 * RandomTest
 * @author WangYanCheng
 * @version 2009-12-10
 */
public class RandomTest {
    /**
     * 生成四位随机数
     * @param bound 边界
     */
    static void genral4BitNum(int bound) {
        Map<Integer, Integer> tmpMap = new HashMap<Integer, Integer>();
        Map<Integer, Integer> shiftMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < bound; i++) {
            int tmpI = (int) (Math.random() * 9000 + 1000);
            if (tmpMap.containsKey(tmpI)) {
                int count = 1;
                count = shiftMap.containsKey(tmpI) ? count + 1 : count;
                shiftMap.put(tmpI, count);
            }
            tmpMap.put(tmpI, tmpI);
        }
        for (Iterator iterator = tmpMap.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Entry) iterator.next();
            System.out.println(entry.getKey() + ":" + entry.getValue() + "\t");
        }
        System.out.println("重复");
        for (Iterator iterator = shiftMap.keySet().iterator(); iterator.hasNext();) {
            Integer key = (Integer) iterator.next();
            System.out.println(key + ":" + shiftMap.get(key) + " 次");
        }
    }
    /**
     * 生成指定范围的随机数
     * @param bound 边界
     * @param rangeB 起始
     * @param rangeE 结束
     */
    static void generalAreaNum(int bound, int rangeB, int rangeE) {
        for (int i = 0; i < bound; i++) {
            System.out.println(((int) (Math.random() * rangeE + rangeB)));
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        /*new Thread(new Runnable() {
            public void run() {
                while (true) {
                    System.out.println("nextBoolean:" + new Random().nextBoolean());
                    System.out.println("nextInt:" + new Random().nextInt());
                    System.out.println("nextLong:" + new Random().nextLong());
                    System.out.println("nextFloat:" + new Random().nextFloat());
                    System.out.println("nextDouble:" + new Random().nextDouble());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
        //genral4BitNum(10);
        generalAreaNum(100, 0, 5);
    }
}
