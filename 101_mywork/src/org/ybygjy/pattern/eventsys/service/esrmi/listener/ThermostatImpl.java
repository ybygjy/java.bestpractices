package org.ybygjy.pattern.eventsys.service.esrmi.listener;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import org.ybygjy.pattern.eventsys.service.esrmi.module.TemperatureChangeEvent;

/**
 * 侦听器接口实现
 * @author WangYanCheng
 * @version 2010-7-23
 */
public class ThermostatImpl extends UnicastRemoteObject implements TemperatureChangeListener, Thermostat {
    /**serial number*/
    private static final long serialVersionUID = -7663375468682958146L;
    /** Holds value of property heater. */
    private boolean heater;
    /** isDebug */
    private static final boolean debug = true;
    private Vector coldRooms = new Vector(2);

    /**
     * Constructor
     * @throws RemoteException RemoteException
     */
    public ThermostatImpl() throws RemoteException {
    }

    /**
     * {@inheritDoc}
     */
    public boolean isHeater()
        throws RemoteException {
        return this.heater;
    }

    /**
     * {@inheritDoc}
     */
    public void setHeater(boolean heater) throws RemoteException {
        if (this.heater != heater) {
            if (debug) {
                System.out.println("INFO::Thermostat::setHeater " + heater);
            }
            this.heater = heater;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateTemperature(TemperatureChangeEvent evtInst) throws RemoteException {
System.out.println(evtInst);
        int newTemp = evtInst.getNewTemperature();
        int oldTemp = evtInst.getOldTemperature();
        String location = evtInst.getLocation();
        if (newTemp < 64 && newTemp != oldTemp) {
            coldRooms.add(location);
            if (coldRooms.size() == 1) {
                setHeater(true);
            }
        } else if (newTemp >= 64 && coldRooms.contains(location)) {
            coldRooms.remove(location);
            if (coldRooms.size() == 0) {
                setHeater(false);
            }
        }
    }
}
