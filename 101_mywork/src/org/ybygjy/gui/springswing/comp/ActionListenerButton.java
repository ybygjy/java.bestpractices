package org.ybygjy.gui.springswing.comp;

import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * 负责扩展JButton组件
 * @author WangYanCheng
 * @version 2011-2-16
 */
@SuppressWarnings("serial")
public class ActionListenerButton extends JButton {
    /** 事件监听器 */
    private ActionListener actionListener;

    /**
     * 添加事件监听器
     * @param actionListener 事件监听器
     */
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    /**
     * 初始化
     */
    public void init() {
        this.addActionListener(this.actionListener);
    }
}
