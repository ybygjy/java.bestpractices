package org.ybygjy.meta.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义实体约束
 * @author WangYanCheng
 * @version 2012-9-13
 */
public class ConstraintMeta {
    /** 约束名称 */
    private String constraintName;
    /** 约束类型 */
    private ConstraintType constraintType;
    /** 关联表名称 */
    private TableMeta tableMeta;
    /** 启/禁用标识 */
    private boolean isEnable;
    /** 约束列 */
    private List<ConstraintColumnMeta> constaintColumn = new ArrayList<ConstraintColumnMeta>();

    /**
     * @return the constraintName
     */
    public String getConstraintName() {
        return constraintName;
    }

    /**
     * @param constraintName the constraintName to set
     */
    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    /**
     * @return the constraintType
     */
    public ConstraintType getConstraintType() {
        return constraintType;
    }

    /**
     * @param constraintType the constraintType to set
     */
    public void setConstraintType(ConstraintType constraintType) {
        this.constraintType = constraintType;
    }

    /**
     * @return the tableMeta
     */
    public TableMeta getTableMeta() {
        return tableMeta;
    }

    /**
     * @param tableMeta the tableMeta to set
     */
    public void setTableMeta(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
    }

    /**
     * @return the constaintColumn
     */
    public List<ConstraintColumnMeta> getConstraintColumn() {
        return constaintColumn;
    }

    /**
     * 添加约束字段
     * @param ccmArr 约束字段集
     */
    public void setConstraintColumn(List<ConstraintColumnMeta> ccmArr) {
        this.constaintColumn.addAll(ccmArr);
    }

    /**
     * @return the isEnable
     */
    public boolean isEnable() {
        return isEnable;
    }

    /**
     * @param isEnable the isEnable to set
     */
    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    /**
     * 定义Oracle约束类型的枚举
     * @author WangYanCheng
     * @version 2012-9-13
     */
    public enum ConstraintType {
        /** Unknown Type*/
        UN("UNKNOWN"),
        /** Check Constraint*/
        C("C"),
        /** Primary Key*/
        P("P"),
        /** Unique Key*/
        U("U"),
        /** Referential Integrity*/
        R("R"),
        /** With check option on a view*/
        V("V"),
        /** With read only, on a view*/
        O("O"),
        /** Hash expression*/
        H("H"),
        /** Constraint that involves a REF column*/
        F("F"),
        /** Supplemental logging*/
        S("S");
        /**类型标记*/
        private String type;
        
        /**
         * Constructor
         * @param type 类型
         */
        private ConstraintType(String type) {
            this.type = type;
        }
        
        /**
         * 给定字符串取对应的约束类型，默认返回未知类型
         * @param typeValue 给定类型字符串
         * @return rtnType 
         */
        public static ConstraintType getConstraintType(String typeValue) {
            for (ConstraintType ct : ConstraintType.values()) {
                if (ct.getConstraintValue().equals(typeValue)) {
                    return ct;
                }
            }
            return UN;
        }
        
        /**
         * 约束值
         * @return type 约束值
         */
        public String getConstraintValue() {
            return this.type;
        }
    }
}
