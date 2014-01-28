package org.ybygjy.meta.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 抽象TableMeta模型
 * @author WangYanCheng
 * @version 2012-4-9
 */
public class TableMeta {
    /** 表名称 */
    private String tableName;
    /** 负责区分源与目标的映射，拙劣的设计导致必须这样做 */
    private String srcTableName;
    /** 负责区分源与目标的映射，拙劣的设计导致必须这样做 */
    private String targetTableName;
    /** fieldMeta集合 */
    private List<FieldMeta> fieldList = new ArrayList<FieldMeta>();
    /** fieldMap存储fieldMap集合基于hashCode机制 */
    private Map<String, FieldMeta> fieldMap = new HashMap<String, FieldMeta>();
    /** 表约束 */
    private List<ConstraintMeta> constraints = new ArrayList<ConstraintMeta>();
    /** 统计特殊类型字段数量，如CLOB\BLOB */
    private int specialTypeCounts;

    /**
     * 表名称
     * @param tableName 表名称
     */
    public void setTableName(String tableName) {
        this.tableName = tableName.toUpperCase();
    }

    public String getTableName() {
        return this.tableName;
    }

    /**
     * 添加FieldMeta
     * <p>
     * 1、添加FieldMeta给List
     * <p>
     * 2、添加FieldMeta给Map
     * @param fieldMeta {@link FieldMeta}
     */
    public void addField(FieldMeta fieldMeta) {
        String fieldCode = fieldMeta.getFieldCode();
        fieldCode = fieldCode.toUpperCase();
        if (!fieldMap.containsKey(fieldCode)) {
            this.fieldMap.put(fieldCode, fieldMeta);
        }
        if (fieldMeta.isSpecialType()) {
            this.specialTypeCounts++;
        }
        this.fieldList.add(fieldMeta);
    }

    /**
     * 取FieldMeta List集合对象
     * @return fieldsList fieldMeta集合
     */
    public List<FieldMeta> getFieldList() {
        return this.fieldList;
    }

    /**
     * 取FieldMeta Map集合对象
     * @return fieldMap fieldMeta集合
     */
    public Map<String, FieldMeta> getFieldMap() {
        return this.fieldMap;
    }

    /**
     * 取FieldMeta
     * @param fieldCode 字段编码
     * @return rtnFieldMeta {@link FieldMeta}
     */
    public FieldMeta getField(String fieldCode) {
        return this.fieldMap.get(fieldCode);
    }

    /**
     * 参考传递的tableMeta重建公共tableMeta
     * @param tableMeta 参照tableMeta
     * @return rtnTableMeta {@link TableMeta}
     */
    public TableMeta consultAndRebuilt(TableMeta tableMeta) {
        // TODO 这里有一层关于结构上的关系，首先当前是通过比较字段名的方式实现两个TableMeta内容的映射
        // TODO 我们缺少对这两个TableMeta的抽象映射模型，需要建立这个映射模型
        // TODO 依据这个映射模型我们就分离了当前依据两个TableMeta的公共关系来进行SQL构造和执行的处理模式
        // TODO 依据这个映射模型SQL构造这边就直接依据自己特定的TableMeta，不存在公共TableMeta
        // TODO 依据这个映射模型SQL执行这边就依赖这个映射模型进行插入或更新
        // TODO 数据模型的结构
        // TODO 1、负责描述两个TableMeta中字段的映射关系
        // TODO 2、定义A表的B字段与A(可其它)表的C字段有联系
        // TODO 3、该工具的定位就是就是处理表级别的数据导出和导入，所以映射模型也要基于这点去考虑
        TableMeta rtnTableMeta = new TableMeta();
        rtnTableMeta.setTableName(getTableName());
        rtnTableMeta.addConstraints(tableMeta.getConstraints());
        rtnTableMeta.setSpecialTypeCounts(tableMeta.getSpecialTypeCounts());
        Map<String, FieldMeta> consultFieldMap = tableMeta.getFieldMap();
        for (Iterator<String> iterator = this.fieldMap.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            // 这里未考虑字段类型等其它限定因素，只要求字段编码相同即可
            if (consultFieldMap.containsKey(key)) {
                FieldMeta tmpFieldMeta = consultFieldMap.get(key);
                FieldMeta fieldMeta = new FieldMeta();
                fieldMeta.setFieldCode(tmpFieldMeta.getFieldCode());
                fieldMeta.setFieldType(tmpFieldMeta.getFieldType());
                fieldMeta.setFieldTypeStr(tmpFieldMeta.getFieldTypeStr());
                rtnTableMeta.addField(fieldMeta);
            } else {
                System.err.println("表：".concat(tableMeta.getTableName()).concat("缺少字段：".concat(key)));
            }
        }
        return rtnTableMeta;
    }

    /**
     * 添加表的相关约束
     * @param constraints {@link ConstraintMeta}
     */
    public void addConstraints(List<ConstraintMeta> constraints) {
        this.constraints.addAll(constraints);
    }

    /**
     * 取表约束列表
     * @return constraints {@link ConstraintMeta}
     */
    public List<ConstraintMeta> getConstraints() {
        return this.constraints;
    }

    /**
     * 获取主键约束
     * @return primaryConstraint 主键约束/null
     */
    public ConstraintMeta getPrimaryConstraint() {
        if (this.constraints.size() > 0) {
            for (ConstraintMeta cmInst : this.constraints) {
                if (cmInst.getConstraintType() == ConstraintMeta.ConstraintType.P) {
                    return cmInst;
                }
            }
        }
        return null;
    }

    /**
     * 取特殊类型字段集
     * @return columns
     */
    public List<FieldMeta> getSpecialTypeColumns() {
        List<FieldMeta> rtnArray = new ArrayList<FieldMeta>(this.specialTypeCounts);
        for (FieldMeta fmInst : this.fieldList) {
            if (fmInst.getFieldType().getValue() >= FieldType.CLOB.getValue()) {
                rtnArray.add(fmInst);
            }
        }
        return rtnArray;
    }

    /**
     * 特殊类型字段数量
     * @return the specialTypeCounts
     */
    public int getSpecialTypeCounts() {
        return specialTypeCounts;
    }

    /**
     * 是否有非标准类型列
     * @return rtnFlag {true:是;false:否}
     */
    public boolean hasSpecialType() {
        return (this.specialTypeCounts > 0);
    }

    /**
     * 特殊类型字段数量
     * @param scount 字段数量
     */
    public void setSpecialTypeCounts(int scount) {
        this.specialTypeCounts = scount;
    }

    /**
     * @return the srcTableName
     */
    public String getSrcTableName() {
        return srcTableName;
    }

    /**
     * @param srcTableName the srcTableName to set
     */
    public void setSrcTableName(String srcTableName) {
        this.srcTableName = srcTableName;
    }

    /**
     * @return the targetTableName
     */
    public String getTargetTableName() {
        return targetTableName;
    }

    /**
     * @param targetTableName the targetTableName to set
     */
    public void setTargetTableName(String targetTableName) {
        this.targetTableName = targetTableName;
    }

    @Override
    public String toString() {
        return "TableMeta [tableName=" + tableName + ", fieldList=" + fieldList + "]";
    }
}
