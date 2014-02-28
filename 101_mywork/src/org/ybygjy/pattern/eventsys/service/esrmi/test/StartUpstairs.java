package org.ybygjy.pattern.eventsys.service.esrmi.test;

import java.rmi.Naming;

import org.ybygjy.pattern.eventsys.service.esrmi.gauge.DownstairsTemperature;
import org.ybygjy.pattern.eventsys.service.esrmi.gauge.TemperatureGauge;
import org.ybygjy.pattern.eventsys.service.esrmi.gauge.UpstairsTemperature;
import org.ybygjy.pattern.eventsys.service.esrmi.service.EventService;

/**
 * StartUpstairs
 * @author WangYanCheng
 * @version 2010-7-23
 */
public class StartUpstairs {
    /**
     * portal
     * @param args args
     */
    public static void main(String[] args) {
        try {
            TemperatureGauge tgInst4Up = new UpstairsTemperature();
            TemperatureGauge tgInst4Down = new DownstairsTemperature();
            EventService remoteService = null;
            String rmiServer = "rmi://127.0.0.1:" + StartEventService.REGISTRY_PORT + "/EventService";
            remoteService = (EventService) Naming.lookup(rmiServer);
            tgInst4Down.setEventService(remoteService);
            tgInst4Up.setEventService(remoteService);
            Object obj = new Object();
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
