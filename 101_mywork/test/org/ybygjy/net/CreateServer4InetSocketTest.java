package org.ybygjy.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * CreateServer4InnerSocketTest
 * @author WangYanCheng
 * @version 2010-7-30
 */
public class CreateServer4InetSocketTest {
    private static CreateServer4InetSocket cs4isInst = null;
    @BeforeClass
    public static void setUpBeforeClass()
        throws Exception {
        cs4isInst = new CreateServer4InetSocket();
        cs4isInst.doStartServer(8000);
    }
    @Test
    public void doTestServer() throws Exception {
        URL urlInst = new URL("http://localhost:8000/1234.xml");
        URLConnection conn = urlInst.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String rtnStr = in.readLine();
        Assert.assertEquals("<?xml version=\"1.0\"?>", rtnStr);
        rtnStr = in.readLine();
        Assert.assertEquals("<resource id=\"HelloHttpServlet\" name=\"HelloWorld\"/>", rtnStr);
    }
    @AfterClass
    public static void afterDownClass()
        throws Exception {
        cs4isInst.doStopServer();
    }
}
