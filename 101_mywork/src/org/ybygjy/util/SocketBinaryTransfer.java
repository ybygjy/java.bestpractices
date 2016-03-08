package org.ybygjy.util;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 文件传输
 * @author WangYanCheng
 * @version 2016年2月29日
 */
public class SocketBinaryTransfer {
	private static int SERV_PORT = 80;
	private static String tmpFilePath = "/tmp/";
	private static transient int clients = 0;
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 5, 2, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
	public void doWork() {
		ServerSocket serverSocket = null;
		DataInputStream bis = null;
		BufferedWriter bws = null;
		try {
			serverSocket = new ServerSocket(SERV_PORT);
			System.out.println("启动服务监听。。。" + SERV_PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("建立Socket连接" + (clients++) + "=>" + socket.toString());
				threadPool.execute(new InnerProcSocketThread(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != bis) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != bws) {
				try {
					bws.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != serverSocket) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class InnerProcSocketThread implements Runnable {
		private Socket socket = null;
		public InnerProcSocketThread(Socket socket) {
			this.socket = socket;
		}
		public void run() {
			DataInputStream dis = null;
			PrintWriter pw = null;
			try {
				dis = new DataInputStream(socket.getInputStream());
				pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				String filePath = tmpFilePath + (int)(Math.random() * 10000) + "tmpfile";
				pw.write("接入文件传输服务器::" + filePath + System.lineSeparator());
				pw.flush();
				//读文件内容长度
				long fileLength = dis.readLong();
System.out.println("内容长度：" + fileLength);
				if (fileLength <= 0) {
					System.out.println("内容长度为零！");
					return;
				}
				FileOutputStream fous = new FileOutputStream(new File(filePath));
				byte[] buff = new byte[1024];
				long len = fileLength;
				int flag = -1;
				while ((flag = dis.read(buff)) != -1) {
					fous.write(buff, 0, flag);
					System.out.println("完成" + ((int)(((float)(len - flag) / fileLength) * 100)) + "%");
					len = len - flag;
				}
				fous.flush();
				fous.close();
				pw.write("文件传输完成::" + filePath + System.lineSeparator());
				pw.flush();
				pw.close();
				System.out.println("处理完成："+filePath);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (null != socket) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public static void main(String[] args) {
		new SocketBinaryTransfer().doWork();
	}
}
