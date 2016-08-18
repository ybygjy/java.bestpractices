package org.ybygjy.basic;

import java.util.Calendar;
import java.util.Date;

public class HelloWorld {
	public static void main(String[] args) {
		for (EnumCustomsCode ecc : EnumCustomsCode.values()) {
			System.out.println(ecc.getCode() + ":" + ecc.getValue() + ":" + ecc.name());
		}
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		System.out.println(calendar.getTime());
		calendar.add(Calendar.MINUTE, -2880);
		System.out.println(calendar.getTime());
	}
}
enum  EnumCustomsCode {
	HANGZHOU("***网络科技有限公司","PT15050***"),
	ZHENGZHOU("郑州海关",""),
	GUANGZHOU("**（香港）有限公司","PTE5100141015000000*"),
	CHONGQING("重庆海关","P20150004*"),
	NINGBO("***","109*"),
	SHENZHEN("深圳海关","");
    private String value;
    private String code;

    public String getValue() {
        return value;
    }

    public void setValue(String name) {
        this.value = name;
    }
    

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	EnumCustomsCode(String value,String code) {
        this.value = value;
        this.code=code;
    }
    
}
