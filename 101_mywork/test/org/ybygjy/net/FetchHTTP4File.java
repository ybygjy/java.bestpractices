package org.ybygjy.net;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FetchHTTP4File {
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private AtomicInteger lineCount = new AtomicInteger();
	public void doWork() {
		//读文件
		File fileObj = new File("/Users/MLS/HelloGroup.txt");
		BufferedReader fileReader = null;
		int lineNums = 1;
		try {
			fileReader = new BufferedReader(new FileReader(fileObj));
			String fileLine = null;
			while ((fileLine = fileReader.readLine()) != null) {
				final String tmpURL = fileLine;
				lineNums++;
				executorService.execute(new Runnable() {
					public void run() {
						try {
							URL urlObj = new URL(tmpURL);
							URLConnection connObj = urlObj.openConnection();
							connObj.setConnectTimeout(10 * 1000);
							BufferedInputStream bisInst = new BufferedInputStream(connObj.getInputStream());
							byte[] buff = new byte[1024];
							int flag = -1;
							System.out.println(tmpURL);
							while((flag = bisInst.read(buff)) != -1) {
//								System.out.print(flag);
//								if (flag % 10 == 0) {
//									System.out.println();
//								}
//								System.out.println(new String(buff, 0, flag, Charset.forName("UTF-8")));
							}
							FetchHTTP4File.this.lineCount.incrementAndGet();
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			};
			executorService.shutdown();
			while(!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
				System.out.println("处理了＝》" + this.lineCount.get());
			}
			System.out.println("线程池关闭!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		new FetchHTTP4File().doWork();
	}
}
