package org.ybygjy.dbcompare.model;

/**
 * 定义模型常量
 * @author WangYanCheng
 * @version 2011-10-8
 */
public class MetaConstant {
    /** 对象数量 */
    public static final String OBJ_COUNT = "$OBJ_COUNT";
    /** 对象缺失/多余统计*/
    public static final String OBJ_LOSTANDEXCESS = "$OBJ_LOSTEXCESS";
    /** 缺失对象数量 */
    public static final String OBJ_LOST = "$OBJ_LOST";
    /** 多余对象数量 */
    public static final String OBJ_EXCESS = "$OBJ_EXCESS";
    /**对象比较明细*/
    public static final String OBJ_COMPAREDETAIL = "$OBJ_COMPAREDETAIL";
    /**非法对象明细*/
    public static final String OBJ_INVALIDDETAIL = "$OBJ_INVALIDDETAIL";
    /** 缺失 */
    public static final int FLAG_LOST = 1;
    /** 多余 */
    public static final int FLAG_EXCESS = 2;
}
