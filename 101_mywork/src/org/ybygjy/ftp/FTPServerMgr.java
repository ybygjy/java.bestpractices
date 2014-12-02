package org.ybygjy.ftp;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.FtpStatistics;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.impl.DefaultFtpServer;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.apache.ftpserver.impl.ServerFtpStatistics;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.Md5PasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.ybygjy.ftp.event.AbstractFileObserver;

/**
 * 负责管理与FTP服务器连接对象的生命周期
 * @author WangYanCheng
 * @version 2011-5-27
 */
public class FTPServerMgr {
    /** 地址 */
    private static String hostName = "127.0.0.1";
    /** 端口 */
    private static int hostPort = 21;
    /** 用户名 */
    private static String userName = "admin";
    /** 密码 */
    private static String password = userName;
    /** ftpServer */
    private static FtpServer ftpServer;
    /** ftpServerMonitor*/
    private static FtpServerMonitor ftpServerMonitor = null;
    /** ftpServer*/
    private static FtpServerFactory ftpServerFactory = null;
    /**
     * Constructor
     */
    public FTPServerMgr() {
        ftpServerMonitor = new FtpServerMonitor();
        ftpServerMonitor.start();
    }
    /**
     * destroy ftp connection
     * @param ftpClient
     */
    public static void destroyFTPClient(FTPClient ftpClient) {
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动FTP功能
     * @param port 端口号
     * @param userInfoFile userInfoFile
     */
    public void startupSimpleFTPServer(final int port, final File userInfoFile) {
        Thread tmpThread = new Thread(new Runnable() {
            public void run() {
                while (null == ftpServer) {
                    ftpServerFactory = new FtpServerFactory();
                    ListenerFactory listenerFactory = new ListenerFactory();
                    listenerFactory.setPort(port < 21 ? 21 : port);
                    ftpServerFactory.addListener("default", listenerFactory.createListener());
                    ftpServerFactory.setUserManager(createUserMgr(userInfoFile));
                    ftpServer = ftpServerFactory.createServer();
                    try {
                        ftpServer.start();
                    } catch (FtpException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            tmpThread.start();
            tmpThread.join();
            tmpThread = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 带有SSL/TLS机制的FTP功能
     * @param port 端口号
     * @param keyStoreFile 安全证书
     * @param keyStorePassword 证书密码
     * @param userInfoFile ftp服务用户信息文件
     */
    public void startupFTPServer4SSL(final int port, final File keyStoreFile,
                                     final String keyStorePassword, final File userInfoFile) {
        Thread tmpThread = new Thread(new Runnable() {
            public void run() {
                while (null == ftpServer) {
                    ftpServerFactory = new FtpServerFactory();
                    ListenerFactory listenerFactory = new ListenerFactory();
                    listenerFactory.setPort(port);
                    SslConfigurationFactory sslFactory = new SslConfigurationFactory();
                    sslFactory.setKeystoreFile(keyStoreFile);
                    sslFactory.setKeystorePassword(keyStorePassword);
                    listenerFactory.setSslConfiguration(sslFactory.createSslConfiguration());
                    listenerFactory.setImplicitSsl(true);
                    ftpServerFactory.addListener("default", listenerFactory.createListener());
//                    ftpServerFactory.setUserManager(createUserMgr(userInfoFile));
                    ftpServerFactory.setUserManager(new CNGIFtpUserManager("admin", new Md5PasswordEncryptor()));
                    ftpServer = ftpServerFactory.createServer();
                    try {
                        ftpServer.start();
                        ftpServerMonitor.setFtpServer(ftpServer);
                        registerFtplet(ftpServer);
                    } catch (FtpException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            tmpThread.start();
            tmpThread.join();
            tmpThread = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create UserManager Instance
     * @param userInfoFile userInfoFile
     * @return userManager/null;
     */
    private static UserManager createUserMgr(File userInfoFile) {
        PropertiesUserManagerFactory userMgrFactory = new PropertiesUserManagerFactory();
        userMgrFactory.setFile(userInfoFile);
        return userMgrFactory.createUserManager();
    }

    /**
     * 停止FTP功能
     */
    public static void stopFTPServer() {
        if (null != ftpServer) {
            ftpServer.stop();
        }
    }

    /**
     * 负责监控FTPServer的运行状态
     * @author WangYanCheng
     * @version 2011-6-14
     */
    class FtpServerMonitor extends Thread {
        public boolean runFlag;
        private DefaultFtpServer defFtpServer;
        private FtpServerMonitor() {
            this.setDaemon(true);
            runFlag = true;
        }
        public boolean setFtpServer(FtpServer ftpServer) {
            defFtpServer = (ftpServer instanceof DefaultFtpServer) ? (DefaultFtpServer) ftpServer : null;
            return defFtpServer != null;
        }
        public void run() {
            while (runFlag) {
                if (null != defFtpServer) {
                    showUsers();
                    showActiveUser();
                    FtpStatistics ftpStatis = defFtpServer.getServerContext().getFtpStatistics();
                    int connNum = ftpStatis.getCurrentConnectionNumber();
                    int loginNum = ftpStatis.getCurrentLoginNumber();
                    System.out.println("当前连接数\t" + connNum + "\n当前登陆数\t" + loginNum);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 注册{@link Ftplet}事件监听
     * @param dfs {@link FtpServer}
     */
    private void registerFtplet(FtpServer ftpServer) {
        DefaultFtpServer dfs = (DefaultFtpServer) ftpServer;
        FtpServerContext serverCtx = dfs.getServerContext();
        ServerFtpStatistics serverFtpStat = (ServerFtpStatistics)serverCtx.getFtpStatistics();
        serverFtpStat.setFileObserver(new AbstractFileObserver(){
            @Override
            public void notifyUpload(FtpIoSession session, FtpFile file, long size) {
                System.out.println(session.getUser());
                //super.notifyUpload(session, file, size);
                System.out.println("监听到上传文件命令==>\n"/*.concat(session.getUserArgument())*/.concat("\n").concat(file.getAbsolutePath()).concat("\t").concat("" + file.getSize()).concat("\n" + size));
            }
        });
    }
    /**
     * 获当前在线(登陆)用户数量
     * <p>因为ftp用户管理这块要与应用系统模块集成，所以ftp服务器自身理论上来说是没有用户的</p>
     */
    public void showUsers() {
        if (null == ftpServerFactory) {
            return;
        }
        String[] userNames = null;
        try {
            userNames = ftpServerFactory.getUserManager().getAllUserNames();
        } catch (FtpException e) {
            e.printStackTrace();
        }
        if (userNames != null && userNames.length > 0) {
            for (String userName : userNames) {
                System.out.println("用户：\t" + userName);
            }
        }
    }
    /**
     * 取服务器当前活动用户数量
     */
    public void showActiveUser() {
        Map<String, Listener> ftpListener = ftpServerFactory.getListeners();
        String[] tmpStrArr = new String[ftpListener.size()];
        tmpStrArr = ftpListener.keySet().toArray(tmpStrArr);
        Listener tmpListener = null;
        for (String tmpStr : tmpStrArr) {
            tmpListener = ftpListener.get(tmpStr);
            System.out.println("当前上下文：".concat(tmpStr).concat("\t").concat("活动用户：\t" + tmpListener.getActiveSessions().size()));
        }
    }
    public void addUser(User user) {
    }
}
