package org.ybygjy.pattern.eventsys.service.esrmi.service;

import java.rmi.RemoteException;

import org.ybygjy.pattern.eventsys.service.esrmi.listener.TemperatureChangeListener;
import org.ybygjy.pattern.eventsys.service.esrmi.module.TemperatureChangeEvent;

/**
 * EventService
 * @author WangYanCheng
 * @version 2010-7-23
 */
public interface EventService extends java.rmi.Remote {
    /**
     * Register TemperatureChangeListener to receive events.
     * @param listener The listener to register
     * @throws RemoteException RemoteException
     */
    void addTemperatureChangeListener(TemperatureChangeListener listener) throws RemoteException;
    /**
     * Removes TemperatureChangeListener from the list of listener
     * @param listener The listener instance to remove.
     * @throws RemoteException RemoteException
     */
    void removeTemperatureChangeListener(TemperatureChangeListener listener) throws RemoteException;
    /**
     * Notifies all registered listeners about the event.
     * @param eventInst The event to be fired.
     * @throws RemoteException RemoteException
     */
    void fireTemperatureChangeEvent(TemperatureChangeEvent eventInst) throws RemoteException;
}
