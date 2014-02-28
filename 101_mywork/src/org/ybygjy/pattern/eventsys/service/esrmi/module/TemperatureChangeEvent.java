package org.ybygjy.pattern.eventsys.service.esrmi.module;
/**
 * 事件上下文数据实体
 * @author WangYanCheng
 * @version 2010-7-23
 */
public class TemperatureChangeEvent extends java.util.EventObject {
    /** serialNo*/
    private static final long serialVersionUID = 2839327344538575126L;
    /**Holds value of property newTemperature*/
    private int newTemperature;
    /**Holds value of property newTemperature*/
    private int oldTemperature;
    /**Holds value of property location.*/
    private String location;
    /**
     * constructor
     * @param source source
     * @param location location
     * @param oldTemp oldTemp
     * @param newTemp newTemp
     */
    public TemperatureChangeEvent(Object source, String location, int oldTemp, int newTemp) {
        super(source);
        this.location = location;
        this.oldTemperature = oldTemp;
        this.newTemperature = newTemp;
    }
    /**
     * @return the newTemperature
     */
    public int getNewTemperature() {
        return newTemperature;
    }
    /**
     * @param newTemperature the newTemperature to set
     */
    public void setNewTemperature(int newTemperature) {
        this.newTemperature = newTemperature;
    }
    /**
     * @return the oldTemperature
     */
    public int getOldTemperature() {
        return oldTemperature;
    }
    /**
     * @param oldTemperature the oldTemperature to set
     */
    public void setOldTemperature(int oldTemperature) {
        this.oldTemperature = oldTemperature;
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
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "{Location:".concat(this.getLocation()) + ";oldTemperature:"
            + this.oldTemperature + ";newTemperature:" + this.newTemperature + "}";
    }
}
