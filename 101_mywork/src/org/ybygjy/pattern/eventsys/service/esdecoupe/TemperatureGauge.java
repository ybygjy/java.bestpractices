package org.ybygjy.pattern.eventsys.service.esdecoupe;

import java.io.Serializable;

/**
 * 事件处理机制-->JavaBean事件模式-->对事件服务进行退耦
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class TemperatureGauge implements Serializable {
    /**uid*/
    private static final long serialVersionUID = -8437409107561740941L;
    /**is debug*/
    private boolean debug = false;
    /**temperature*/
    private int temperature;
    /**max temperature*/
    private int maxinum = 70;
    /**min temperature*/
    private int mininum = 65;
    /**location*/
    private String location;
    /**
     * Constructor
     * @param debug isDebug
     */
    public TemperatureGauge(boolean debug) {
        this.debug = debug;
        new Thread(
                new Runnable() {
                    public void run() {
                        boolean increment = false;
                        int curr = 66;
                        while (true) {
                            curr = increment ? (++curr) : (--curr);
                            increment = curr == getMaxinum() ? false : curr == getMininum() ? true : increment;
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
     * getMaxinum
     * @return maxinum
     */
    public int getMaxinum() {
        return this.maxinum;
    }
    /**
     * getMininum
     * @return mininum
     */
    public int getMininum() {
        return this.mininum;
    }
    /**
     * getLocation
     * @return get curr location
     */
    public String getLocation() {
        return this.location;
    }
    /**
     * set Temperature
     * @param temperature temperature to set
     */
    public void setTemperature(int temperature) {
        if (debug) {
            System.out.println("DEBUG:TemperatureGauge.setTemperature->" + temperature);
        }
        TemperatureChangeEvent tceInst = new TemperatureChangeEvent(this, getLocation(), this.temperature, temperature);
        this.temperature = temperature;
        EventServiceImpl.getServiceInstance().fireTemperatureChangeEvent(tceInst);
    }
}
