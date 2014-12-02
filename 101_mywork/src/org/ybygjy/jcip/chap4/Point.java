package org.ybygjy.jcip.chap4;

/**
 * 二维位置
 * @author WangYanCheng
 * @version 2014-7-18
 */
public class Point {
    public final int x;
    public final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }
}
