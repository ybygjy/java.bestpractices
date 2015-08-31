package org.ybygjy.util;

/**
 * IPv4地址与整型类型互转
 * @author WangYanCheng
 * @version 2015年8月16日
 */
public class IPConvert {
	public static long ip2Long(String ipAddr) {
		String[] tmpIPData = ipAddr.split("\\.");
		int result = ((toUnsignedInt(Integer.parseInt(tmpIPData[0]))<< 24)
				| (toUnsignedInt(Integer.parseInt(tmpIPData[1])) << 16) 
				| (toUnsignedInt(Integer.parseInt(tmpIPData[2])) << 8) 
				| (toUnsignedInt(Integer.parseInt(tmpIPData[3]))));
		//无符号转换
		long rtnValue = toUnsignedLong(result);
		return rtnValue;
	}
	/**
	 * Int无符号转换
	 * @param intVal
	 * @return intVal
	 */
	private static int toUnsignedInt(int intVal) {
		int result = intVal & 0x7fff;
		if (intVal < 0) {
			result = result | 0x8000;
		}
		return result;
	}
	/**
	 * Long无符号转换
	 * @param longVal
	 * @return longVal
	 */
	private static long toUnsignedLong(long longVal) {
		//注意十六进制表示的变量类型为Long(L)
		long result = longVal & 0x07fffffffL;
		if (longVal < 0) {
			result = result | 0x080000000L;
		}
		return result;
	}
	/**
	 * 给定数值转换为对应的IP地址
	 * @param longValue
	 * @return
	 */
	public static String long2IP(long longValue) {
		int ip1 = (int) (longValue >> 24) & 0xff;
		int ip2 = (int) (longValue >> 16) & 0xff;
		int ip3 = (int) (longValue >> 8) & 0xff;
		int ip4 = (int) longValue & 0xff;
		return ip1 + "." + ip2 + "." + ip3 + "." + ip4;
	}
	/**
	 * 测试入口
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) {
		String[] ipArr = {"182.118.0.0", "10.1.7.106", "222.223.254.254"};
		for(int i = 0; i < ipArr.length; i++) {
			String ipAddr = ipArr[i];
			Long ipNumber = IPConvert.ip2Long(ipAddr);
			String ipStr = IPConvert.long2IP(ipNumber);
			System.out.println(ipNumber + "=>" + ipStr);
		}
	}
}
