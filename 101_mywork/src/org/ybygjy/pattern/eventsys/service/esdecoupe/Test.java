package org.ybygjy.pattern.eventsys.service.esdecoupe;


/**
 * 事件处理机制-->JavaBean事件模式-->对事件服务进行退耦
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
        new DownstairsTemperature(true);
        new UpstairsTemperature(true);
        EventServiceImpl.getServiceInstance().addTemperatureChangeListener(therInst);
    }
}
