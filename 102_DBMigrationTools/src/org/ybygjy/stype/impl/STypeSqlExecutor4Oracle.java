package org.ybygjy.stype.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ybygjy.meta.model.FieldMeta;

/**
 * Oracle实现
 * @author WangYanCheng
 * @version 2012-10-15
 */
public class STypeSqlExecutor4Oracle extends AbstractSTypeSqlExecutor {

    /**
     * Constructor
     * @param conn {@link Connection}
     */
    public STypeSqlExecutor4Oracle(Connection conn) {
        super(conn);
    }

    /**
     * 依据JDBC交换Clob内容
     * @param targetClob 目标Clob
     * @param srcClob 原始Clob,可以是非Oracle
     * @throws SQLException {@link SQLException}
     */
    private void swapClob(Clob targetClob, Clob srcClob) throws SQLException {
        if (null == srcClob) {
            return;
        }
        targetClob.truncate(0);
        BufferedInputStream bis = new BufferedInputStream(srcClob.getAsciiStream());
        BufferedOutputStream bos = new BufferedOutputStream(targetClob.setAsciiStream(1));
        byte[] buff = new byte[4 * 1024];
        int flag = -1;
        try {
            while ((flag = bis.read(buff)) != -1) {
                bos.write(buff, 0, flag);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected int setterClobParameter(PreparedStatement pstmt, ResultSet rs, FieldMeta[] fms, int paramIndex)
        throws SQLException {
        for (FieldMeta fm : fms) {
            Clob tClob = this.conn.createClob();
            pstmt.setClob(paramIndex++, tClob);
            this.swapClob(tClob, rs.getClob(fm.getFieldCode()));
        }
        return paramIndex;
    }
}
