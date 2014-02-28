package org.ybygjy.pattern.complex.simduck.fact;

import org.ybygjy.pattern.complex.simduck.Quackable;

public abstract class AbstractDuckFactory {
    public abstract Quackable createMallardDuck();
    public abstract Quackable createRedheadDuck();
    public abstract Quackable createDuckCall();
    public abstract Quackable createRubberDuck();
}
