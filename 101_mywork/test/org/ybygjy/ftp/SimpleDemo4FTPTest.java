package org.ybygjy.ftp;

import static org.junit.Assert.fail;

import java.io.File;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
/**
 * FTP测试
 * @author WangYanCheng
 * @version 2011-5-31
 */
public class SimpleDemo4FTPTest {
    private static SimpleDemo4FTP sd4FTP;
    private String remoteDir = ".";

    @Before
    public void setUp() throws Exception {
        sd4FTP = new SimpleDemo4FTP();
        Assert.assertNotNull(sd4FTP);
    }

    @After
    public void tearDown() throws Exception {
        sd4FTP.destroy();
    }

    @Test
    public void testUploadFile() {
        String filePath = (this.getClass().getResource(this.getClass().getSimpleName().concat(".class")).getFile());
        File uploadFile = new File(filePath);
        Assert.assertTrue(sd4FTP.uploadFile(remoteDir, uploadFile));
    }
    public void testUploadFile4SSL() {
        FTPClientMgr ftpMGR = new FTPClientMgr();
        FTPClient ftpClient = ftpMGR.createFTPClient();
        Assert.assertTrue(FTPClientMgr.login(ftpClient, "admin", "admin"));
        sd4FTP.setFtpClient(ftpClient);
    }
    @Test
    public void testListDir() {
        Assert.assertNotNull(sd4FTP.listDir("."));
    }
    @Test
    public void testList4ListParse() {
        sd4FTP.list4ListParse(remoteDir);
    }
    @Test
    public void testDownloadFile() {
        File outFile = new File(this.getClass().getResource("").getFile());
        String remoteFilePath = "\\mkdir\\sqlnet.log";
        File tmpFile = sd4FTP.download(remoteFilePath, outFile);
        Assert.assertNotNull(tmpFile);
        System.out.println("耗费：".concat(tmpFile.length() + "").concat("字节，下载了文件：").concat(tmpFile.getAbsolutePath()));
    }

    @Test
    public void testDownload() {
        fail("Not yet implemented");
    }

}
