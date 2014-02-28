package org.ybygjy.pattern.eventsys.service.esrmi.gauge;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.ybygjy.pattern.eventsys.service.esrmi.module.TemperatureChangeEvent;
import org.ybygjy.pattern.eventsys.service.esrmi.service.EventService;

/**
 * TemperatureGaugeImpl
 * @author WangYanCheng
 * @version 2010-7-23
 */
public abstract class TemperatureGaugeImpl extends UnicastRemoteObject implements TemperatureGauge {
    /**
     * serialVersion
     */
    private static final long serialVersionUID = 419485123714247008L;
    /** Holds value of property temperature. */
    private int temperature = 64;
    private static final boolean debug = true;
    /** eventService property */
    private EventService eventService = null;

    /**
     * Constructor
     * @throws RemoteException RemoteException
     */
    public TemperatureGaugeImpl() throws RemoteException {
        new Thread(new Runnable() {
            /**
             * run
             */
            public void run() {
                boolean increment = false;
                try {
                     int max = getMaximum();
                     int min = getMinimum();
                     int cur = getTemperature();
                     while (true) {
                         if (increment) {
                             cur++;
                         } else {
                             cur--;
                         }
                         setTemperature(cur);
                         if (cur == min) {
                             increment = true;
                         } else if (cur == max) {
                             increment = false;
                         }
                         Thread.sleep(10000);
                     }
                } catch (InterruptedException ire) {
                    ire.printStackTrace();
                } catch (RemoteException ine) {
                    ine.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * {@inheritDoc}
     */
    public int getTemperature()
        throws RemoteException {
        return this.temperature;
    }

    /**
     * {@inheritDoc}
     */
    public void setTemperature(int temperature)
        throws RemoteException {
        if (debug) {
            System.out.println("INFO::" + getClass().getName() + "::setTemperature " + temperature);
        }
        try {
            if (this.eventService != null) {
                eventService.fireTemperatureChangeEvent(new TemperatureChangeEvent(this, getLocation(),
                    this.temperature, temperature));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.temperature = temperature;
    }
    /**
     * {@inheritDoc}
     */
    public void setEventService(EventService eventServiceInst)
        throws RemoteException {
        this.eventService = eventServiceInst;
    }
    /**
     * getter for maximum value
     * @return maximum value
     */
    protected abstract int getMaximum();
    /**
     * getter for minimum value
     * @return minimum value
     */
    protected abstract int getMinimum();
    /**
     * getter for location
     * @return location
     */
    protected abstract String getLocation();
}
