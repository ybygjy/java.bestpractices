package org.ybygjy.jcip.chap15;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 非阻塞的链表
 * @author WangYanCheng
 * @version 2014-12-03
 */
public class LinkQueueTest {
	/**
	 * 测试入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		ConcurrentLinkedQueue<Object> linkedQueue = new ConcurrentLinkedQueue<Object>();
		linkedQueue.add("A");
		linkedQueue.add("B");
		linkedQueue.add("C");
		System.out.println(Arrays.toString(linkedQueue.toArray()));
	}
}
class LinkedQueue<E> {
	private static class Node<E> {
		final E item;
		final AtomicReference<Node<E>> next;
		public Node(E node, Node<E> next) {
			this.item = node;
			this.next = new AtomicReference<Node<E>>(next);
		}
	}
	private final Node<E> dummy = new Node<E>(null, null);
	private final AtomicReference<Node<E>> head = new AtomicReference<Node<E>>(dummy);
	private final AtomicReference<Node<E>> tail = new AtomicReference<Node<E>>(dummy);
	/**
	 * 非阻塞算法中的插入算法
	 * @param item
	 * @return
	 */
	public boolean put(E item) {
		Node<E> nextNode = new Node<E>(item, null);
		while (true) {
			//取尾节点为当前节点
			Node<E> currTail = tail.get();
			//当前节点的下一个节点
			Node<E> tailNext = currTail.next.get();
			if (currTail == tail.get()) {
				//节点状态不稳定,推进尾节点
				if (tailNext != null) {
					//修正尾节点
					tail.compareAndSet(currTail, tailNext);
				} else {
					if (currTail.next.compareAndSet(null, nextNode)) {
						tail.compareAndSet(currTail, nextNode);
						return true;
					}
				}
			}
		}
	}
	/**
	 * 非阻塞算法中的出队算法
	 * @return
	 */
	public E poll() {
		while (true) {
			//取头节点
			Node<E> currHead = this.head.get();
			Node<E> headNext = currHead.next.get();
			//头节点等于尾节点
			if(currHead == tail.get() && headNext == null) {
				return null;
			}
			if (currHead == this.head.get()) {
				//取头节点内容
				E headCtx = currHead.item;
				if (head.compareAndSet(currHead, headNext)) {
					return headCtx;
				}
			}
		}
	}
}
