package org.ybygjy.pattern.eventsys.service.esdecoupe;
/**
 * 事件处理机制-->JavaBean事件模式-->对事件服务进行退耦<br>
 * 上升温度控制
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class UpstairsTemperature extends TemperatureGauge {
    /**uid*/
    private static final long serialVersionUID = -7800265258048073977L;
    /**
     * Constuctor
     * @param debug isDebug
     */
    public UpstairsTemperature(boolean debug) {
        super(debug);
    }
    /* (non-Javadoc)
     * @see org.ybygjy.pattern.eventsys.service.basic.TemperatureGauge#getLocation()
     */
    @Override
    public String getLocation() {
        return "UpstairsTemperature";
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
        return 65;
    }
}
