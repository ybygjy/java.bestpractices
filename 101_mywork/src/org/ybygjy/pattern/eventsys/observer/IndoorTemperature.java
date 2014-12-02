package org.ybygjy.pattern.eventsys.observer;

import java.util.Observable;

/**
 * Java2平台事件处理机制-->Observer模式-->Observable
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class IndoorTemperature extends Observable {
    /** is debug */
    private boolean debug = false;
    /** temperature */
    private int temperature = 0;
    /** max temperature */
    public static final int MAX_TEMPERATURE = 67;
    /** min temperature */
    public static final int MIN_TEMPERATURE = 62;

    /**
     * Constructor
     * @param debug debug
     */
    public IndoorTemperature(boolean debug) {
        this.debug = debug;
        new Thread(new Runnable() {
            public void run() {
                boolean increment = false;
                int cur = MAX_TEMPERATURE;
                while (true) {
                    if (increment) {
                        cur++;
                    } else {
                        cur--;
                    }
                    setTemperature(cur);
                    increment = MIN_TEMPERATURE == cur ? true : MAX_TEMPERATURE == cur ? false
                            : increment;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * @return the temperature
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * @param temperature the temperature to set
     */
    public void setTemperature(int temperature) {
        if (this.temperature != temperature) {
            if (debug) {
                System.out.println("DEBUG::IndoorTemperature.setTemperature-->" + temperature);
            }
            this.temperature = temperature;
            setChanged();
        }
        notifyObservers(this.temperature);
    }
}
