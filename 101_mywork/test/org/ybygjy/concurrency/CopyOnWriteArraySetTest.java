package org.ybygjy.concurrency;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Java并发容器测试
 * @author WangYanCheng
 * @version 2014年10月29日
 */
public class CopyOnWriteArraySetTest {
	public static void main(String[] args) {
		CopyOnWriteArraySet<InnerObject> copyOnWriteArraySetObj = new CopyOnWriteArraySet<InnerObject>();
		copyOnWriteArraySetObj.add(new InnerObject("A", "A_Type"));
		copyOnWriteArraySetObj.add(new InnerObject("B", "B_Type"));
		new ReadThread(copyOnWriteArraySetObj).start();
		new WriteThread(copyOnWriteArraySetObj).start();
		new WriteThread(copyOnWriteArraySetObj).start();
	}
	static class ReadThread extends Thread {
		private CopyOnWriteArraySet<InnerObject> copyOnWriteArraySetObj;
		public ReadThread(CopyOnWriteArraySet<InnerObject> copyOnWriteArraySetObj) {
			this.copyOnWriteArraySetObj = copyOnWriteArraySetObj;
		}
		public void run() {
			while (true) {
				StringBuffer sbuf = new StringBuffer();
				sbuf.append("[");
				for (Iterator<InnerObject> iterator = this.copyOnWriteArraySetObj.iterator(); iterator.hasNext();) {
					InnerObject innerObject = iterator.next();
					sbuf.append(innerObject.getObjectName() + ":" + innerObject.getObjectType()).append(";");
				}
				sbuf.append("]");
				System.out.println(sbuf);
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 写线程
	 * @author WangYanCheng
	 * @version 2014年10月29日
	 */
	static class WriteThread extends Thread {
		private CopyOnWriteArraySet<InnerObject> copyOnWriteArraySetObj;
		public WriteThread(CopyOnWriteArraySet<InnerObject> copyOnWriteArraySetObj) {
			this.copyOnWriteArraySetObj = copyOnWriteArraySetObj;
		}
		public void run() {
			while (true) {
				String tmpValue = String.valueOf((int) (Math.random() * 1000));
				if (this.copyOnWriteArraySetObj.add(new InnerObject(getName() + "_" + tmpValue, tmpValue)) ) {
					try {
						sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
class InnerObject {
	private String objectName;
	private String objectType;
	public InnerObject(String objectName, String objectType) {
		super();
		this.objectName = objectName;
		this.objectType = objectType;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}
