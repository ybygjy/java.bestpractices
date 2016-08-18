package org.ybygjy.basic.algorithms;

/**
 * 最大公约数、最小公倍数
 * @author WangYanCheng
 * @version 2016年8月10日
 */
public class GCBService {
    public int gcb(int a, int b) {
        if (a < b) {
            return gcb(b, a);
        }
        int r = 0;
        while (b > 0) {
            r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    public static void main(String[] args) {
        GCBService gcbService = new GCBService();
        int result = gcbService.gcb(319, 377);
        System.out.println(result);
    }
}
