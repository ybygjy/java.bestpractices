package org.ybygjy.ui.listener;

import java.util.EventObject;

/**
 * 定义事件对象
 * @author WangYanCheng
 * @version 2012-9-7
 */
public class UIEvent extends EventObject {
    /**
     * serial number
     */
    private static final long serialVersionUID = 831280961473656832L;
    /**任务开始执行*/
    private static final int EVE_WORKSTART = 2000;
    /**任务执行完成*/
    private static final int EVE_WORKFINISH = 2001;
    /***/
    private static final int UI_EVE_C = 2002;
    /** 事件ID */
    private int eventType;

    /**
     * 构造函数
     * @param source 事件源
     */
    public UIEvent(Object source) {
        super(source);
    }

    /**
     * 构造函数
     * @param source 事件源
     * @param eventId 事件类型
     * @param arg 参数
     */
    public UIEvent(Object source, int eventId, Object arg) {
        super(source);
    }

    /**
     * 取得事件ID
     * @return eventType {@linkplain UIEvent#eventType}
     */
    public int getEventType() {
        return this.eventType;
    }
}
