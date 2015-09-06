package org.ybygjy.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Asynchronized I/O
 * @author WangYanCheng
 * @version 2015年9月7日
 */
public class AIOServerTest {
	public static void main(String[] args) {
		final InnerAIOServer aioServer = new InnerAIOServer();
		final InnerAIOClient aioClient = new InnerAIOClient(); 
		new Thread(new Runnable() {
			public void run() {
				aioServer.doWork("localhost", 8888);
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				aioClient.doWork("localhost", 8888);
				aioClient.write((byte) 10);
			}
		}).start();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		System.out.println(byteBuffer.get());
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
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	public void write(byte bytee) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(32);
		System.out.println("byteBuffer#1=>" + byteBuffer.toString());
		byteBuffer.put(byteBuffer);
		byteBuffer.flip();
		System.out.println("byteBuffer#2=>" + byteBuffer.toString());
		this.ascInst.write(byteBuffer);
	}
}
