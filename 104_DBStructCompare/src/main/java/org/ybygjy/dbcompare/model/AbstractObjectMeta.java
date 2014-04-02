package org.ybygjy.dbcompare.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础对象结构
 * @author WangYanCheng
 * @version 2011-10-5
 */
public class AbstractObjectMeta {
	private String objectId;
    private String objectName;
    private String owner;
    /** 对象类型{TABLE:VIEW:PRO:FUN:ETC} */
    private MetaType objectType;
    /**对象集*/
    private List<AbstractObjectFieldMeta> objectFieldArr;
    /**触发器集*/
    private List<TriggerMeta> triggerObjArr;
    /**约束集*/
    private List<ConstraintMeta> constraintArr;
    /**
     * Constructor
     */
    public AbstractObjectMeta() {
        objectFieldArr = new ArrayList<AbstractObjectFieldMeta>();
        triggerObjArr = new ArrayList<TriggerMeta>();
        constraintArr = new ArrayList<ConstraintMeta>();
    }

    public AbstractObjectMeta(String objName) {
        this();
        this.objectName = objName;
    }
    public AbstractObjectMeta(String objName, MetaType tableType) {
        this(objName);
        this.objectType = tableType;
    }
    /**
     * 添加字段对象
     * @param fieldCode 字段编码
     * @return 字段对象
     */
    public AbstractObjectFieldMeta addField(String fieldCode) {
        AbstractObjectFieldMeta tfo = new AbstractObjectFieldMeta(this, fieldCode);
        this.objectFieldArr.add(tfo);
        return tfo;
    }

    /**
     * 添加字段对象
     * @param tfoInst 字段对象
     */
    public void addField(AbstractObjectFieldMeta tfoInst) {
        getObjectFieldArr().add(tfoInst);
    }

    /**
     * 添加触发器对象
     * @param triggObj 触发器对象
     */
    public void addTrigger(TriggerMeta triggObj) {
        getTriggerObjArr().add(triggObj);
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * @return the tableFieldArr
     */
    public List<AbstractObjectFieldMeta> getObjectFieldArr() {
        return objectFieldArr;
    }

    /**
     * @param tableFieldArr the tableFieldArr to set
     */
    public void setObjectFieldArr(List<AbstractObjectFieldMeta> tableFieldArr) {
        this.objectFieldArr = tableFieldArr;
    }

    public List<TriggerMeta> getTriggerObjArr() {
        return triggerObjArr;
    }

    public void setTriggerObjArr(List<TriggerMeta> triggerObjArr) {
        this.triggerObjArr = triggerObjArr;
    }

    /**
     * 取表对象类型
     * @return objectType {table/view/etc}
     */
    public MetaType getObjectType() {
        return objectType;
    }

    /**
     * 存储表对象类型
     * @param objectType objectType
     */
    public void setObjectType(MetaType objectType) {
        this.objectType = objectType;
    }
    /**
     * 对象归属
     * @param owner owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }
    /**
     * 取对象归属
     * @return owner owner
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     * 对象标记码
     * @return objectId objectId
     */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * 存储对象标记码
	 * @param objectId objectId
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * 取对象约束集
	 * @return rtnConArr 对象约束集
	 */
	public List<ConstraintMeta> getConstraintArr() {
		return constraintArr;
	}

	/**
	 * 存储对象约束集
	 * @param constraintArr 对象约束集
	 */
	public void setConstraintArr(List<ConstraintMeta> constraintArr) {
		this.constraintArr = constraintArr;
	}

	/**
	 * 追加对象约束
	 * @param dbConsInst 对象约束
	 */
	public void addConstraint(ConstraintMeta dbConsInst) {
		getConstraintArr().add(dbConsInst);
	}
}
