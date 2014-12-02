package org.ybygjy.basic.thinking.thread.collaborat;

/**
 * 餐厅中经典的生产者、消费者问题
 * @author WangYanCheng
 * @version 2010-10-8
 */
public class Restaurant {
    /** order */
    private Order order;
    /** synchronized lock */
    private static Object synLock = new Object();

    /**
     * @return the order
     */
    public Order getOrder() {
        synchronized (synLock) {
            return order;
        }
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Order order) {
        synchronized (synLock) {
            this.order = order;
        }
    }

    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Restaurant rest = new Restaurant();
        WaitPerson waitPerson = new WaitPerson(rest);
        Chef chef = new Chef(rest, waitPerson);
    }
}

/**
 * 订单
 * @author WangYanCheng
 * @version 2010-10-8
 */
class Order {
    /** 订单变量 */
    private static int i = 0;
    /** 订单数量 */
    private int count = i++;

    /**
     * Constructor
     */
    public Order() {
        if (count == 10) {
            System.out.println("Out of food, closing.");
            System.exit(0);
        }
    }

    @Override
    public String toString() {
        return "Order " + count;
    }
}

/**
 * 消费者
 * @author WangYanCheng
 * @version 2010-10-8
 */
class WaitPerson extends Thread {
    /** restaurant */
    private Restaurant rest;

    /**
     * Constructor
     * @param rest {@link Restaurant}
     */
    public WaitPerson(Restaurant rest) {
        this.rest = rest;
        start();
    }

    @Override
    public void run() {
        while (true) {
            if (rest.getOrder() == null) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException intrr) {
                        intrr.printStackTrace();
                    }
                }
                System.out.println("WaitPerson got " + rest.getOrder());
                rest.setOrder(null);
            }
        }
    }
}

/**
 * 生产者
 * @author WangYanCheng
 * @version 2010-10-8
 */
class Chef extends Thread {
    /** restaurant */
    private Restaurant rest;
    /** waitPerson */
    private WaitPerson waitPerson;

    /**
     * Constructor
     * @param rest {@link Restaurant}
     * @param waitP {@link WaitPerson}
     */
    public Chef(Restaurant rest, WaitPerson waitP) {
        this.rest = rest;
        this.waitPerson = waitP;
        start();
    }

    @Override
    public void run() {
        while (true) {
            if (rest.getOrder() == null) {
                rest.setOrder(new Order());
                System.out.println("Order up!");
                synchronized (waitPerson) {
                    waitPerson.notify();
                }
            }
            try {
                sleep(100);
            } catch (InterruptedException intrr) {
                intrr.printStackTrace();
            }
        }
    }
}
