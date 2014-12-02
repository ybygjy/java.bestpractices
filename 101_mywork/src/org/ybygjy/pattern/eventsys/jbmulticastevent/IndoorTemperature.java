package org.ybygjy.pattern.eventsys.jbmulticastevent;

import java.io.Serializable;
import java.util.EventListener;

import javax.swing.event.EventListenerList;
/**
 * 事件机制-->JavaBean模式-->Multicase Event Pattern<br>
 * 室温
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class IndoorTemperature implements Serializable {
    /**UID*/
    private static final long serialVersionUID = 1L;
    /**event listener*/
    private EventListenerList ellInst = null;
    /**debug*/
    private boolean debug = false;
    /**temperature*/
    private int temperature = 0;
    /**MAX TEMPERATURE*/
    public static final int MAX_TEMPERATURE = 70;
    /**MIN TEMPERATURE*/
    public static final int MIN_TEMPERATURE = 65;
    /**
     * Constructor
     * @param debug isDebug
     */
    public IndoorTemperature(boolean debug) {
        this.debug = debug;
        new Thread(
                new Runnable() {
                    public void run() {
                        int curr = 66;
                        boolean increment = false;
                        while (true) {
                            curr = increment ? (++curr) : (--curr);
                            increment = curr == MAX_TEMPERATURE ? false : curr == MIN_TEMPERATURE ? true : increment;
                            setTemperature(curr);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }
                        }
                    }
                }
                ).start();
    }
    /**
     * set temperature
     * @param temperature temperature to set
     */
    public void setTemperature(int temperature) {
        fireTemperatureEvent(this.temperature, temperature);
        this.temperature = temperature;
    }
    /**
     * addTemperatureChangeListener
     * @param listener listener to set
     */
    public synchronized void addTemperatureChangeListener(TemperatureChangeListener listener) {
        if (null == ellInst) {
            ellInst = new javax.swing.event.EventListenerList();
        }
        ellInst.add(TemperatureChangeListener.class, listener);
    }
    /**
     * removeTemperatureChangeListener
     * @param tclInst tclInst
     */
    public synchronized void removeTemperatureChangeListener(TemperatureChangeListener tclInst) {
        if (null != ellInst) {
            ellInst.remove(TemperatureChangeListener.class, tclInst);
        }
    }
    /**
     * fireTemperatureEvent
     * @param oldValue oldValue
     * @param newValue newValue
     */
    protected synchronized void fireTemperatureEvent(int oldValue, int newValue) {
        if (debug) {
            System.out.println("DEBUG:IndoorTemperature.fireTemperatureEvent:" + newValue);
        }
        if (null != ellInst) {
            TemperatureChangeEvent tceInst = new TemperatureChangeEvent(this, oldValue, newValue);
            EventListener[] elList = ellInst.getListeners(TemperatureChangeListener.class);
            for (int i = 0; i < elList.length; i++) {
                TemperatureChangeListener tcl = (TemperatureChangeListener) elList[i];
                tcl.updateTemperature(tceInst);
            }
        }
    }
}
