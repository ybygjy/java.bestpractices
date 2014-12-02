package org.ybygjy.pattern.eventsys.service.esrmi.listener;

import java.rmi.RemoteException;

/**
 * 恒温装置-->侦听器
 * @author WangYanCheng
 * @version 2010-7-23
 */
public interface Thermostat extends java.rmi.Remote {
    /**
     * get Heater
     * @return true/false
     * @throws RemoteException RemoteException
     */
    boolean isHeater() throws RemoteException;
    /**
     * set heater
     * @param heater heater
     * @throws RemoteException RemoteException
     */
    void setHeater(boolean heater) throws RemoteException;
    /**
     * invoke this method when fire event
     * @param tceInst eventContextData
     * @throws RemoteException RemoteException
     */
    //void updateTemperature(TemperatureChangeEvent tceInst) throws RemoteException;
}
