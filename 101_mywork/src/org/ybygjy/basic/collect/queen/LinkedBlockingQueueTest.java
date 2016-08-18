package org.ybygjy.basic.collect.queen;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueTest {
	public static void main(String[] args) {
		LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>();
		try {
	        linkedBlockingQueue.put("Hello");
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
		try {
	        String result = linkedBlockingQueue.take();
	        System.out.println(result);
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
		linkedBlockingQueue.add("A");
		String headValue = linkedBlockingQueue.remove();
		System.out.println(headValue);
		linkedBlockingQueue.offer("B");
		String elementStr = linkedBlockingQueue.element();
		System.out.println(elementStr);
		linkedBlockingQueue.remove();
		boolean rtnBool = linkedBlockingQueue.offer("B");
		System.out.println(rtnBool);
		headValue = linkedBlockingQueue.peek();
		System.out.println(headValue);
		headValue = linkedBlockingQueue.poll();
		System.out.println(headValue);
		try {
	        linkedBlockingQueue.put("D");
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
		try {
	        headValue = linkedBlockingQueue.take();
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
	}
}
