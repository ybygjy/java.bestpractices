package org.ybygjy.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 深入理解jvm#堆溢出
 * @author WangYanCheng
 * @version 2016年8月15日
 */
public class HeapOOM {
	static class OOMObject{
	}
	public static void main(String[] args) {
		List<OOMObject> objList = new ArrayList<OOMObject>();
		while (true) {
			objList.add(new OOMObject());
		}
	}
}
