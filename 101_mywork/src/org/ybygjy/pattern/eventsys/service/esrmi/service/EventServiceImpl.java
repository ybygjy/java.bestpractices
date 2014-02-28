package org.ybygjy.pattern.eventsys.service.esrmi.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.ybygjy.pattern.eventsys.service.esrmi.listener.TemperatureChangeListener;
import org.ybygjy.pattern.eventsys.service.esrmi.module.TemperatureChangeEvent;

/**
 * EventServiceImpl
 * @author WangYanCheng
 * @version 2010-7-23
 */
public class EventServiceImpl extends UnicastRemoteObject implements EventService {
    /**serial number*/
    private static final long serialVersionUID = 220279096674775481L;
    /**Utility field used by event firing mechanism.*/
    private javax.swing.event.EventListenerList listenerList = null;
    /**EventServiceImpl*/
    private static EventServiceImpl serviceInst = null;
    /**
     * Constructor
     * @throws RemoteException RemoteException
     */
    private EventServiceImpl() throws RemoteException {
    }
    /**
     * getter the EventService implements instance
     * @return serviceInst
     * @throws RemoteException RemoteException
     */
    public static EventServiceImpl getEventService() throws RemoteException {
        if (null == serviceInst) {
            serviceInst = new EventServiceImpl();
        }
        return serviceInst;
    }
    /**
     * {@inheritDoc}
     */
    public synchronized void addTemperatureChangeListener(TemperatureChangeListener listener)
        throws RemoteException {
        if (null == listenerList) {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(TemperatureChangeListener.class, listener);
    }
    /**
     * {@inheritDoc}
     */
    public synchronized void removeTemperatureChangeListener(TemperatureChangeListener listener)
    throws RemoteException {
        listenerList.remove(TemperatureChangeListener.class, listener);
    }
    /**
     * {@inheritDoc}
     */
    public void fireTemperatureChangeEvent(TemperatureChangeEvent eventInst)
        throws RemoteException {
        if (null == this.listenerList) {
            return;
        }
        Object[] listeners = this.listenerList.getListeners(TemperatureChangeListener.class);
        for (int i = 0; i < listeners.length; i++) {
            ((TemperatureChangeListener) listeners[i]).updateTemperature(eventInst);
        }
    }
}
