package org.ybygjy.basic.security.digest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

/**
 * 消息认证码验证
 * <p>如果密钥被用作消息摘要生成过程的一部分，则将该算法称为消息认证码</p>
 * <p>二种消息认证码算法的支持：HMAC/SHA-1、HMAC/MD5</p>
 * @author WangYanCheng
 * @version 2011-7-4
 */
public class MessageAuthenticationCodeExample {
    /**
     * HMAC/MD5算法产生消息验证码
     * @param srcPlain 明文消息串
     * @throws UnsupportedEncodingException 未被支持的字符集
     * @throws NoSuchAlgorithmException 未被支持的算法
     * @throws InvalidKeyException 非法的key值对象
     */
    public void mac4MD5(String srcPlain) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
        //算法支持信息
        String sysInfo = keyGenerator.getProvider().getInfo();
System.out.println("算法支持信息：".concat(sysInfo));
        //利用HmacMD5计算Key
        SecretKey secretMD5Key = keyGenerator.generateKey();
System.out.println("HmacMD5计算的Key：".concat(secretMD5Key.toString()));
        //创建消息认证码服务对象
        Mac mac = Mac.getInstance("HmacMD5");
        byte[] srcPlainByte = srcPlain.getBytes("UTF-8");
        //初始化、计算消息认证码
        mac.init(secretMD5Key);
        mac.update(srcPlainByte);
        //算法支持信息
        sysInfo = mac.getProvider().getInfo();
System.out.println("算法支持信息：".concat(sysInfo));
        //读取结果
        String resultStr = new String(mac.doFinal(), "utf-8");
        System.out.println("原始消息：".concat(srcPlain).concat("\n消息认证码：").concat(resultStr));
    }
    /**
     * 测试入口
     * @param args 参数列表
     * @throws InvalidKeyException 非法的Key值对象
     * @throws UnsupportedEncodingException 未被支持的字符集
     * @throws NoSuchAlgorithmException 未被支持的算法
     */
    public static void main(String[] args) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageAuthenticationCodeExample maceInst = new MessageAuthenticationCodeExample();
        String srcPlain = "I'm a Software Engineer.";
        maceInst.mac4MD5(srcPlain);
    }
}
