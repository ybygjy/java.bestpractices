package org.ybygjy.dbcompare.model;

/**
 * 定义对象约束
 * @author WangYanCheng
 * @version 2011-10-17
 */
public class ConstraintMeta {
	/**约束名称*/
	private String consName;
	/**约束类型{主键\外键\CHECK\UNIQUE\etc}*/
	private String consType;
	/**关联对象*/
	private AbstractObjectMeta dbomInst;
	/**
	 * Constraint
	 * @param consName 约束名称
	 */
	public ConstraintMeta(String consName){
		this.consName = consName;
	}
	/**
	 * 取约束名称
	 * @return
	 */
	public String getConsName() {
		return consName;
	}
	/**
	 * 存储约束名称
	 * @param consName 约束名称
	 */
	public void setConsName(String consName) {
		this.consName = consName;
	}

	/**
	 * 取约束类型
	 * @return consType rtnConsType
	 */
	public String getConsType() {
		return consType;
	}

	/**
	 * 存储约束类型
	 * @param consType 约束类型
	 */
	public void setConsType(String consType) {
		this.consType = consType;
	}

	/**
	 * 取关联对象实例
	 * @return rtnDBOMInst rtnDBOMInst
	 */
	public AbstractObjectMeta getDbomInst() {
		return dbomInst;
	}

	/**
	 * 存储关联对象实例
	 * @param dbomInst 对象实例
	 */
	public void setDbomInst(AbstractObjectMeta dbomInst) {
		this.dbomInst = dbomInst;
	}
}
