package org.ybygjy.basic.collect.queen;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTest {
	public static void main(String[] args) {
		ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(1, false);
		arrayBlockingQueue.add("A");
		String resultValue = arrayBlockingQueue.element();
		System.out.println(resultValue);
		resultValue = arrayBlockingQueue.poll();
		System.out.println(resultValue);
		try {
	        arrayBlockingQueue.put("B");
	        arrayBlockingQueue.put("C");
        } catch (InterruptedException e) {
	        e.printStackTrace();
        }
	}
}
