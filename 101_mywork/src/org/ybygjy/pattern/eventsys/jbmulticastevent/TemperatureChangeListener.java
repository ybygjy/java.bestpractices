package org.ybygjy.pattern.eventsys.jbmulticastevent;

import java.util.EventListener;

/**
 * 事件机制-->JavaBean模式-->Multicast Event<br>
 * 侦听器接口
 * @author WangYanCheng
 * @version 2010-2-20
 */
public interface TemperatureChangeListener extends EventListener {
    /**
     * updateTemperature
     * @param tceInst tceInst
     */
    void updateTemperature(TemperatureChangeEvent tceInst);
}
