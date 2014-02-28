package org.ybygjy.pattern.eventsys.observer;


/**
 * Java2平台事件处理机制-->Observer模式-->测试入口
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class Test1 {
    /**
     * 测试入口
     * @param args args
     */
    public static void main(String[] args) {
        boolean isDebug = true;
        Thermostat tsInst = new Thermostat(isDebug);
        IndoorTemperature itInst = new IndoorTemperature(isDebug);
        itInst.addObserver(tsInst);
    }
}
