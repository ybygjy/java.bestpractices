package org.ybygjy.sql.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ybygjy.meta.model.FieldMeta;
import org.ybygjy.meta.model.FieldType;
import org.ybygjy.meta.model.TableMeta;

/**
 * 分析构造SQL
 * @author WangYanCheng
 * @version 2012-4-10
 */
public class AnalyseSql {
    /**表实体*/
    private TableMeta tableMeta;
    /**表字段集*/
    private String[] columns;
    /**表字段对象集*/
    private FieldMeta[] fieldMetaes;
    /**
     * 构造函数
     * @param tableMeta {@link TableMeta}
     */
    public AnalyseSql(TableMeta tableMeta) {
        this.tableMeta = tableMeta;
        //取字段对象集
        List<FieldMeta> fields = this.tableMeta.getFieldList();
        //分配字段对象空间
        fieldMetaes = fields.toArray(new FieldMeta[fields.size()]);
        int flag = 0;
        columns = new String[fields.size()];
        for (FieldMeta fmInst : fields) {
            columns[flag++] = fmInst.getFieldCode();
        }
    }

    /**
     * 构造插入语句
     * @return rtnSQL
     */
    public String analyseInsertSql() {
        StringBuffer tmpCol = new StringBuffer(), tmpPlaceholder = new StringBuffer();
        for (FieldMeta fieldMeta : fieldMetaes) {
            tmpCol.append(KeyWordMapp.mappingKeyWord(fieldMeta.getFieldCode())).append(",");
            tmpPlaceholder.append(KeyWordMapp.mappingTypeDefValue(fieldMeta.getFieldType(), "?").concat(","));
        }
        tmpCol.setLength(tmpCol.length() - 1);
        tmpPlaceholder.setLength(tmpPlaceholder.length() - 1);
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("INSERT INTO ").append(tableMeta.getTargetTableName()).append(" (")
            .append(tmpCol.toString()).append(") VALUES(").append(tmpPlaceholder.toString())
            .append(")");
        return sbuf.toString();
    }

    /**
     * 构造查询SQL
     * @return rtnSQL
     */
    public String analyseQuerySql() {
        StringBuffer rtnStr = new StringBuffer();
        rtnStr.append("SELECT ");
        for (FieldMeta fieldMeta : fieldMetaes) {
            if (fieldMeta.isSpecialType()) {
                continue;
            }
            rtnStr.append(KeyWordMapp.mappingKeyWord(fieldMeta.getFieldCode())).append(",");
        }
        rtnStr.setLength(rtnStr.length() - 1);
        rtnStr.append(" FROM ").append(tableMeta.getSrcTableName());
        return rtnStr.toString();
    }
    
    public FieldMeta[] getFieldMetaArr() {
        return this.fieldMetaes;
    }
    /**
     * 关键字映射
     * @author WangYanCheng
     * @version 2012-4-24
     */
    static class KeyWordMapp {
        /**关键字映射集*/
        private static Map<String, String> keyWordMapps = new HashMap<String, String>();
        /**特殊类型映射集*/
        private static Map<FieldType, String> typeDefVMapp = new HashMap<FieldType, String>();
        static {
            keyWordMapps.put("ROW", "\"ROW\"");
            keyWordMapps.put("Row", "\"Row\"");
            keyWordMapps.put("LEVEL", "\"LEVEL\"");
        }
        static {
            typeDefVMapp.put(FieldType.CLOB, "EMPTY_CLOB()");
            typeDefVMapp.put(FieldType.NCLOB, "EMPTY_CLOB()");
        }
        /**
         * 匹配给定字符的关键字
         * @param keyWorld 给定字符
         * @return rtnKey 给定字符/关键字映射
         */
        public static String mappingKeyWord(String keyWorld) {
            return keyWordMapps.containsKey(keyWorld) ? keyWordMapps.get(keyWorld) : keyWorld;
        }
        /**
         * 匹配特定字段类型对应的默认值
         * @param key 主键
         * @param defV 默认值
         * @return rtnV/defV
         */
        public static String mappingTypeDefValue(FieldType key, String defV) {
            return typeDefVMapp.containsKey(key) ? typeDefVMapp.get(key) : defV;
        }
    }
}