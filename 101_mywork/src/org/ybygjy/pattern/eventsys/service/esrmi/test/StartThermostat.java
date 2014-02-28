package org.ybygjy.pattern.eventsys.service.esrmi.test;

import java.rmi.Naming;

import org.ybygjy.pattern.eventsys.service.esrmi.listener.TemperatureChangeListener;
import org.ybygjy.pattern.eventsys.service.esrmi.listener.ThermostatImpl;
import org.ybygjy.pattern.eventsys.service.esrmi.service.EventService;



/**
 * StartThermostat
 * @author WangYanCheng
 * @version 2010-7-23
 */
public class StartThermostat {
    /**
     * portal
     * @param arg arg
     */
    public static void main(String[] arg) {
        try {
            TemperatureChangeListener tsInst = new ThermostatImpl();
            EventService remoteService = null;
            String rmiServer = "rmi://127.0.0.1:" + StartEventService.REGISTRY_PORT + "/EventService";
            remoteService = (EventService) Naming.lookup(rmiServer);
            remoteService.addTemperatureChangeListener(tsInst);
            Object obj = new Object();
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
