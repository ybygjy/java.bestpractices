package org.ybygjy.nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 3种I/O方式对比
 * <p>1.传统I/O</p>
 * <p>2.基于Byte的I/O</p>
 * <p>3.基于内存映射的NIO</p>
 * @author WangYanCheng
 * @version 2015年9月1日
 */
public class NIOComparator {
	/**循环次数*/
	private int circlNums = 4000000;
	/**
	 * 传统I/O
	 * @param fileInst
	 */
	public void ioMethod(File fileInst) {
		long beginTime = System.currentTimeMillis();
		long endTime = 0;
		DataOutputStream dosInst = null;
		try {
			dosInst = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileInst)));
			for(int i = 0; i < this.circlNums; i++) {
				dosInst.writeInt(i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (null != dosInst) {
				try {
					dosInst.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		endTime = System.currentTimeMillis();
System.out.println((endTime - beginTime));
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileInst)));
			for (int i = 0; i < this.circlNums; i++) {
				dis.readInt();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		endTime = System.currentTimeMillis();
System.out.println((endTime - beginTime));
	}
	public void byteMethod(File fileInst) {
		long beginTime = System.currentTimeMillis();
		long endTime = 0L;
		FileOutputStream fous = null;
		try {
			fous = new FileOutputStream(fileInst);
			FileChannel fileChannel = fous.getChannel();
			ByteBuffer byteBuff = ByteBuffer.allocate(this.circlNums * 4);
			for (int i = 0; i < this.circlNums; i++) {
				byteBuff.put(this.int2Bytes(i));
			}
			byteBuff.flip();
			fileChannel.write(byteBuff);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (null != fous) {
				try {
					fous.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		endTime = System.currentTimeMillis();
System.out.println((endTime - beginTime));
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileInst);
			FileChannel fileChannel = fis.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(this.circlNums * 4);
			fileChannel.read(byteBuffer);
			fileChannel.close();
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				this.byte2Int(byteBuffer.get(),byteBuffer.get(),byteBuffer.get(),byteBuffer.get());
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		endTime = System.currentTimeMillis();
System.out.println((endTime - beginTime));
	}
	public void mapMethod(File fileInst) {
		long beginTime = System.currentTimeMillis();
		long endTime = 0L;
		RandomAccessFile rafInst = null;
		try {
			rafInst = new RandomAccessFile(fileInst, "rw");
			FileChannel fileChannel = rafInst.getChannel();
			ByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, this.circlNums * 4);
			for (int i = 0; i < this.circlNums; i++) {
				byteBuffer.put(this.int2Bytes(i));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (rafInst != null) {
				try {
					rafInst.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		endTime = System.currentTimeMillis();
System.out.println((endTime - beginTime));
		FileInputStream fisInst = null;
		try {
			fisInst = new FileInputStream(fileInst);
			FileChannel fileChannel = fisInst.getChannel();
			ByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
			while (byteBuffer.hasRemaining()) {
				this.byte2Int(byteBuffer.get(), byteBuffer.get(), byteBuffer.get(), byteBuffer.get());
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (fisInst != null) {
				try {
					fisInst.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		endTime = System.currentTimeMillis();
System.out.println((endTime - beginTime));
	}
	/**
	 * 给定字节转换成int
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 */
	private int byte2Int(byte b, byte c, byte d, byte e) {
		int rtnValue = 0;
		rtnValue = (b & 0xff << 24) | ((c & 0xff) << 16) | ((d & 0xff) << 8) | ((e & 0xff));
		return rtnValue;
	}
	/**
	 * 给定int转换为字节数组
	 * @param src
	 * @return
	 */
	public byte[] int2Bytes(int src) {
		byte[] rtnBytes = new byte[4];
		rtnBytes[3] = (byte) (src & 0xff);//低8位
		rtnBytes[2] = (byte) ((src >> 8) & 0xff);//次低8位
		rtnBytes[1] = (byte) ((src >> 16) & 0xff);//次高8位
		rtnBytes[0] = (byte) ((src >> 24) & 0xff);//高8位
		return rtnBytes;
	}
	/**
	 * 列表
	 * @param args
	 */
	public static void main(String[] args) {
		File fileInst = new File("/Users/MLS/HelloNIO");
		NIOComparator niocInst = new NIOComparator();
		niocInst.ioMethod(fileInst);
		niocInst.byteMethod(fileInst);
		niocInst.mapMethod(fileInst);
	}
}
