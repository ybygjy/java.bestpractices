package org.ybygjy.basic.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

import org.apache.ftpserver.FtpServerConfigurationException;

/**
 * 证书相关主题学习
 * @author WangYanCheng
 * @version 2011-6-1
 */
public class KeyStoreTest {
    private String keyStoreType = null;

    public String getDefKeyStoreType() {
        return (KeyStore.getDefaultType());
    }
    /**
     * 加载证书
     * @param storeFile
     * @param storeType
     * @param storePass
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public KeyStore loadStore(File storeFile, String storeType, String storePass) throws IOException, GeneralSecurityException {
        InputStream fin = null;
        try {
            if (storeFile.exists()) {
                System.out.println("Trying to load store from file");
                fin = new FileInputStream(storeFile);
            } else {
                System.out.println("Trying to load store from classpath");
                fin = getClass().getClassLoader().getResourceAsStream(storeFile.getPath());

                if (fin == null) {
                    throw new FtpServerConfigurationException("Key store could not be loaded from "
                        + storeFile.getPath());
                }
            }
            KeyStore store = KeyStore.getInstance(storeType);
            store.load(fin, storePass.toCharArray());
            return store;
        } finally {
            if (fin != null) {
                fin.close();
            }
        }
    }

    public static void main(String[] args) {
        File keyStoreFile = new File("D:\\work\\workspace\\mywork\\webRoot\\WEB-INF\\classes\\org\\ybygjy\\ftp\\ftpserver.jks");
        KeyStoreTest kstInst = new KeyStoreTest();
        try {
            kstInst.loadStore(keyStoreFile, kstInst.getDefKeyStoreType(), "123456");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}
