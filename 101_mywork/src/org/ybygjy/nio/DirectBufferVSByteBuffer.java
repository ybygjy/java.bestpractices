package org.ybygjy.nio;

import java.nio.ByteBuffer;

/**
 * DirectBuffer比ByteBuffer更加接近操作系统
 * 前者比后者速度更快，但前者构造分配空间或销毁时耗时较大
 * @author WangYanCheng
 * @version 2015年9月2日
 */
public class DirectBufferVSByteBuffer {
	private int testCount = 100000;
	/**
	 * DirectBuffer测试
	 */
	public void directBufferTest() {
		long beginTime = System.currentTimeMillis();
		ByteBuffer buffer = ByteBuffer.allocateDirect(this.testCount * 32);
		for(int i = 0; i < this.testCount; i++) {
			buffer.putInt(i);
		}
		buffer.flip();
		for(int i = 0; i < this.testCount; i++) {
			buffer.getInt();
		}
		buffer.clear();
		long endTime = System.currentTimeMillis();
System.out.println(endTime - beginTime);
		beginTime = System.currentTimeMillis();
		for (int i = 0; i < this.testCount; i++) {
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(this.testCount);			
			byteBuffer.clear();
		}
		endTime = System.currentTimeMillis();
		System.out.println(endTime - beginTime);
	}
	public void byteBufferTest() {
		long beginTime = System.currentTimeMillis();
		ByteBuffer byteBuffer = ByteBuffer.allocate(this.testCount * 32);
		for (int i = 0; i< this.testCount; i++) {
			byteBuffer.putInt(i);
		}
		for (int i = 0; i< this.testCount; i++) {
			byteBuffer.getInt();
		}
		long endTime = System.currentTimeMillis();
System.out.println(endTime - beginTime);
		beginTime = System.currentTimeMillis();
		for (int i = 0; i < this.testCount; i++) {
			byteBuffer = ByteBuffer.allocate(this.testCount);
			byteBuffer.clear();
		}
		endTime = System.currentTimeMillis();
System.out.println(endTime - beginTime);
	}
	public static void main(String[] args) {
		DirectBufferVSByteBuffer buffTestInst = new DirectBufferVSByteBuffer();
		buffTestInst.directBufferTest();
		buffTestInst.byteBufferTest();
	}
}
