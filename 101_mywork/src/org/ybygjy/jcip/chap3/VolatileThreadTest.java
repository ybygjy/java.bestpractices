package org.ybygjy.jcip.chap3;


/**
 * 负责构造一个volatile多线程任务处理环境
 * <p>1.1、单写多读</p>
 * <p>1.2、没有锁</p>
 * <p>1.3、volatile确保变量的更新操作对其它线程可见</p>
 * <p>1.4、volatile变量不会缓存在寄存器或其它处理器不可见的地方</p>
 * <p>1.5、让人沉思的一句话(volatile变量对可见性的影响比volatile变量本身更为重要)</p>
 * 注意
 * <p>2.1、多线程读的问题，volatile只保证变量的更新操作在无需特殊同步的情况下对其它线程可见</p>
 * @author WangYanCheng
 * @version 2014-7-17
 */
public class VolatileThreadTest {
    public void doWork() {
        final VolatileVO volatileVO = new VolatileVO();
        VolatileWriteThread writeThread = new VolatileWriteThread(volatileVO);
        VolatileReadThread readThread1 = new VolatileReadThread(volatileVO);
        VolatileReadThread readThread2 = new VolatileReadThread(volatileVO);
        writeThread.start();
        readThread1.start();
        readThread2.start();
    }
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        VolatileThreadTest volatileThreadTest = new VolatileThreadTest();
        volatileThreadTest.doWork();
    }
    /**
     * 负责{@link VolatileVO} 写操作
     * @author WangYanCheng
     * @version 2014-7-17
     */
    class VolatileWriteThread extends Thread {
        private VolatileVO volatileVo;
        public VolatileWriteThread(VolatileVO volatileVO) {
            this.volatileVo = volatileVO;
        }
        @Override
        public void run() {
            while (true) {
                int i = volatileVo.getStep() + 1;
                this.volatileVo.setStep(i);
                if (i%500 == 0) {
                    break;
                }
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 负责{@link VolatileVO} 读操作
     * @author WangYanCheng
     * @version 2014-7-17
     */
    class VolatileReadThread extends Thread {
        private VolatileVO volatileVo;
        public VolatileReadThread(VolatileVO volatileVo) {
            this.volatileVo = volatileVo;
        }

        @Override
        public void run() {
            while (true) {
                String tmpValue = getName() + "#" + volatileVo.getStep();
                System.out.println(tmpValue);
                if (volatileVo.getStep()%500==0) {
                    break;
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
/**
 * 定义数据实体
 * @author WangYanCheng
 * @version 2014-7-17
 */
class VolatileVO {
	/** 使用volatile确保并发环境下值的可见性*/
    private volatile int step;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
