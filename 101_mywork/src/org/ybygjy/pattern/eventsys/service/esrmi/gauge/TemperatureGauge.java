package org.ybygjy.pattern.eventsys.service.esrmi.gauge;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.ybygjy.pattern.eventsys.service.esrmi.service.EventService;

/**
 * TemperatoreGauge
 * @author WangYanCheng
 * @version 2010-7-23
 */
public interface TemperatureGauge extends Remote, java.io.Serializable {
    /**
     * getter for property temperature
     * @return value of property temperature
     * @throws RemoteException RemoteException
     */
    int getTemperature() throws RemoteException;
    /**
     * setter for property temperature
     * @param temperature temperature value
     * @throws RemoteException RemoteException
     */
    void setTemperature(int temperature) throws RemoteException;
    /**
     * Setter for EventService Instance
     * @param eventServiceInst eventServiceInstance
     * @throws RemoteException RemoteException
     */
    void setEventService(EventService eventServiceInst) throws RemoteException;
}
