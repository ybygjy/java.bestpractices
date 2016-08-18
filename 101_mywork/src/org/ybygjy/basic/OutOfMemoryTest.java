package org.ybygjy.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存溢出
 * @author WangYanCheng
 * @version 2016年7月25日
 */
public class OutOfMemoryTest {
	/**
	 * StackOverflowOverflow
	 */
	public void stackOverflow() {
		this.stackOverflow();
	}
	/**
	 * OutOfMemory
	 */
	public void outOfMemory() {
		List<Integer[]> tmpList = new ArrayList<Integer[]>();
		while(true) {
			tmpList.add(new Integer[1024*1024]);
			try {
	            Thread.sleep(10);
            } catch (InterruptedException e) {
	            e.printStackTrace();
            }
		}
	}
	public static void main(String[] args) {
		OutOfMemoryTest ofmtInst = new OutOfMemoryTest();
//		ofmtInst.stackOverflow();
		ofmtInst.outOfMemory();
	}
}
