package org.ybygjy.meta.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ybygjy.meta.model.ConstraintColumnMeta;
import org.ybygjy.meta.model.ConstraintMeta;
import org.ybygjy.meta.model.FieldMeta;
import org.ybygjy.meta.model.FieldType;
import org.ybygjy.meta.model.TableMeta;
import org.ybygjy.util.DBUtils;


/**
 * Meta管理MSSql实现
 * @author WangYanCheng
 * @version 2012-4-9
 */
public class MetaMgrImpl4MSSql extends AbstractMetaMgr {
    /**
     * 构造函数
     * @param conn {@link Connection}
     */
    public MetaMgrImpl4MSSql(Connection conn) {
        super(conn);
    }
    
    @Override
    public TableMeta getTableMeta(String tableName) {
        String sql = "SELECT UPPER(A.TABLE_NAME) AS TABLE_NAME FROM INFORMATION_SCHEMA.TABLES A WHERE A.TABLE_NAME =?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TableMeta rtnTableMeta = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tableName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                String tmpTableName = rs.getString("TABLE_NAME");
                rtnTableMeta = new TableMeta();
                rtnTableMeta.setTableName(tmpTableName);
                fetchFieldMeta(rtnTableMeta);
            }
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != pstmt) {
                DBUtils.close(pstmt);
            }
        }
        return rtnTableMeta;
    }

    @Override
    public Map<String, TableMeta> getAllTableMeta() {
        String sql = "SELECT UPPER(A.TABLE_NAME) AS TABLE_NAME FROM INFORMATION_SCHEMA.TABLES A WHERE A.TABLE_NAME ='FB_INTREST_RATES'";
        Statement stmt = null;
        ResultSet rs = null;
        Map<String, TableMeta> rtnMap = new HashMap<String, TableMeta>();
        try {
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                TableMeta tableMeta = new TableMeta();
                tableMeta.setTableName(tableName);
                fetchFieldMeta(tableMeta);
                rtnMap.put(tableName, tableMeta);
            }
        } catch (SQLException e) {
            this.logger.warning(e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != stmt) {
                DBUtils.close(stmt);
            }
        }
        return rtnMap;
    }

    /**
     * 取FieldMeta
     * @param tableMeta {@link TableMeta}
     * @throws SQLException {@link SQLException}
     */
    public void fetchFieldMeta(TableMeta tableMeta) throws SQLException {
        String sql = "SELECT UPPER(A.COLUMN_NAME) AS COLUMN_NAME, UPPER(A.DATA_TYPE) AS DATA_TYPE, A.COLUMN_DEFAULT, A.CHARACTER_MAXIMUM_LENGTH AS DATA_LENGTH FROM INFORMATION_SCHEMA.COLUMNS A WHERE UPPER(A.TABLE_NAME) = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = this.conn.prepareStatement(sql);
            pstmt.setString(1, tableMeta.getTableName());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                FieldMeta fmInst = new FieldMeta();
                fmInst.setFieldCode(rs.getString("COLUMN_NAME"));
                fmInst.setFieldTypeStr(rs.getString("DATA_TYPE"));
                fmInst.setFieldType(mappingFieldType(rs.getString("DATA_TYPE")));
                fmInst.setDataLength(rs.getInt("DATA_LENGTH"));
                tableMeta.addField(fmInst);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != rs) {
                rs.close();
            }
            if (null != pstmt) {
                pstmt.close();
            }
        }
    }

    /**
     * 映射字段类型
     * @param fieldType 类型字符串
     * @return rtnFieldType {@link FieldType}
     */
    private FieldType mappingFieldType(String fieldType) {
        FieldType rtnFieldType = FieldType.mappingType4MSSql(fieldType);
        return rtnFieldType == null ? FieldType.STR : rtnFieldType;
    }

    public static void main(String[] args) {
        Connection conn = DBUtils.createConn4MSSql("jdbc:sqlserver://192.168.3.225:1433;databaseName=MASTER;user=sa;password=syj");
        try {
            MetaMgrImpl4MSSql mmimInst = new MetaMgrImpl4MSSql(conn);
            Map<String, TableMeta> tmpMap = mmimInst.getAllTableMeta();
            System.out.println(tmpMap.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        DBUtils.close(conn);
    }

    @Override
    public void disableTableConstraint(String tableName, String consStr) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void disableTableConstraint(String[] tableNames) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void enableTableConstraint(String tableName, String consStr) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void enableTableConstraint(String[] tableNames) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String[] getTableConstraints(String tableName) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.ybygjy.meta.MetaMgr#disableTableConstraint(java.lang.String)
     */
    @Override
    public void disableTableConstraint(String tableName) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.ybygjy.meta.MetaMgr#enableTableConstraint(java.lang.String)
     */
    @Override
    public void enableTableConstraint(String tableName) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public List<ConstraintMeta> getConstraints(TableMeta tableMeta) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public List<ConstraintColumnMeta> getConstraintsColumns(ConstraintMeta cmInst) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void resetTableSequence(String tableName) {
        // TODO Auto-generated method stub
        
    }
}
