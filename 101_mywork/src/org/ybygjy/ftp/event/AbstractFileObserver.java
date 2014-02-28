package org.ybygjy.ftp.event;

import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.impl.FileObserver;
import org.apache.ftpserver.impl.FtpIoSession;

public class AbstractFileObserver implements FileObserver {
    /**
     * Constructor
     */
    public AbstractFileObserver() {
    }
    public void notifyDelete(FtpIoSession session, FtpFile file) {
    }

    public void notifyDownload(FtpIoSession session, FtpFile file, long size) {
        // TODO Auto-generated method stub
        
    }

    public void notifyMkdir(FtpIoSession session, FtpFile file) {
        // TODO Auto-generated method stub
        
    }

    public void notifyRmdir(FtpIoSession session, FtpFile file) {
        // TODO Auto-generated method stub
        
    }

    public void notifyUpload(FtpIoSession session, FtpFile file, long size) {
        // TODO Auto-generated method stub
        
    }

}
