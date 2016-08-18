package org.ybygjy.jvm;

/**
 * 深入理解jvm#栈溢出
 * @author WangYanCheng
 * @version 2016年8月15日
 */
public class VMStackSOF {
	private int stackLength = 1;
	public void stackLeak() {
		stackLength++;
		stackLeak();
	}
	public static void main(String[] args) throws Throwable {
		VMStackSOF vmStackSOF = new VMStackSOF();
		try {
			vmStackSOF.stackLeak();
		} catch (Throwable e) {
			System.err.println("stack length:" + vmStackSOF.stackLength);
			throw e;
		}
		System.out.println(vmStackSOF.stackLength);
	}
}
