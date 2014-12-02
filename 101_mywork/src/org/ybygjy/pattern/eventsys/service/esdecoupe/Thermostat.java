package org.ybygjy.pattern.eventsys.service.esdecoupe;

import java.util.Vector;

/**
 * 事件处理机制-->JavaBean事件模式-->对事件服务进行退耦
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class Thermostat implements TemperatureChangeListener {
    /** isDebug*/
    private boolean debug = false;
    /**codeRoom*/
    private Vector coldRoom = null;
    /**
     * Constructor
     * @param debug debug
     */
    public Thermostat(boolean debug) {
        this.debug = debug;
    }
    /**
     * {@inheritDoc}
     */
    public void updateTemperature(TemperatureChangeEvent tceInst) {
        if (debug) {
            System.out.println(tceInst.getLocation() + ":" + tceInst.getSource().toString());
        }
    }
}
