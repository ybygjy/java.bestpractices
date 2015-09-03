package org.ybygjy.nio;

import java.lang.reflect.Field;

/**
 * 监控DirectBuffer内存情况
 * @author WangYanCheng
 * @version 2015年9月3日
 */
public class DirectMonitor {
	public void doMonitor() {
		Class clazz = null;
		try {
			clazz = Class.forName("java.nio.Bits");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		Field maxMemory = null;
		Field reservedMemory = null;
		try {
			maxMemory = clazz.getDeclaredField("maxMemory");
			maxMemory.setAccessible(true);
			reservedMemory = clazz.getDeclaredField("reservedMemory");
			reservedMemory.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		synchronized(clazz) {
			try {
				Long maxMemoryValue = maxMemory.getLong(clazz);
System.out.println(maxMemoryValue);
				Long reservedMemoryValue = reservedMemory.getLong(clazz);
System.out.println(reservedMemoryValue);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		DirectMonitor directMonitor = new DirectMonitor();
		directMonitor.doMonitor();
	}
}
