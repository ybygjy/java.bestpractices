package org.ybygjy.pattern.eventsys.jbboundproperty;
/**
 * 事件机制-->JavaBean模式-->Bound Property Pattern
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class Test {
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        Thermostat therInst = new Thermostat(true);
        IndoorTemperature itInst = new IndoorTemperature(true);
        itInst.addPropertyChangeListener(therInst);
    }
}
