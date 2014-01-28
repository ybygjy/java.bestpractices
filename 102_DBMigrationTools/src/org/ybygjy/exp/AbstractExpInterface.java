package org.ybygjy.exp;

import org.ybygjy.ExpInterface;
import org.ybygjy.TestEnv;

public abstract class AbstractExpInterface implements ExpInterface {
    /**资源管理器*/
    protected TestEnv testEnvInst;
    /**
     * Constructor
     * @param testEnvInst {@link TestEnv}
     */
    public AbstractExpInterface(TestEnv testEnvInst) {
        this.testEnvInst = testEnvInst;
    }
}
