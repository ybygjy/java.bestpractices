package org.ybygjy.pattern.eventsys.service.esrmi.listener;

import java.rmi.RemoteException;

import org.ybygjy.pattern.eventsys.service.esrmi.module.TemperatureChangeEvent;

/**
 * 侦听器接口
 * @author WangYanCheng
 * @version 2010-7-23
 */
public interface TemperatureChangeListener extends java.util.EventListener, java.rmi.Remote {
    /**
     * 温度事件处理
     * @param evtInst evtInst
     * @throws RemoteException RemoteException
     */
    void updateTemperature(TemperatureChangeEvent evtInst) throws RemoteException;
}
