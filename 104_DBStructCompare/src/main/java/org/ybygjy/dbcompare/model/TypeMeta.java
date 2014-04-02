package org.ybygjy.dbcompare.model;

/**
 * 类型基础对象
 * @author WangYanCheng
 * @version 2011-10-10
 */
public class TypeMeta {
    private String typeName;
    private String typeOid;
    private String typeCode;
    private int attCounts;
    public TypeMeta(String typeName) {
        this.typeName = typeName;
    }
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getTypeOid() {
        return typeOid;
    }
    public void setTypeOid(String typeOid) {
        this.typeOid = typeOid;
    }
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    public int getAttCounts() {
        return attCounts;
    }
    public void setAttCounts(int attCounts) {
        this.attCounts = attCounts;
    }
}
