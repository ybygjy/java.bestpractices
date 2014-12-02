package org.ybygjy.basic.thinking.thread.testframework;

import org.ybygjy.basic.thinking.thread.testframework.state.InvariantState;

/**
 * 定义测试规则声明
 * @author WangYanCheng
 * @version 2010-9-30
 */
public interface Invariant {
    /**
     * 测试规则
     * @return {@link InvariantState}
     */
    InvariantState invariant();
}
