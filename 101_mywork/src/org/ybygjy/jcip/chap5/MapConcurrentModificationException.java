package org.ybygjy.jcip.chap5;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证并发场景下对Map容器的迭代抛出ConcurrentModificationException异常
 * <p>1.早期的并发容器如HashTable</p>
 * @author WangYanCheng
 * @version 2014年10月19日
 */
public class MapConcurrentModificationException {
	private final Map<String, String> hashTableObj;
	/**
	 * 构造函数初使化
	 */
	public MapConcurrentModificationException() {
		this.hashTableObj = new HashMap<String, String>();
	}
	/**
	 * 测试入口
	 */
	public void doWork() {
		//创建多个线程负责写入
		for (int i = 0; i < 3; i++) {
			new Thread(){
				public void run() {
					while (true) {
						String key = String.valueOf(Math.random());
						hashTableObj.put(key, key);
						try {
							sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
		//创建多个线程负责迭代容器
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread() {
				public void run() {
					while (true) {
						System.out.println(hashTableObj.toString());
						/*
						Iterator<Map.Entry<String, String>> iterator = hashTableObj.entrySet().iterator();
						while (iterator.hasNext()) {
							Map.Entry<String, String> entry = iterator.next();
							System.out.println(entry.getKey() + ":" + entry.getValue());
						}
						*/
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			thread.start();
		}
	}
	/**
	 * 程序入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		MapConcurrentModificationException mmeInst = new MapConcurrentModificationException();
		mmeInst.doWork();
	}
}
