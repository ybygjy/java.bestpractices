package org.ybygjy.util;

import java.io.UnsupportedEncodingException;

public class URLDecoder {
	public static void main(String[] args) {
		String tmpStr = "busiTypeId=HIGO&remark=200077-%E6%97%A0%E6%B3%95%E8%8E%B7%E5%8F%96%E9%93%B6%E8%A1%8C%E5%8D%A1%E5%9C%B0%E5%9F%9F%E4%BF%A1%E6%81%AF&status=FAIL&HMAC=f880e7b388de57ef793317c9cddbb30a&customerNo=MLS_D_00226289&merchantId=MLS_I_00000008&version=20131111";
		try {
			tmpStr = java.net.URLDecoder.decode(tmpStr, "utf-8");
//			System.out.println(tmpStr);
			String[] tmpArr = tmpStr.split("&");
			for (String str : tmpArr) {
				System.out.println(str);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
//http://payadmin.meilishuo.com/api/merchant/bindCard.do?accountNo=348061284828&accountType=0&applyNo=156525207506394925&bankCode=BOC&bankName=中国银行&busiTypeId=HIGO&city=北京市&curId=CNY&customerName=北京尚加壹时尚贸易有限公司&customerNo=MLS_D_00222939&customerType=0&merchantId=MLS_I_00000008&openAccName=北京尚加壹时尚贸易有限公司&province=北京市&version=20131111&HMAC=f7347c31e93d4b3f7bd67a7250c99286
