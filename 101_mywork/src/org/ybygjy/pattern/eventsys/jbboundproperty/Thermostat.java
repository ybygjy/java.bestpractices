package org.ybygjy.pattern.eventsys.jbboundproperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 事件机制-->JavaBean模式-->bound property pattern
 * @author WangYanCheng
 * @version 2010-2-20
 */
public class Thermostat implements PropertyChangeListener {
    /**isDebug*/
    private boolean debug = false;
    /**
     * Constructor
     * @param debug isdebug
     */
    public Thermostat(boolean debug) {
        this.debug = debug;
    }
    /**
     * {@inheritDoc}
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (debug) {
            String debugTmpl = "事件源{@ES@},事件标记{@FL@},PropagationId{@PI@},新值{@NV@},旧值{@OV@}";
            System.out.println(debugTmpl
                    .replaceAll("@ES@", evt.getSource().toString())
                    .replaceAll("@NV@", evt.getNewValue().toString())
                    .replaceAll("@OV@", evt.getOldValue().toString())
                    .replaceAll("@FL@", evt.getPropertyName())
                    .replaceAll("@PI@", String.valueOf(evt.getPropagationId())));
        }
    }
}
