package org.ybygjy.basic.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StatementTestTest {
    private StatementTest stmtTest;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        stmtTest = new StatementTest();
        conn = ConnectionMgr.getConn();
        stmtTest.setConn(conn);
    }

    @Test
    public void testPreparedTest() throws SQLException {
        stmtTest.preparedTest();
    }
    @Test
    public void testCursorHoldability() throws SQLException {
        stmtTest.cursorHoldabilitySupport();
    }
    @After
    public void endUp() throws Exception {
        ConnectionMgr.closeConn(conn);
    }
}
