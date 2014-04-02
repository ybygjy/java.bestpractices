package org.ybygjy.dbcompare.model;

/**
 * 定义任务类别
 * @author WangYanCheng
 * @version 2011-10-9
 */
public enum MetaType {
    /** 表 */
    TABLE_OBJ(1),
    /** 表字段 */
    TABLE_FIELDOBJ(2),
    /** 视图 */
    VIEW_OBJ(3),
    /** 函数 */
    FUNC_OBJ(4),
    /** 过程 */
    PROC_OBJ(5),
    /** 触发器 */
    TRIG_OBJ(6),
    /** 序列 */
    SEQ_OBJ(7),
    /** 类型*/
    TYPE_OBJ(8),
    /** 约束*/
    CONS_OBJ(9),
    /** 状态非法*/
    INVALID_OBJ(10),
    /** 包对象(Oracle)*/
    PACKAGE_OBJ(11),
    /**未识别*/
    NONE_OBJ(100);
    private int taskType;

    /**
     * Constructor
     * @param taskType taskType
     */
    MetaType(int taskType) {
        this.taskType = taskType;
    }
    @Override
    public String toString() {
        return String.valueOf(this.taskType);
    }
}
