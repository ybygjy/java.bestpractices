package org.ybygjy.gui.springswing.comp;

import java.awt.Component;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * 定义了应用程序的某个带有BoxLayout布局的组件
 * @author WangYanCheng
 * @version 2011-2-16
 */
@SuppressWarnings("serial")
public class BoxLayoutPanel extends JPanel {
    /** boxlayout与轴扩展相关参数 {按X/Y轴扩展}*/
    private int axis;
    /** 当前组件元素集合 */
    private List panelComponents;

    /**
     * setter Axis
     * @param axis axis
     */
    public void setAxis(int axis) {
        this.axis = axis;
        this.repaint();
    }

    /**
     * setter panelComponents
     * @param panelComponents 组件集合
     */
    public void setPanelComponents(List panelComponents) {
        this.panelComponents = panelComponents;
    }

    /**
     * 初始化
     */
    public void init() {
        setLayout(new BoxLayout(this, axis));
        for (Iterator iterator = panelComponents.iterator(); iterator.hasNext();) {
            Component comp = (Component) iterator.next();
            add(comp);
        }
    }
}
