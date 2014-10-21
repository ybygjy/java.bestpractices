package org.ybygjy.jcip.chap5;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * 验证并发场景下对Map容器的迭代抛出ConcurrentModificationException异常
 * <p>1.早期的并发容器如HashTable，锁的粒度在类实例级别，所有公开的方法共用一个锁</p>
 * <p>2.</p>
 * HashTable与HashMap在toString方法中的实现逻辑不同,从HashMap的继承结构来看，HashMap复用了上层AbstractMap的toString方法，而HashTable实现了自己的toString方法。
 * HashMap典型Iterator模式所以会有ConcurrentModificationException。
 * HashTable也是典型的Iterator模式，但HashTable对外暴露的公共方法是加锁的，如put、toString，
 * 但唯有entrySet、values方法未直接加锁，这两个方法使用的是同步容器加锁方式，同步容器的作用是为非线程安全的容器增加并发安全的支持（类似代理模式为特定类增加IOC/AOP支持一样）。
 * 但在HashTable中的entrySet、values方法调用同步容器工厂对外发布的是新构造的对象，分别是Entry和Collection，但需要注意这两个新实例使用的锁与HashTable实例的锁一致。
 * @author WangYanCheng
 * @version 2014年10月20日
 */
public class MapConcurrentModificationException {
	/**实例变量*/
	private final Map<String, String> containerObj;
	/**
	 * 构造函数初使化
	 */
	public MapConcurrentModificationException() {
		this.containerObj = new Hashtable<String, String>();
		//this.containerObj = new HashMap<String, String>();
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
						containerObj.put(key, key);
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
						System.out.println(containerObj.toString());
						//HashTable容器的toString是加锁的，在此通过sleep出让cpu延迟对容器的迭代，这期间写线程会写入新的元素，后续对容器的迭代复现ConcurrentModificationException的机率就更大了。
						try {
							sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Iterator<Map.Entry<String, String>> iterator = containerObj.entrySet().iterator();
						while (iterator.hasNext()) {
							Map.Entry<String, String> entry = iterator.next();
							System.out.println(entry.getKey() + ":" + entry.getValue());
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
