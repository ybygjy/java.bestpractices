package org.ybygjy.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Socket文件传输客户端
 * <p>1.建立与服务端的连接</p>
 * <p>2.传输数据到服务端</p>
 * @author WangYanCheng
 * @version 2016年2月29日
 */
public class SocketBinaryTransferClient {
	private static String SERV_ADDR = "localhost";
	private static int SERV_PORT = 80;
	private static String filePath = "/Users/MLS/2002_mpay_tag/payserver-2.4.44-002/pay-admin/target/pay-admin.war";
	public void doWork() {
		Socket socket = null;
		BufferedReader brs = null;
		DataOutputStream bos = null;
		try {
			socket = new Socket(SERV_ADDR, SERV_PORT);
System.out.println("建立与服务器的连接。。。");
			brs = new BufferedReader(new InputStreamReader(socket.getInputStream()));
System.out.println(brs.readLine());
System.out.println("传输内容。。。");
			File fileInst = new File(filePath);
			bos = new DataOutputStream(socket.getOutputStream());
			bos.writeLong(fileInst.length());
			bos.flush();
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileInst));
			byte[] buff = new byte[1024];
			int flag = -1;
			while ((flag = bis.read(buff)) != -1) {
				bos.write(buff, 0, flag);
				bos.flush();
			}
			bos.close();
			bis.close();
			System.out.println("写完了::" + brs.readLine());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != brs) {
				try {
					brs.close();
				} catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
			if (null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 执行入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		new SocketBinaryTransferClient().doWork();
	}
}
