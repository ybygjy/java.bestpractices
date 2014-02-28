package org.ybygjy.gui.springswing.event;

import java.awt.event.ActionEvent;

import org.ybygjy.gui.springswing.comp.ListTableActionListener;

/**
 * 负责删除按钮事件实现
 * @author WangYanCheng
 * @version 2011-2-16
 */
public class DeleteButtonActionListener extends ListTableActionListener {
    /**
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        int rowCount = table.getRowCount();
System.out.println("rowCount:" + rowCount + ":selectCount:" + selectedRow);
        if (rowCount <= selectedRow) {
            return;
        }
        if (selectedRow != -1 && !table.isEditing()) {
            list.remove(selectedRow);
            table.revalidate();
        }
    }
}
