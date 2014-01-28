package org.ybygjy.ui.listener;

/**
 * 负责定义前端界面组件间的事件监听
 * @author WangYanCheng
 * @version 2012-9-7
 */
public interface UIListener {
    /**
     * 事件执行
     * @param e {@link UIEvent};
     */
    void actionPerformed(UIEvent e);
}
