package org.ybygjy.basic.thinking.thread.testframework;

import org.ybygjy.basic.thinking.thread.testframework.state.InvariantFailure;
import org.ybygjy.basic.thinking.thread.testframework.state.InvariantState;


/**
 * {@link Invariant} consumer
 * @author WangYanCheng
 * @version 2010-9-30
 */
public class InvariantWatcher extends Thread {
    /** invariant Instance */
    private Invariant invariant;

    /**
     * Constructor
     * @param invariant invariant
     */
    public InvariantWatcher(Invariant invariant) {
        this.invariant = invariant;
        setDaemon(true);
        start();
    }

    /**
     * Constructor
     * @param invariant invariant
     * @param timeOut timeOut
     */
    public InvariantWatcher(Invariant invariant, final int timeOut) {
        this(invariant);
        new Timeout(timeOut, "Timed out without violating invariant.");
    }

    @Override
    public void run() {
        while (true) {
            InvariantState state = invariant.invariant();
            if (state instanceof InvariantFailure) {
                System.out.println("Invariant violated: " + ((InvariantFailure) state).getValue());
                System.exit(0);
            }
        }
    }
}
