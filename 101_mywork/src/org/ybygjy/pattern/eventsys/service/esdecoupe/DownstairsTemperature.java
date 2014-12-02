package org.ybygjy.pattern.eventsys.service.esdecoupe;
/**
 * 事件处理机制-->JavaBean事件模式-->对事件服务进行退耦<br>
 * 下降温度控制
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class DownstairsTemperature extends TemperatureGauge {
    /**uid*/
    private static final long serialVersionUID = -3076358185041446755L;
    /**
     * Constructor
     * @param debug debug
     */
    public DownstairsTemperature(boolean debug) {
        super(debug);
    }
    /* (non-Javadoc)
     * @see org.ybygjy.pattern.eventsys.service.basic.TemperatureGauge#getLocation()
     */
    @Override
    public String getLocation() {
        return "DownstairsTemperature";
    }
    /* (non-Javadoc)
     * @see org.ybygjy.pattern.eventsys.service.basic.TemperatureGauge#getMaxinum()
     */
    @Override
    public int getMaxinum() {
        return 70;
    }
    /* (non-Javadoc)
     * @see org.ybygjy.pattern.eventsys.service.basic.TemperatureGauge#getMininum()
     */
    @Override
    public int getMininum() {
        return 60;
    }
}
