package org.ybygjy;

/**
 * 验证私有静态变量是实例级的，每个实例中该变量是不一样的
 * @author WangYanCheng
 * @version 2015年2月6日
 */
public class PrivateStaticTest {
	private final Person[] personArr = new Person[10];
	public PrivateStaticTest() {
		for (int i = 0; i < this.personArr.length; i++) {
			this.personArr[i] = new Person("P_" + i, "P_" + i);
		}
	}
	/**
	 * 逻辑入口
	 */
	private void doWork() {
		for(int i = 0; i < 50; i++) {
			int index = (personArr.length-1)%(i+1);
			Person personObj = personArr[index];
System.out.println(personObj);
			new Thread(new ThreadClient(personObj)).start();
		}
	}
	/**
	 * 测试入口
	 * @param args
	 */
	public static void main(String[] args) {
		PrivateStaticTest pstInst = new PrivateStaticTest();
		pstInst.doWork();
	}
}
class ThreadClient implements Runnable {
	private Person personObj;
	public ThreadClient(Person personObj) {
		this.personObj = personObj;
	}
	public void run() {
		int count = 1;
		while (true) {
			this.personObj.setName(Thread.currentThread().getName() + "_" + count++);
			this.personObj.setIdNum(Thread.currentThread().getName() + "_" + count);
			//sleep出让cpu时间，但不会释放持有的锁
			try {
				Thread.sleep((long)(Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (1000 == count) {
System.out.println(Thread.currentThread().getName() + " finished!");
				break;
			}
		}
	}
}
/**
 * 设计该类的实例被用来进行多线程共享
 * @author WangYanCheng
 * @version 2015年2月6日
 */
class Person {
	private static final Object pubLock = new Object();
	private final Object innerLock = new Object();
	private String name;
	private String idNum;
	public Person(String name, String idNum) {
		this.name = name;
		this.idNum = idNum;
	}
	public void setName(String name) {
		synchronized(pubLock) {
			synchronized(innerLock) {
				this.name = name;
			}
		}
	}
	public String getName() {
		synchronized(pubLock) {
			synchronized(innerLock) {
				return this.name;
			}
		}
	}
	public void setIdNum(String idNum) {
		synchronized(pubLock) {
			synchronized(innerLock) {
				this.idNum = idNum;
			}
		}
	}
	public String getIdNum() {
		synchronized(pubLock) {
			synchronized(innerLock) {
				return this.idNum;
			}
		}
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", idNum=" + idNum + ",pubLock" + pubLock.toString() + ",innerLock" + innerLock + "]";
	}	
}
