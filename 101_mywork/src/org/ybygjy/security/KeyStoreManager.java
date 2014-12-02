package org.ybygjy.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableEntryException;
import java.security.KeyStore.Entry;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

/**
 * KeyStore管理
 * <p>1、KeyStore库自身的管理</p>
 * <p>2、条目的增加、删除</p>
 * @author WangYanCheng
 * @version 2013-10-7
 */
public class KeyStoreManager {
    private KeyStore keyStore;
    private File keyStoreFile;
    private String keyStorePass;

    /**
     * 构造方法
     * @param keyStoreFile KeyStore文件路径
     * @param keyStorePass KeyStore文件密码
     */
    public KeyStoreManager(File keyStoreFile, String keyStorePass) {
        this.keyStoreFile = keyStoreFile;
        this.keyStorePass = keyStorePass;
        this.keyStore = loadKeyStore(keyStoreFile, keyStorePass);
    }

    /**
     * 加载KeyStore文件内容
     * @param keyStoreFile 文件实体
     * @param keyStorePass 密码信息
     * @return rtnKeyStore {@link KeyStore}
     */
    private KeyStore loadKeyStore(File keyStoreFile, String keyStorePass) {
        KeyStore keyStore = null;
        FileInputStream fisInst = null;
        try {
            fisInst = new FileInputStream(keyStoreFile);
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(fisInst, keyStorePass.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fisInst) {
                try {
                    fisInst.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return keyStore;
    }

    /**
     * 添加信任证书
     * @param trustFile 证书文件
     */
    public void addTrustItem(File trustFile) {
        Certificate certificate = createCertificate(trustFile);
        KeyStore.TrustedCertificateEntry tceInst = new KeyStore.TrustedCertificateEntry(certificate);
        try {
            this.keyStore.setEntry("com.alipay." + (certificate.hashCode()), tceInst, null);
            this.restoreKeyStore(this.keyStore);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除给定别名的条目
     * @param alias
     */
    public void deleteTrustItem(String alias) {
        try {
            this.keyStore.deleteEntry(alias);
            restoreKeyStore(this.keyStore);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建证书实例
     * @return certInst {@link Certificate}
     */
    private Certificate createCertificate(File trustFile) {
        Certificate rtnCertificate = null;
        FileInputStream fisInst = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            fisInst = new FileInputStream(trustFile);
            rtnCertificate = certificateFactory.generateCertificate(fisInst);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fisInst) {
                try {
                    fisInst.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnCertificate;
    }

    /**
     * 打印当前JVM可选安全实现列表
     */
    public void showSecurityProvider() {
        Provider[] securityProviderArray = Security.getProviders();
        for (Provider provider : securityProviderArray) {
            System.out.println(provider.toString());
        }
    }

    /**
     * 打印当前KeyStore条件信息
     */
    private void showItemList() {
        Enumeration<String> aliasEnum = null;
        try {
            aliasEnum = keyStore.aliases();
            while (aliasEnum.hasMoreElements()) {
                String aliasName = aliasEnum.nextElement();
                System.out.println("条目Begin==>" + aliasName);
                Entry entry = keyStore.getEntry(aliasName, null);
                if (entry instanceof KeyStore.SecretKeyEntry) {
                    KeyStore.SecretKeyEntry kskeInst = (KeyStore.SecretKeyEntry) entry;
                    System.out.println(kskeInst.toString());
                } else if (entry instanceof KeyStore.TrustedCertificateEntry) {
                    KeyStore.TrustedCertificateEntry kskeInst = (KeyStore.TrustedCertificateEntry) entry;
                    System.out.println(kskeInst.toString());
                } else if (entry instanceof KeyStore.PrivateKeyEntry) {
                    KeyStore.PrivateKeyEntry kskeInst = (KeyStore.PrivateKeyEntry) entry;
                    System.out.println(kskeInst.toString());
                }
                System.out.println("条目End==>" + aliasName);
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转储KeyStore
     * @param keyStore {@link KeyStore}
     */
    private void restoreKeyStore(KeyStore keyStore) {
        FileOutputStream fous = null;
        try {
            fous = new FileOutputStream(keyStoreFile);
            this.keyStore.store(fous, keyStorePass.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fous) {
                try {
                    fous.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 逻辑入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        File keyStoreFile = new File("D:\\dev\\trust.jks");
        File certFile = new File("D:\\dev\\alipay.com.cer");
        String keyStorePass = "1";
        KeyStoreManager kmInst = new KeyStoreManager(keyStoreFile, keyStorePass);
        // 添加信任证书
        kmInst.addTrustItem(certFile);
        // 删除信任证书
        //kmInst.deleteTrustItem("org.ybygjy.autocert_22697208");
        // 打印证书库条目信息
        kmInst.showItemList();
    }
}
