package org.ybygjy.basic;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 异常概念的学习
 * <p>
 * 1、自定义受检异常
 * <p>
 * 2、自定义非受检异常
 * <p>
 * 3、受检与非受检异常的转换
 * @author WangYanCheng
 * @version 2012-11-15
 */
public class CauseTest {
    public void doWork() {
        doTestUncheckedException();
    }

    public void doTestCheckedException() throws CheckedException {
        throw new CheckedException("checked Exception", null);
    }

    public void doTestUncheckedException() {
        try {
            doTestCheckedException();
        } catch (CheckedException e) {
            throw new UnCheckedException(e);
        }
    }
    public static void main(String[] args) {
        new CauseTest().doWork();
    }
}

class CheckedException extends Exception {
    public CheckedException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public CheckedException(Throwable cause) {
        super(cause);
    }
}

class UnCheckedException extends RuntimeException {
    public UnCheckedException(Throwable cause) {
        super(cause);
    }
}
