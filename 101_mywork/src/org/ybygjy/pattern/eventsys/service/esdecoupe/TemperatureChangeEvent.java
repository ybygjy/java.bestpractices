package org.ybygjy.pattern.eventsys.service.esdecoupe;

import java.util.EventObject;

/**
 * 事件处理机制-->JavaBean事件处理-->对事件服务进行退耦<br>
 * 事件信息
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class TemperatureChangeEvent extends EventObject {

    /**UID*/
    private static final long serialVersionUID = 2301211692501025504L;
    /**new Temperature*/
    private Object newValue;
    /**old Temperature*/
    private Object oldValue;
    /**location*/
    private String location;
    /**
     * constructor
     * @param source source
     * @param location location
     * @param oldValue oldValue
     * @param newValue newValue
     */
    public TemperatureChangeEvent(Object source, String location, Object oldValue, Object newValue) {
        super(source);
        this.newValue = newValue;
        this.oldValue = oldValue;
        this.location = location;
    }
    /**
     * @return the newValue
     */
    public Object getNewValue() {
        return newValue;
    }
    /**
     * @param newValue the newValue to set
     */
    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }
    /**
     * @return the oldValue
     */
    public Object getOldValue() {
        return oldValue;
    }
    /**
     * @param oldValue the oldValue to set
     */
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }
    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }
    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
