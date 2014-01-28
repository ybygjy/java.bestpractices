package org.ybygjy.util;

import java.sql.Connection;

public class Test {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DBUtils.createConn4MSSql("jdbc:sqlserver://192.168.0.16;databaseName=nsag;user=nsag;password=11111111;instanceName=sql2005");
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(conn);
        }
    }
}
