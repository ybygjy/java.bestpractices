package org.ybygjy.pattern.flyweight.coffee;
/**
 * 餐桌
 * @author WangYanCheng
 * @version 2010-11-22
 */
public class Table {
    /**number*/
    private int number;

    /**
     * Constructor
     * @param number number
     */
    public Table(int number) {
        this.number = number;
    }
    /**
     * getter the number
     * @return number
     */
    public int getNumber() {
        return this.number;
    }
}
