package org.ybygjy.basic.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 * SimpleSSL，负责SSL实践
 * @author WangYanCheng
 * @version 2011-5-30
 */
public class SimpleSSL {
    public void doWork(String url) {
        SSLServerSocketFactory ssFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket ssSocket = null;
        try {
            ssSocket = (SSLServerSocket) ssFactory.createServerSocket(80);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        int id = 1;
        while (true) {
            try {
                SSLSocket socket = (SSLSocket) ssSocket.accept();
System.out.println(socket.getInetAddress());
                OutputStream outStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outStream);
                URL urlObj = new URL(url);
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(urlObj.openStream()));
                String tmpStr = null;
                while ((tmpStr = buffReader.readLine()) != null) {
                    System.out.println(tmpStr);
                    printWriter.write(tmpStr);
                    printWriter.flush();
                }
                printWriter.close();
                buffReader.close();
                socket.close();
                String[] tmpArray = socket.getEnabledCipherSuites();
                for (String tmpStr1 : tmpArray) {
                    System.out.println(tmpStr1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        SimpleSSL simpleSSLInst = new SimpleSSL();
        simpleSSLInst.doWork("http://www.baidu.com");
    }
}
