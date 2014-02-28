package org.ybygjy.pattern.eventsys.service.esrmi.test;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.ybygjy.pattern.eventsys.service.esrmi.service.EventService;
import org.ybygjy.pattern.eventsys.service.esrmi.service.EventServiceImpl;

/**
 * StartEventListener
 * @author WangYanCheng
 * @version 2010-7-23
 */
public class StartEventService {
    /**register port*/
    public static final int REGISTRY_PORT = Registry.REGISTRY_PORT;
    public static final boolean debug = true;
    /**
     * Constructor
     */
    public StartEventService() {
    }
    /**
     * portal
     * @param args arguments list
     */
    public static void main(String[] args) {
        if (debug) {
            System.out.println("Creating registry...");
        }
        try {
            LocateRegistry.createRegistry(REGISTRY_PORT);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        if (debug) {
            System.out.println("Main::Retrieving the event service.");
        }
        try {
            EventService esInst = EventServiceImpl.getEventService();
            if (debug) {
                System.out.println("Main::Binding event service to registry.");
            }
            Naming.bind("EventService", esInst);
        } catch (RemoteException re) {
            re.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Object obj = new Object();
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException ire) {
                ire.printStackTrace();
            }
        }
    }
}
