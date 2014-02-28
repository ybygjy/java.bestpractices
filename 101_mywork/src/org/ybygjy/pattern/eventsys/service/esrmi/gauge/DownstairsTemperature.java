package org.ybygjy.pattern.eventsys.service.esrmi.gauge;
/**
 * DownstairsTemperature
 * @author WangYanCheng
 * @version 2010-7-23
 */
public class DownstairsTemperature extends TemperatureGaugeImpl {
    /**serial number*/
    private static final long serialVersionUID = -2688503724674276757L;
    /**
     * Constructor
     * @throws java.rmi.RemoteException RemoteException
     */
    public DownstairsTemperature() throws java.rmi.RemoteException {
        super();
    }
    /**
     * {@inheritDoc}
     */
    public int getMaximum() {
        return 65;
    }
    /**
     * {@inheritDoc}
     */
    public int getMinimum() {
        return 60;
    }
    /**
     * {@inheritDoc}
     */
    public String getLocation() {
        return "Downstairs";
    }
}
