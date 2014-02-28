package org.ybygjy.pattern.eventsys.jbmulticastevent;
/**
 * 事件机制-->JavaBean模式-->Multicast Event Pattern
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class Test {
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        TemperatureChangeListener tclInst = new Thermostat(true);
        IndoorTemperature itInst = new IndoorTemperature(true);
        itInst.addTemperatureChangeListener(tclInst);
    }
}
