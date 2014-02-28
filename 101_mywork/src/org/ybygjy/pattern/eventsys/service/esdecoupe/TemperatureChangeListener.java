package org.ybygjy.pattern.eventsys.service.esdecoupe;

import java.util.EventListener;

/**
 * 事件处理机制-->JavaBean事件处理-->对事件服务进行退耦<br>
 * 侦听器接口
 * @author WangYanCheng
 * @version 2010-2-20
 */
public interface TemperatureChangeListener extends EventListener {
    /**
     * monitor the temperature change listener
     * @param tce tce
     */
    void updateTemperature(TemperatureChangeEvent tce);
}
