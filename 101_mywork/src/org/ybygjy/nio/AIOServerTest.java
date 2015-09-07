package org.ybygjy.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Asynchronized I/O
 * @author WangYanCheng
 * @version 2015年9月7日
 */
public class AIOServerTest {
	/**运行标识*/
	private static volatile boolean running = true;
	/**
	 * 测试入口
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		final InnerAIOServer aioServer = new InnerAIOServer();
		final InnerAIOClient aioClient = new InnerAIOClient(); 
		final CountDownLatch cdlInst = new CountDownLatch(1);
		new Thread(new Runnable() {
			public void run() {
				cdlInst.countDown();
				aioServer.doWork("127.0.0.1", 8888);
			}
		}).start();
		for(int i = 0; i < 100; i++) {
			try {
				cdlInst.await();
				new Thread(new Runnable() {
					public void run() {
						aioClient.doWork("127.0.0.1", 8888);
					}
				}).start();
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		Runtime.getRuntime().addShutdownHook(new Thread() {
//			public void run() {
//				synchronized(AIOServerTest.class) {
//					running = false;
//					AIOServerTest.class.notify();
//				}
//			}
//		});
//		synchronized(AIOServerTest.class) {
//			while (running) {
//				System.out.println("等待退出!");
//				try {
//					AIOServerTest.class.wait();
//					System.out.println("退出了！");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}
}
/**
 * AsynchronousServerSocket
 * @author WangYanCheng
 * @version 2015年9月7日
 */
class InnerAIOServer {
	public void doWork(String host, int port) {
		final AsynchronousServerSocketChannel asscInst;
		try {
			asscInst = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(InetAddress.getByName(host), port));
			asscInst.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
				@Override
				public void completed(AsynchronousSocketChannel result, Void attachment) {
					asscInst.accept(null, this);
					handle(result);
				}
				
				@Override
				public void failed(Throwable exc, Void attachment) {
				}
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void handle(AsynchronousSocketChannel ascInst) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		try {
			ascInst.read(byteBuffer).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		byteBuffer.flip();
		System.out.println("server=>" + byteBuffer.getDouble());
	}	
}
/**
 * AsynchronousSocketChannel 客户端
 * @author WangYanCheng
 * @version 2015年9月7日
 */
class InnerAIOClient {
	private AsynchronousSocketChannel ascInst;
	public void doWork(String host,int port) {
		try {
			this.ascInst = AsynchronousSocketChannel.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Future<?> future = this.ascInst.connect(new InetSocketAddress(host, port));
		try {
			future.get();
			this.write(Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	public void write(double bytee) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(32);
		byteBuffer.putDouble(bytee);
		byteBuffer.flip();
		this.ascInst.write(byteBuffer);
	}
}
