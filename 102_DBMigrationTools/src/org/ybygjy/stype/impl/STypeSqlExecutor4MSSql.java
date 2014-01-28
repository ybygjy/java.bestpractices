package org.ybygjy.stype.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ybygjy.meta.model.FieldMeta;

/**
 * SQL Server µœ÷
 * @author WangYanCheng
 * @version 2012-10-15
 */
public class STypeSqlExecutor4MSSql extends AbstractSTypeSqlExecutor {
    /**
     * Constructor
     * @param conn {@link Connection}
     */
    public STypeSqlExecutor4MSSql(Connection conn) {
        super(conn);
    }

    @Override
    protected int setterClobParameter(PreparedStatement pstmt, ResultSet rs, FieldMeta[] fms, int paramIndex)
        throws SQLException {
        return 0;
    }
}
