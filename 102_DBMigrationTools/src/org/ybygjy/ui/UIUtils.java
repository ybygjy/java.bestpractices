package org.ybygjy.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 封装公共函数，便于集中管理
 * @author WangYanCheng
 * @version 2012-9-4
 */
public class UIUtils {
    /**
     * 向给定容器内添加JLabel对象
     * @param container 容器对象
     * @param jLabelName 标签名称
     * @param gblInst {@link GridBagLayout}
     * @param gbcInst {@link GridBagConstraints}
     */
    public static JLabel makeJLabel(Container container, String jLabelName, GridBagLayout gblInst,
                            GridBagConstraints gbcInst) {
        gbcInst.weightx = 0;
        JLabel jLabel = new JLabel(jLabelName);
        gblInst.setConstraints(jLabel, gbcInst);
        container.add(jLabel);
        return jLabel;
    }

    /**
     * 向给定容器内添加JTextField对象
     * @param container 容器对象
     * @param defValue 默认值
     * @param gblInst {@link GridBagLayout}
     * @param gbcInst {@link GridBagConstraints}
     */
    public static JTextField makeJTextField(Container container, String defValue, GridBagLayout gblInst,
                                GridBagConstraints gbcInst) {
        gbcInst.ipadx = 150;
        gbcInst.weightx = 0.3;
        JTextField jTextField = new JTextField(defValue);
        gblInst.setConstraints(jTextField, gbcInst);
        container.add(jTextField);
        gbcInst.ipadx = 0;
        return jTextField;
    }

    /**
     * 向给定窗口内添加给定组件，使用GridBagLayout布局
     * @param container {@link Container}
     * @param compInst 组件{@link JComponent}
     * @param gblInst {@link GridBagLayout}
     * @param gbcInst {@link GridBagConstraints}
     */
    public static void addGBLComp(Container container, Container compInst,
                                  GridBagLayout gblInst, GridBagConstraints gbcInst) {
        gblInst.setConstraints(compInst, gbcInst);
        container.add(compInst);
    }

    /**
     * 向给定窗口内添加JButton组件
     * @param container 容器对象
     * @param btnText 按钮显示名称
     * @param gblInst {@link GridBagLayout}
     * @param gbcInst {@link GridBagConstraints}
     * @return rtnBtn {@link JButton}
     */
    public static JButton makeJButton(Container container, String btnText, GridBagLayout gblInst,
                                   GridBagConstraints gbcInst) {
        JButton rtnBtn = new JButton(btnText);
        gblInst.setConstraints(rtnBtn, gbcInst);
        container.add(rtnBtn);
        return rtnBtn;
    }
}
