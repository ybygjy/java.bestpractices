package org.ybygjy.mvn;
/**
 * HelloMaven
 * @author MLS
 */
public class HelloMaven {
	public void showInfo() {
		System.out.println("Hello Maven.");
	}
	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		new HelloMaven().showInfo();
	}
}
