package org.ybygjy.web.fusionchar;

import java.sql.Connection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Servlet implementation class for Servlet: InitServlet Initializes the
 * database name,path,DSN.Also, it instantiates the DBConnection class<br>
 * and puts it in the Application context so that all jsps can use this instance
 * of<br>
 * DBConnection class which has all the db related parameters configured.
 */
public class InitServlet {

    /**
     * Init method of the servlet.<br>
     * Reads the ServletContext parameters, instantiates DBConnection class<br>
     * and sets these values to it.
     * @param ctx ctx
     * @throws ServletException ServletException
     */
    public void init(ServletContext ctx) throws ServletException {
        DBConnection dbConn = new DBConnection();

        String dbName = "MySQL";
        String mySQLDSN = "jdbc/FusionChartsDB";

        dbConn.setDbName(dbName);
        dbConn.setMySQLDSN(mySQLDSN);
        ctx.setAttribute("dbConn", dbConn);
        try {
            Connection conn = dbConn.getConnection();
            System.out.println("数据库连接:" + conn.toString());
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
