package org.ybygjy.pattern.eventsys.jbmulticastevent;

import java.util.EventObject;

/**
 * TemperatureChangeEvent
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class TemperatureChangeEvent extends EventObject {
    /**UID*/
    private static final long serialVersionUID = 1L;
    /**temp value*/
    private Object oldValue = null, newValue = null;
    /**
     * constructor
     * @param source source to set
     * @param oldValue oldValue value to set
     * @param newValue newValue value to set
     */
    public TemperatureChangeEvent(Object source, Object oldValue, Object newValue) {
        super(source);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    /**
     * set newValue
     * @param newValue newValue to set
     */
    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }
    /**
     * get newValue
     * @return the newValue to get
     */
    public Object getNewValue() {
        return this.newValue;
    }
    /**
     * set oldValue
     * @param oldValue oldValue to set
     */
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }
    /**
     * get old Value
     * @return the oldValue to get
     */
    public Object getOldValue() {
        return this.oldValue;
    }
}
