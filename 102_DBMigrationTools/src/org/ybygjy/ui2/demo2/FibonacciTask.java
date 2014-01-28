package org.ybygjy.ui2.demo2;

import java.util.Observable;
import java.util.Observer;

/**
 * FibonacciTask
 * @author WangYanCheng
 * @version 2012-11-1
 */
public class FibonacciTask extends Observable implements Runnable {
    /**{@link FibBean}*/
    private FibBean fibBean;
    /**
     * Constructor
     * @param observer {@link Observer}
     * @param fibBean fibBean
     */
    public FibonacciTask(Observer observer, FibBean fibBean) {
        this.fibBean = fibBean;
        addObserver(observer);
        new Thread(this).start();
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        this.fibBean.setFibValue(calc(this.fibBean.getFibTerm()));
        setChanged();
        notifyObservers(this.fibBean);
    }

    /**
     * ¼ÆËãÂß¼­(µÝ¹é)
     * @param fibNum ÏîÊý
     * @return rtnV
     */
    private long calc(long fibNum) {
        if (fibNum <= 2) {
            return 1;
        }
        return (calc(fibNum - 1) + calc(fibNum - 2));
    }
}
