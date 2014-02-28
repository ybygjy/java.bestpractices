package org.ybygjy.util.svn;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.novell.ldap.util.Base64;

public final class SecurityUtil {
    private SecurityUtil(){}

    private static final byte[] iv = new byte[] { 82, 22, 50, 44, -16, 124, -40, -114, -87, -40, 37, 23, -56, 23, -33, 75 };
    private static final String KEY = "Ninestar123";
    private static final String CHARSET_NAME = "UTF-8"; 
    
    
    public static String encrypt(String str){
        if (!StringUtil.hasLength(str)){
            throw new NullPointerException("str������Ϊ�գ�");
        }
        
        try {
            byte[] contentBytes = str.getBytes(CHARSET_NAME);
            byte[] keyBytes = KEY.getBytes(CHARSET_NAME);
            byte[] encryptBytes = runEncrypt(contentBytes,keyBytes);
            String encodeString= Base64.encode(encryptBytes);//.getEncodeString(encryptBytes);
            return encodeString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public static String decrypt(String str){
        if (!StringUtil.hasLength(str)){
            throw new NullPointerException("str������Ϊ�գ�");
        }
        try {
            byte[] keyBytes = KEY.getBytes(CHARSET_NAME);
            byte[] contentBytes = Base64.decode(str);//.getDecodeBytes(str);
            
            byte[] decryptBytes = runDecrypt(contentBytes,keyBytes);
            
            String decryptString = new String(decryptBytes, CHARSET_NAME);
            return decryptString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }  
    
    
    private static byte[] runEncrypt(byte[] contentBytes,byte[] keyBytes) {
        return run(Cipher.ENCRYPT_MODE, contentBytes, keyBytes);
    }

    private static byte[] runDecrypt(byte[] contentBytes,byte[] keyBytes) {
        return run(Cipher.DECRYPT_MODE, contentBytes, keyBytes);
    }   
    
    private static byte[] run(int cryptMode,byte[] contentBytes,byte[] keyBytes) {
        try {
            final String algorithmName = "AES";
            final String algorithmMode = "CBC";
            final String algorithmFillMode = "PKCS5Padding";
            final String separator = "/";
            final String mode = algorithmName + separator + algorithmMode + separator + algorithmFillMode;
            KeyGenerator kgen = KeyGenerator.getInstance(algorithmName);
            kgen.init(128, new SecureRandom(keyBytes));
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            SecretKey key = kgen.generateKey();
            Cipher cipher = Cipher.getInstance(mode);            
            cipher.init(cryptMode, key, paramSpec);
            return cipher.doFinal(contentBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }       
    }    
    
    
    public static void main(String[] args){
        String encrypt = encrypt(SvnConstants.PASSWORD);
        System.out.println(encrypt);        
        String decrypt = decrypt(encrypt);
        System.out.println(decrypt(encrypt));
        System.out.println(SvnConstants.PASSWORD.equals(decrypt));
    }
}
