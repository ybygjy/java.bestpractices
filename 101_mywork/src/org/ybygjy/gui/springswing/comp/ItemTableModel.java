package org.ybygjy.gui.springswing.comp;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * 负责定义列表组件相关内容
 * @author WangYanCheng
 * @version 2011-2-16
 */
@SuppressWarnings("serial")
public class ItemTableModel extends AbstractTableModel {
    /** tableItem list */
    private List itemList;

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
        System.out.println(super.getColumnName(column));
        return "Items";
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public int getColumnCount() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    public int getRowCount() {
        return itemList.size();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
     * int, int)
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        itemList.set(rowIndex, aValue);
    }

    /**
     * {@inheritDoc}
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return itemList.get(rowIndex);
    }

    /**
     * setter ItemList
     * @param itemList itemList
     */
    public void setItemList(List itemList) {
        this.itemList = itemList;
    }
}
