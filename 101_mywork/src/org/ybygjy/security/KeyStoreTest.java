package org.ybygjy.security;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Enumeration;

/**
 * KeyStoreTest 测试
 * @author WangYanCheng
 * @version 2013-9-27
 */
public class KeyStoreTest {
    private File keyStoreFile;
    private char[] storePassword;
    public KeyStoreTest() {
        char SEP = File.separatorChar;
        keyStoreFile = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security", /*"jssecacerts"*/"cacerts");
        storePassword = "changeit".toCharArray();
    }
    public void doWork() {
        KeyStore keyStore = getKeyStore();
        if (null == keyStore) {
            return;
        }
        doPrintAliases(keyStore);
    }
    /**
     * 打印出KeyStore存储的条目
     * @param keyStore
     */
    private void doPrintAliases(KeyStore keyStore) {
        try {
            Enumeration<String> aliasesEnum = keyStore.aliases();
            while (aliasesEnum.hasMoreElements()) {
                String tmpAliases = aliasesEnum.nextElement();
                if ("mapi.alipay.com-1".equals(tmpAliases)) {
                    System.out.println(aliasesEnum.nextElement());
                }
            }
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private KeyStore getKeyStore() {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream(keyStoreFile), storePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyStore;
    }
    public static void main(String[] args) {
        new KeyStoreTest().doWork();
    }
}
