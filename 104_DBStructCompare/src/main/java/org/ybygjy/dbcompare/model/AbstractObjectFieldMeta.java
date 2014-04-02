package org.ybygjy.dbcompare.model;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 字段对象模型
 * @author WangYanCheng
 * @version 2011-10-13
 */
public class AbstractObjectFieldMeta {
    private AbstractObjectMeta tableObj;
    private String fieldCode;
    private String fieldType;
    private String fieldLen;
    private String fieldPre;
    private String fieldSca;
    private String fieldNull;
    /**字段对象排序/位置*/
    private String fieldOrder;
    private String fieldDefvLen;
    private String fieldDefValue;
    public AbstractObjectFieldMeta() {
    }
    public AbstractObjectFieldMeta(AbstractObjectMeta tableObj) {
    	this.tableObj = tableObj;
    }
    public AbstractObjectFieldMeta(AbstractObjectMeta tableObj, String fieldCode) {
    	this(tableObj);
        this.fieldCode = fieldCode;
    }
    /**
     * @return the tableObj
     */
    public AbstractObjectMeta getTableObj() {
        return tableObj;
    }
    /**
     * @param tableObj the tableObj to set
     */
    public void setTableObj(AbstractObjectMeta tableObj) {
        this.tableObj = tableObj;
    }
    /**
     * @return the fieldCode
     */
    public String getFieldCode() {
        return fieldCode;
    }
    /**
     * @param fieldCode the fieldCode to set
     */
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }
    /**
     * @return the fieldType
     */
    public String getFieldType() {
        return fieldType;
    }
    /**
     * @param fieldType the fieldType to set
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
    /**
     * @return the fieldLen
     */
    public String getFieldLen() {
        return fieldLen;
    }
    /**
     * @param fieldLen the fieldLen to set
     */
    public void setFieldLen(String fieldLen) {
        this.fieldLen = fieldLen;
    }
    /**
     * @return the fieldPre
     */
    public String getFieldPre() {
        return fieldPre;
    }
    /**
     * @param fieldPre the fieldPre to set
     */
    public void setFieldPre(String fieldPre) {
        this.fieldPre = fieldPre;
    }
    /**
     * @return the fieldSca
     */
    public String getFieldSca() {
        return fieldSca;
    }
    /**
     * @param fieldSca the fieldSca to set
     */
    public void setFieldSca(String fieldSca) {
        this.fieldSca = fieldSca;
    }
    /**
     * 取字段对象排序位置
     * @return fieldOrder fieldOrder
     */
    public String getFieldOrder() {
		return fieldOrder;
	}
    /**
     * 存储字段排序位置
     * @param fieldOrder fieldOrder
     */
	public void setFieldOrder(String fieldRID) {
		this.fieldOrder = fieldRID;
	}
	/**
     * @return the fieldNull
     */
    public String getFieldNull() {
        return fieldNull;
    }
    /**
     * @param fieldNull the fieldNull to set
     */
    public void setFieldNull(String fieldNull) {
        this.fieldNull = fieldNull;
    }
    /**
     * @return the fieldDefvLen
     */
    public String getFieldDefvLen() {
        return fieldDefvLen;
    }
    /**
     * @param fieldDefvLen the fieldDefvLen to set
     */
    public void setFieldDefvLen(String fieldDefvLen) {
        this.fieldDefvLen = fieldDefvLen;
    }
    /**
     * 取字段默认值
     * @return rtnDefValue fieldDefValue
     */
    public String getFieldDefValue() {
		return fieldDefValue;
	}
    /**
     * 存储字段默认值
     * @param fieldDefValue fieldDefValue
     */
	public void setFieldDefValue(String fieldDefValue) {
		this.fieldDefValue = fieldDefValue;
	}
	/**
	 * 从结果集对象加载数据
	 * @param rs 结果集
	 * @throws SQLException 抛出异常做Log
	 */
	public void loadData(ResultSet rs) throws SQLException {
        setFieldCode(rs.getString("FIELD_NAME"));
        setFieldType(rs.getString("FIELD_TYPE"));
        setFieldLen(rs.getString("FIELD_LEN"));
        setFieldPre(rs.getString("FIELD_PRE"));
        setFieldSca(rs.getString("FIELD_SCA"));
        setFieldNull(rs.getString("FIELD_NULL"));
        setFieldDefvLen(rs.getString("FIELD_DEFVLEN"));
        setFieldDefValue(rs.getString("FIELD_DEFVALUE"));
    }
}
