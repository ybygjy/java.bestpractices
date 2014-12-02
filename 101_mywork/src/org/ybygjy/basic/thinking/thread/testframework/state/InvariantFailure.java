package org.ybygjy.basic.thinking.thread.testframework.state;

/**
 * 失败信息标记
 * @author WangYanCheng
 * @version 2010-9-30
 */
public class InvariantFailure implements InvariantState {
    /** inner value */
    private Object value;

    /**
     * Constructor
     * @param value value
     */
    public InvariantFailure(Object value) {
        this.value = value;
    }

    /**
     * getValue
     * @return value value
     */
    public Object getValue() {
        return this.value;
    }
}
