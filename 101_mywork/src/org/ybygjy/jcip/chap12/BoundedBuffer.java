package org.ybygjy.jcip.chap12;

import java.util.concurrent.Semaphore;

/**
 * 基于信号量的有界缓存
 * <p>1.使用{@link Semaphore}对象计数做为队列的边界</p>
 * <p>2.</p>
 * @author WangYanCheng
 * @version 2014年11月6日
 */
public class BoundedBuffer<E> {
	/** 队列可用数据项数量*/
	private Semaphore availableItems;
	/** 队列可用空间*/
	private Semaphore availableSpaces;
	/** 定义泛型数组用于存储数据项*/
	private final E[] items;
	/** 入列位置*/
	private int putPosition = 0;
	/** 出列位置*/
	private int takePosition = 0;
	/**
	 * 构造函数
	 * @param capacity 队列长度
	 */
	public BoundedBuffer(int capacity) {
		this.availableItems = new Semaphore(0);
		this.availableSpaces = new Semaphore(capacity);
		this.items = ((E[]) new Object[capacity]);
	}
	/**
	 * 队列是否为空
	 * @return rtnBoolean true/false
	 */
	public boolean isEmpty() {
		return this.availableItems.availablePermits() == 0;
	}
	/**
	 * 队列是否已满
	 * @return rtnBoolean true/false
	 */
	public boolean isFull() {
		return this.availableSpaces.availablePermits() == 0;
	}
	/**
	 * 入队
	 * @param x
	 * @throws InterruptedException
	 */
	public void put(E x) throws InterruptedException {
		this.availableSpaces.acquire();
		this.doInsert(x);
		this.availableItems.release();
	}
	/**
	 * 出队
	 * @return
	 * @throws InterruptedException
	 */
	public E take() throws InterruptedException {
		this.availableItems.acquire();
		E item = doExtract();
		this.availableSpaces.release();
		return item;
	}
	/**
	 * 入队
	 * @param x
	 */
	private synchronized void doInsert(E x) {
		int i = this.putPosition;
		items[i] = x;
		this.putPosition = (++i == this.items.length) ? 0 : i;
	}
	/**
	 * 出队
	 * @return rtnObj
	 */
	private synchronized E doExtract() {
		int i = this.takePosition;
		E x = this.items[i];
		items[i] = null;
		this.takePosition = (++i == items.length) ? 0 : i;
		return x;
	}
}
