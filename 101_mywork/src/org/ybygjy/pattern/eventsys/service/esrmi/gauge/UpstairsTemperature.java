package org.ybygjy.pattern.eventsys.service.esrmi.gauge;

import java.rmi.RemoteException;

/**
 * UpstairsTemperature
 * @author WangYanCheng
 * @version 2010-7-23
 */
public class UpstairsTemperature extends TemperatureGaugeImpl {
    /** serial number*/
    private static final long serialVersionUID = 5642101906583159430L;

    /**
     * Constructor
     * @throws RemoteException RemoteException
     */
    public UpstairsTemperature() throws RemoteException {
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected String getLocation() {
        return "Upstairs";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getMaximum() {
        return 68;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getMinimum() {
        return 62;
    }
}
