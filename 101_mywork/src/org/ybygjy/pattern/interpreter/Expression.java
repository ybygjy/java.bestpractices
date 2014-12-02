package org.ybygjy.pattern.interpreter;

/**
 * 表达式的抽象
 * @author WangYanCheng
 * @version 2013-2-18
 */
public abstract class Expression {
    public abstract boolean interpret(Context ctx);
    public abstract boolean equals(Object o);
    public abstract int hashCode();
    public abstract String toString();
}
