package org.ybygjy.dbcompare;

/**
 * 定义数据库类型
 * @author WangYanCheng
 * @version 2011-10-13
 */
public enum DBEnum {
	ORA(1),MSSQL(2);
	private int type;
	private DBEnum(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
}
