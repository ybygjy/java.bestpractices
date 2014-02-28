package org.ybygjy.pattern.eventsys.service.esdecoupe;

import javax.swing.event.EventListenerList;

/**
 * 事件处理机制-->JavaBean事件模式-->对事件服务进行退耦<br>
 * 独立事件服务
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class EventServiceImpl {
    /**EventListenerList*/
    private EventListenerList ell = null;
    /**reference self instance*/
    private static EventServiceImpl service = null;
    /**
     * singleton pattern
     */
    private EventServiceImpl() {
    }
    /**
     * getInstance
     * @return instance for EventServiceImpl
     */
    public static final EventServiceImpl getServiceInstance() {
        if (null == service) {
            service = new EventServiceImpl();
        }
        return service;
    }
    /**
     * addTemperatureChangeListener
     * @param tclInst tclInst
     */
    public synchronized void addTemperatureChangeListener(TemperatureChangeListener tclInst) {
        if (null == ell) {
            ell = new EventListenerList();
        }
        ell.add(TemperatureChangeListener.class, tclInst);
    }
    /**
     * remove TemperatureChangeListener
     * @param tclInst temperatureChangeListener
     */
    public synchronized void removeTemperatureChangeListener(TemperatureChangeListener tclInst) {
        if (null != ell) {
            ell.remove(TemperatureChangeListener.class, tclInst);
        }
    }
    /**
     * fire Temperature change event
     * @param tce TemperatureChangeEvent
     */
    public void fireTemperatureChangeEvent(TemperatureChangeEvent tce) {
        if (null == ell) {
            return;
        }
        Object[] listeners = ell.getListeners(TemperatureChangeListener.class);
        for (int i = 0; i < listeners.length; i++) {
            ((TemperatureChangeListener) listeners[i]).updateTemperature(tce);
        }
    }
}
