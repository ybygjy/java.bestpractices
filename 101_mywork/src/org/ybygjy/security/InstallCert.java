package org.ybygjy.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 自动安装SSL/TLS网站安全证书，主要处理流程
 * <p>1、取本地默认安全配置文件证书库</p>
 * <p>2、使用SSLContext利用证书侦听机制转储证书至本地信任证书库</p>
 * @author WangYanCheng
 * @version 2014-4-14
 */
public class InstallCert {
    private String host;
    private int port;
    private char[] passphrase;
    public InstallCert(String host, int port, char[] passphrase) {
        super();
        this.host = host;
        this.port = port;
        this.passphrase = passphrase;
    }
    public void doWork() throws Exception {
        File cacertsFile = loadCacertsFile(passphrase);
System.out.println("Load Key Store File=>" + cacertsFile + "...");
        KeyStore ks = loadKeyStore(cacertsFile, passphrase);
        SSLContext context = SSLContext.getInstance("TLS");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
        context.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory sslSocketFactory = context.getSocketFactory();
System.out.println("Openning connection to " + host + ":" + port + "...");
        SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(host, port);
        socket.setSoTimeout(10000);
        try {
System.out.println("Starting SSL handshake...");
            socket.startHandshake();
            socket.close();
System.out.println("No errors, certificate is already trusted.");
        } catch(SSLException ssle) {
            ssle.printStackTrace(System.out);
        }
        X509Certificate[] chain = tm.getChains();
        if (null == chain) {
            System.out.println("Could not obtain server certificate chain.");
            return;
        }
        dumpX509Certificate(chain, ks);
    }
    /**
     * 转储证书
     * @param chain 证书链
     * @param ks 证书库
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     * @throws KeyStoreException 
     * @throws CertificateException 
     */
    private void dumpX509Certificate(X509Certificate[] chain, KeyStore ks) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println("Server sent" + chain.length + " certificate(s):");
        System.out.println();
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        MessageDigest md5  = MessageDigest.getInstance("MD5");
        for (int i = 0; i < chain.length; i++) {
            X509Certificate cert = chain[i];
            System.out.println(" " + (i + 1) + "Subject " + cert.getSubjectDN());
            System.out.println("    Issuer " + cert.getIssuerDN());
            sha1.update(cert.getEncoded());
            System.out.println("    sha1   " + toHexString(sha1.digest()));
            md5.update(cert.getEncoded());
            System.out.println("    md5    " + toHexString(md5.digest()));
            System.out.println();
        }
        System.out.println("Enter certificate to add to trusted keystore or 'q' to quit:[1]");
        String line = reader.readLine().trim();
        int k;
        try {
            k = (line.length() == 0) ? 0 : Integer.parseInt(line) - 1;
        } catch (NumberFormatException e) {
            System.out.println("KeyStore not changed.");
            return;
        }
        X509Certificate cert = chain[k];
        String alias = host + "-" + (k + 1);
        ks.setCertificateEntry(alias, cert);
        OutputStream out = new FileOutputStream("jssecacerts");
        ks.store(out, passphrase);
        out.close();
        System.out.println();
        System.out.println(cert);
        System.out.println();
        System.out.println("Added certificate to keystore: 'jssecacerts' using alias '" + alias + "'");
    }
    /**
     * 十六进制转换
     * @param digest 字节串
     * @return rtnStr 十六进制字串
     */
    private String toHexString(byte[] digest) {
        char[] HEXDIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder sbud = new StringBuilder(digest.length * 3);
        for (int b : digest) {
            b &= 0xff;
            sbud.append(HEXDIGITS[b >> 4]);
            sbud.append(HEXDIGITS[b & 15]);
            sbud.append(' ');
        }
        return sbud.toString();
    }
    /**
     * 加载证书库
     * @param cacertsFile 证书文件
     * @param passphrase 密码串
     * @return keyStore 证书库
     */
    private KeyStore loadKeyStore(File cacertsFile, char[] passphrase) throws Exception {
        InputStream ins = new FileInputStream(cacertsFile);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(ins, passphrase);
        return ks;
    }
    /**
     * 确认证书库文件
     * @param passphrase 证书
     * @return rtnFile 证书库文件
     */
    private File loadCacertsFile(char[] passphrase) {
        File file = new File("jssecacerts");
        if (file.isFile() == false) {
            char SEP = File.separatorChar;
            File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security");
            file = new File(dir, file.getName());
            if (file.isFile() == false) {
                file = new File(dir, "cacerts");
            }
        }
        return file;
    }
    public static void main(String[] args) {
        String host = "https://www.github.com";
        int port = 443;
        char[] passphrase = "changeit".toCharArray();
        InstallCert installCert = new InstallCert(host, port, passphrase);
        try {
            installCert.doWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/**
 * 实现X509证书的验证接口，验证过程中存储了证书链的实体对象，供外部转储到本地信任证书库
 * @author WangYanCheng
 * @version 2014-4-14
 */
class SavingTrustManager implements X509TrustManager {
    private final X509TrustManager tm;
    private X509Certificate[] chain;
    public SavingTrustManager(X509TrustManager tm) {
        this.tm = tm;
    }
    @Override
    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
        throws CertificateException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
        throws CertificateException {
        this.chain = chain;
        tm.checkServerTrusted(chain, authType);
    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        throw new UnsupportedOperationException();
    }
    public X509Certificate[] getChains() {
        return chain;
    }
}