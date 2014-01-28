package org.ybygjy.meta.model;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.ybygjy.logger.LoggerFactory;

/**
 * 约定FieldMeta类型
 * <table border=1>
 * <tr>
 * <th>类型编号</th>
 * <th>类型代码</th>
 * </tr>
 * <tr>
 * <td>00</td>
 * <td>STR</td>
 * </tr>
 * <tr>
 * <td>01</td>
 * <td>NUM</td>
 * </tr>
 * <tr>
 * <td>02</td>
 * <td>DATE</td>
 * </tr>
 * <tr>
 * <td>100</td>
 * <td>CLOB</td>
 * </tr>
 * <tr>
 * <td>200</td>
 * <td>BLOB</td>
 * </tr>
 * </table>
 * @author WangYanCheng
 * @version 2012-4-10
 */
public enum FieldType {
    STR(00), NUM(01), DATE(02), CLOB(100), BLOB(200), NCLOB(101);
    private int flag;
    private static Map<String, FieldType> msSqlTypeMap = new HashMap<String, FieldType>();
    private static Map<String, FieldType> oracleTypeMap = new HashMap<String, FieldType>();
    /**{@link Logger}*/
    private static Logger logger;
    private FieldType(int flag) {
        this.flag = flag;
    }

    public int getValue() {
        return this.flag;
    }
    static {
        logger = LoggerFactory.getInstance().getLogger(FieldType.class.getName());
    }
    static {
        /** 定义MSSql类型映射 */
        msSqlTypeMap.put("INT", NUM);
        msSqlTypeMap.put("DECIMAL", NUM);
        msSqlTypeMap.put("SMALLINT", NUM);
        msSqlTypeMap.put("NUMERIC", NUM);
        msSqlTypeMap.put("TINYINT", NUM);
        msSqlTypeMap.put("BIGINT", NUM);
        msSqlTypeMap.put("BIT", NUM);
        msSqlTypeMap.put("FLOAT", NUM);
        msSqlTypeMap.put("CHAR", STR);
        msSqlTypeMap.put("NCHAR", STR);
        msSqlTypeMap.put("VARCHAR", STR);
        msSqlTypeMap.put("NVARCHAR", STR);
        msSqlTypeMap.put("TEXT", CLOB);
        msSqlTypeMap.put("NTEXT", NCLOB);
        msSqlTypeMap.put("SMALLDATETIME", DATE);
        msSqlTypeMap.put("DATETIME", DATE);
        msSqlTypeMap.put("VARBINARY", BLOB);
        msSqlTypeMap.put("BINARY", BLOB);
        msSqlTypeMap.put("IMAGE", BLOB);
        /** 定义OracleSql类型映射 */
        oracleTypeMap.put("^NUMBER.*", NUM);
        oracleTypeMap.put("^CHAR.*", STR);
        oracleTypeMap.put("^VARCHAR2.*", STR);
        oracleTypeMap.put("^CLOB.*", CLOB);
        oracleTypeMap.put("^BLOB.*", BLOB);
        oracleTypeMap.put("^TIMESTAMP.*", DATE);
        oracleTypeMap.put("^DATE.*", DATE);
    }

    /**
     * 映射MSSql类型
     * @param fieldType 类型描述
     * @return rtnType {@link FieldType}
     */
    public static FieldType mappingType4MSSql(String fieldType) {
        fieldType = fieldType.toUpperCase();
        return msSqlTypeMap.containsKey(fieldType) ? msSqlTypeMap.get(fieldType) : null;
    }

    /**
     * 映射Oracle类型
     * @param fieldType 类型描述
     * @return rtnType {@link FieldType}默认返回{@linkplain FieldType#STR}
     */
    public static FieldType mappingType4Oracle(String fieldType) {
        FieldType rtnType = null;
        fieldType = fieldType.toUpperCase();
        for (String regExpStr : oracleTypeMap.keySet()) {
            if (fieldType.matches(regExpStr)) {
                rtnType = oracleTypeMap.get(regExpStr);
                break;
            }
        }
        if (rtnType == null) {
            logger.warning("非法Oracle数据类型==>".concat(fieldType));
        }
        return rtnType;
    }
}
