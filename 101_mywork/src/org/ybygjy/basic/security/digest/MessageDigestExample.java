package org.ybygjy.basic.security.digest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 负责验证消息摘要包括摘要的创建、计算与读取
 * <p>首先创建消息摘要服务相关对象MessageDigest，其次是计算消息摘要也就是传入用于计算的明文，然后读取计算结果；</p>
 * @author WangYanCheng
 * @version 2011-7-4
 */
public class MessageDigestExample {
    /**
     * 利用MD5算法生成消息摘要
     * @param planText 原始消息
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public void messageDigest4MD5(byte[] planText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //消息摘要模块支持信息
        String providerInfo = messageDigest.getProvider().getInfo();
        System.out.println("摘要模块支持信息：".concat(providerInfo));
        //计算摘要
        messageDigest.update(planText);
        //读取摘要
        providerInfo = new String(messageDigest.digest(), "UTF-8");
        System.out.println("原始消息：".concat(new String(planText, "UTF-8")).concat("\n消息摘要：").concat(providerInfo));
    }
    /**
     * 测试入口
     * @param args 参数列表
     * @throws UnsupportedEncodingException 未支持的字符集
     * @throws NoSuchAlgorithmException 未被支持的算法
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigestExample mdeInst = new MessageDigestExample();
        String srcPlain = "I'm a Software Engineer.";
        mdeInst.messageDigest4MD5(srcPlain.getBytes());
    }
}
