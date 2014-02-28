package org.ybygjy.gui.springswing.event;

import java.awt.event.ActionEvent;

import org.ybygjy.gui.springswing.comp.ListTableActionListener;
/**
 * 负责添加按钮事件监听实现
 * @author WangYanCheng
 * @version 2011-2-16
 */
public class AddNewButtonActionListener extends ListTableActionListener {
    /**
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e) {
        list.add("New Item 4 ActionPerformed " + e.getSource());
        table.revalidate();
    }
}
