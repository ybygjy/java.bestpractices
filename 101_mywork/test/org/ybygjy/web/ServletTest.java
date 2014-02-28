/**
 * 
 */
package org.ybygjy.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ybygjy.test.RequestMethodEnum;
import org.ybygjy.test.TestUtils;

/**
 * @author WangYanCheng
 * @version 2010-7-30
 */
public class ServletTest {

    /**
     * @throws java.lang.Exception java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * TestUser method for
     * {@link org.ybygjy.web.Servlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * @throws IOException IOException
     */
    @Test
    public void testServiceHttpServletRequestHttpServletResponse_1() throws IOException {
        URL urlInst = null;
        URLConnection conn = null;
        StringBuilder sbui = new StringBuilder();
        try {
            urlInst = TestUtils.doCreateURL(domainUrl, sourceDesc, TestUtils.doConvertArray2Map(null,
                new String[][] { { "act", "doLogin" }, { "userName", "wangyancheng" }, { "userPW", "abcd" } }));
            conn = TestUtils.doCreateURLConn(urlInst, RequestMethodEnum.GET);
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                Charset.forName("UTF-8")));
            String tmpStr = null;
            while ((tmpStr = bufReader.readLine()) != null) {
                sbui.append(tmpStr);
            }
            Assert.assertEquals(sbui.toString(), preOutResult[0]);
        } catch (IOException ioe) {
            throw ioe;
//            throw new RuntimeException(ioe.getCause());
        }
    }
    /**
     * TestUser method for
     * {@link org.ybygjy.web.Servlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * @throws IOException IOException
     */
    @Test
    public void testServiceHttpServletRequestHttpServletResponse_2() throws IOException {
        URL urlInst = null;
        URLConnection conn = null;
        StringBuilder sbui = new StringBuilder();
        try {
            urlInst = TestUtils.doCreateURL(domainUrl, sourceDesc, TestUtils.doConvertArray2Map(null,
                new String[][] { { "act", "doLogin" }, { "userName", "" }, { "userPW", "aad" } }));
            conn = TestUtils.doCreateURLConn(urlInst, RequestMethodEnum.GET);
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                Charset.forName("UTF-8")));
            String tmpStr = null;
            while ((tmpStr = bufReader.readLine()) != null) {
                sbui.append(tmpStr);
            }
            Assert.assertEquals(sbui.toString(), preOutResult[1]);
        } catch (IOException ioe) {
            throw ioe;
        }
    }
    /**
     * TestUser method for
     * {@link org.ybygjy.web.Servlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * @throws IOException IOException
     */
    @Test
    public void testServiceHttpServletRequestHttpServletResponse_3() throws IOException {
        URL urlInst = null;
        URLConnection conn = null;
        StringBuilder sbui = new StringBuilder();
        try {
            urlInst = TestUtils.doCreateURL(domainUrl, sourceDesc, TestUtils.doConvertArray2Map(null,
                new String[][] { { "act", "doLogin" }, { "userName", "wangyancheng" }, { "userPW", "" } }));
            conn = TestUtils.doCreateURLConn(urlInst, RequestMethodEnum.GET);
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream(),
                Charset.forName("UTF-8")));
            String tmpStr = null;
            while ((tmpStr = bufReader.readLine()) != null) {
                sbui.append(tmpStr);
            }
            Assert.assertEquals(sbui.toString(), preOutResult[2]);
        } catch (IOException ioe) {
            throw ioe;
        }
    }

    /** domain */
    private String domainUrl = "http://localhost:8090";
    /** sourceDesc */
    private String sourceDesc = "/org.ybygjy.Servlet";
    /**µÇÂ½²âÊÔ½á¹û*/
    String[] preOutResult = { "{\"success\":false}",
        "{\"errorMessage\":\"µÇÂ½Ê§°Ü£¬ÓÃ»§ÃûÎª¿Õ£¡\",\"success\":false}",
    "{\"errorMessage\":\"µÇÂ½Ê§°Ü£¬ÃÜÂë²»ÄÜÎª¿Õ£¡\",\"success\":false}" };
}
