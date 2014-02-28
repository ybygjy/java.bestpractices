package org.ybygjy.pattern.proxy.rmistate.state;

import java.rmi.RemoteException;
import java.util.Random;

import org.ybygjy.pattern.proxy.rmistate.GumballMachine;
import org.ybygjy.pattern.proxy.rmistate.IState;
/**
 * hasQuarterState
 * @author WangYanCheng
 * @version 2010-12-24
 */
public class HasQuarterState implements IState {
    /**
     * serialized
     */
    private static final long serialVersionUID = -355598204330324125L;
    /**gumballInst*/
    private transient GumballMachine gumballInst;
    /**random winner*/
    private Random random;
    /**
     * constructor
     * @param gumballInst gumballInst
     */
    public HasQuarterState(GumballMachine gumballInst) {
        this.gumballInst = gumballInst;
        this.random = new Random(System.currentTimeMillis());
    }

    /**
     * {@inheritDoc}
     */
    public void dispense() {
        System.out.println("没有糖果可用");
    }
    /**
     * {@inheritDoc}
     */
    public void ejectQuarter() {
        System.out.println("退回钱币.");
        gumballInst.setCurrState(gumballInst.getNoQuarterState());
    }
    /**
     * {@inheritDoc}
     */
    public void insertQuarter() {
        System.out.println("You can't insert another quarter.");
    }
    /**
     * {@inheritDoc}
     */
    public void turnCrank() {
        System.out.println("You turned.");
        int winner = random.nextInt(10);
        try {
            if (winner == 0 && gumballInst.getCount() > 1) {
                gumballInst.setCurrState(gumballInst.getWinnerState());
            } else {
                gumballInst.setCurrState(gumballInst.getSoldState());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
