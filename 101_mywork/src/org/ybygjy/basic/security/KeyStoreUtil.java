package org.ybygjy.basic.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * ∑‚◊∞KeyStoreª˘¥°≤Ÿ◊˜
 * @author WangYanCheng
 * @version 2011-6-3
 */
public class KeyStoreUtil {
    /**
     * º”‘ÿ√‹‘øø‚
     * @param keyStoreFile √‹‘øŒƒº˛
     * @param keyStorePWD √‹‘ø√‹¬Î
     * @return keystore/null
     */
    public static KeyStore loadKeyStore(File keyStoreFile, char[] keyStorePWD) {
        FileInputStream fins = null;
        try {
            fins = new FileInputStream(keyStoreFile);
            KeyStore keyStore = KeyStore.getInstance("jks");
            keyStore.load(fins, keyStorePWD);
            fins.close();
            fins = null;
            return keyStore;
        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        } catch (CertificateException ce) {
            ce.printStackTrace();
        } catch (KeyStoreException kse) {
            kse.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (null != fins) {
                try {
                    fins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * getKeyManager
     * @param keyStore √ÿ‘øø‚
     * @param password √ÿ‘øø‚√‹¬Î
     * @return keyMgr/null
     */
    public static KeyManager getKeyManager(KeyStore keyStore, char[] password) {
        KeyManagerFactory keyMgrFactory = null;
        try {
            keyMgrFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyMgrFactory.init(keyStore, password);
            return keyMgrFactory.getKeyManagers()[0];
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * getTrustKeyManager
     * @param keyStore √ÿ‘øø‚
     * @return trustMgr/null
     */
    public static TrustManager getTrustKeyManager(KeyStore keyStore) {
        TrustManagerFactory trustMgrFactory;
        try {
            trustMgrFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustMgrFactory.init(keyStore);
            return trustMgrFactory.getTrustManagers()[0];
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
}
