package org.ybygjy.ftp;

import java.io.File;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FTPClientMgrTest {
    private static String parentDirPath;
    private static File keyStore;
    private FTPClientMgr ftpClientMgr;
    private FTPClient ftpClient;
    @Before
    public void setUp() throws Exception {
        parentDirPath = FTPClientMgrTest.class.getResource("").getFile();
        keyStore = new File(parentDirPath, "client.jks");
        ftpClientMgr = new FTPClientMgr();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCreateFTPClient() {
        ftpClient = ftpClientMgr.createFTPClient();
    }

    @Test
    public void testCreateFTPClient4SSL() {
        String password = "password";
        ftpClient = ftpClientMgr.createFTPClient4SSL(keyStore, password);
    }

    @Test
    public void testLogin() {
        testCreateFTPClient4SSL();
        Assert.assertTrue(FTPClientMgr.login(ftpClient, "admin", "admin"));
    }
}
