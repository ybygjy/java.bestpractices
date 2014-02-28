package org.ybygjy.pattern.eventsys.jbmulticastevent;
/**
 * 事件机制-->JavaBean模式-->Multicast Event
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class Thermostat implements TemperatureChangeListener {
    /**is debug*/
    private boolean debug = false;
    /**
     * Constructor
     * @param debug debug
     */
    public Thermostat(boolean debug) {
        this.debug = debug;
    }
    /**
     * update Temperature
     * @param tceInst tceInst
     */
    public void updateTemperature(TemperatureChangeEvent tceInst) {
        if (debug) {
            String debugTmpl = "事件源{@ES@},PropagationId{@PI@},新值{@NV@},旧值{@OV@}";
            System.out.println(debugTmpl.replaceAll("@ES@", tceInst.getSource().toString())
                    .replaceAll("@NV@", tceInst.getNewValue().toString())
                    .replaceAll("@OV@", tceInst.getOldValue().toString())
                    );
        }
    }
}
