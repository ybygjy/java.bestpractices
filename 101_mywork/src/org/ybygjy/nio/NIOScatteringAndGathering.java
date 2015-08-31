package org.ybygjy.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * NIO散射和聚集
 * @author WangYanCheng
 * @version 2015年8月31日
 */
public class NIOScatteringAndGathering {
	/**
	 * 创建文件
	 * @param filePath
	 * @return
	 */
	public File createFiles(String filePath) {
		File fileInst = new File(filePath);
		if (!fileInst.exists()) {
			try {
				fileInst.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getCause());
			}
		}
		return fileInst;
	}
	/**
	 * 写文件
	 * @param fileInst
	 */
	public void writeFiles(File fileInst) {
		ByteBuffer byteBuff = ByteBuffer.wrap("Java性能优化".getBytes(Charset.forName("UTF-8")));
		ByteBuffer authorBuffer = ByteBuffer.wrap("谁才是作者".getBytes(Charset.forName("UTF-8")));
		ByteBuffer[] buffers = new ByteBuffer[]{byteBuff, authorBuffer};
		FileOutputStream fous = null;
		FileChannel fileChannel = null;
		try {
			fous = new FileOutputStream(fileInst);
			fileChannel = fous.getChannel();
			fileChannel.write(buffers);
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
	}
	/**
	 * 读文件
	 * @param fileInst
	 */
	public void readFiles(File fileInst) {
		FileInputStream fins = null;
		ByteBuffer[] buffers = new ByteBuffer[]{
			ByteBuffer.allocate(1024),
			ByteBuffer.allocate(1024)
		};
		try {
			fins = new FileInputStream(fileInst);
			FileChannel fileChannel = fins.getChannel();
			long readBytesNums = fileChannel.read(buffers);
			StringBuffer sbuf = new StringBuffer();
			if (readBytesNums > 0) {
				for(int i = 0; i < buffers.length; i++) {
					ByteBuffer byteBuff = buffers[i];
					if (byteBuff.hasArray()) {
						String tmpStr = new String(byteBuff.array(), "UTF-8");
						sbuf.append(tmpStr);
					}
				}
			}
			System.out.println(sbuf);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (null != fins) {
				try {
					fins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 测试入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		String filePath = "/Users/MLS/HelloNIOScattingAndGathering";
		NIOScatteringAndGathering nioScattiAndGathiInst = new NIOScatteringAndGathering();
		File fileInst = nioScattiAndGathiInst.createFiles(filePath);
		nioScattiAndGathiInst.writeFiles(fileInst);
		nioScattiAndGathiInst.readFiles(fileInst);
	}
}
