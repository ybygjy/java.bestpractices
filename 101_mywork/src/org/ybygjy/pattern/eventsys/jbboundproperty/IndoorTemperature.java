package org.ybygjy.pattern.eventsys.jbboundproperty;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * 事件机制-->JavaBean模式-->Bound Property Pattern<br>
 * 室温
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class IndoorTemperature implements Serializable {
    /**UUID*/
    private static final long serialVersionUID = 1L;
    /** is debug */
    private boolean debug = false;
    /**temperature*/
    private int temperature = 60;
    /**侦听器管理*/
    private PropertyChangeSupport pcsInst = null;
    /**max temperature*/
    public static final int MAX_TEMPERATURE = 70;
    /**min temperature*/
    public static final int MIN_TEMPERATURE = 65;
    /**
     * constructor
     * @param debug isdebug
     */
    public IndoorTemperature(boolean debug) {
        this.debug = debug;
        this.pcsInst = new PropertyChangeSupport(this);
        new Thread(
                new Runnable() {
                    public void run() {
                        int curr = 66;
                        boolean increment = false;
                        while (true) {
                            curr = increment ? ++curr : --curr;
                            increment = curr == MIN_TEMPERATURE ? true : curr == MAX_TEMPERATURE ? false : increment;
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
     * temperature to set
     * @param temperature temperature
     */
    public void setTemperature(int temperature) {
        int oldTemperature = this.temperature;
        this.temperature = temperature;
        if (debug) {
            System.out.println("DEBUG:IndoorTemperature.setTemperature:" + temperature);
        }
        pcsInst.firePropertyChange("temperature", oldTemperature, this.temperature);
    }
    /**
     * 注册侦听器
     * @param pclInst pclInst
     */
    public void addPropertyChangeListener(PropertyChangeListener pclInst) {
        pcsInst.addPropertyChangeListener(pclInst);
    }
    /**
     * 删除指定侦听器
     * @param pclInst pclInst
     */
    public void removePropertyChangeListener(PropertyChangeListener pclInst) {
        pcsInst.removePropertyChangeListener(pclInst);
    }
}
