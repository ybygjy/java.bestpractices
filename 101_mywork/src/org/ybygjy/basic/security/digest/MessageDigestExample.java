package org.ybygjy.basic.security.digest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.ybygjy.util.Base64;

import com.sun.jndi.toolkit.chars.BASE64Encoder;
import com.sun.mail.util.BASE64EncoderStream;

/**
 * 负责验证消息摘要包括摘要的创建、计算与读取
 * <p>首先创建消息摘要服务相关对象MessageDigest，其次是计算消息摘要也就是传入用于计算的明文，然后读取计算结果；</p>
 * @author WangYanCheng
 * @version 2011-7-4
 */
public class MessageDigestExample {
	/**十六进制*/
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
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
//        System.out.println("摘要模块支持信息：".concat(providerInfo));
        //计算摘要
        messageDigest.update(planText);
        //读取摘要
//        providerInfo = new String(messageDigest.digest(), "UTF-8");
        System.out.println(new String(encodeHex(messageDigest.digest())));
//        System.out.println("原始消息：".concat(new String(planText, "UTF-8")).concat("\n消息摘要：").concat(providerInfo));
    }

    /**
     * 二进制转十六进制字符组
     * @param data 二进制组
     * @return rtnCharArr
     */
    private String encodeHex(byte[] data) {
    	StringBuffer sbuf = new StringBuffer();
    	for (byte byteValue : data) {
    		sbuf.append(Integer.toHexString((0xF0 & byteValue)>>> 4));
    		sbuf.append(Integer.toHexString(0x0F & byteValue));
    	}
    	return sbuf.toString();
    }
    /**
     * 测试入口
     * @param args 参数列表
     * @throws UnsupportedEncodingException 未支持的字符集
     * @throws NoSuchAlgorithmException 未被支持的算法
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigestExample mdeInst = new MessageDigestExample();
        String srcPlain = "0f13970157d9c0d026126f375bf066bbaccountNo=6237000013790830168&accountType=1&applyNo=151609367235604021&bankCode=CCB&busiTypeId=HIGO&curId=CNY&customerName=中国建设银行&customerNo=MLS_D_00258228&customerType=0&merchantId=MLS_I_00000008&openAccName=中国建设银行&version=20131111";
//        String srcPlain = "ea559fdaeb5dbb7668436f87df16e38eaccountNo=6237000013790830168&accountType=1&applyNo=151609367235604021&bankCode=CCB&busiTypeId=HIGO&curId=CNY&customerName=中国建设银行&customerNo=MLS_D_00258228&customerType=0&merchantId=MLS_I_00000008&openAccName=中国建设银行&version=20131111";
        mdeInst.messageDigest4MD5(srcPlain.getBytes("UTF-8"));
    }
}
