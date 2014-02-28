package org.ybygjy.pattern.complex.simduck.fact;

import org.ybygjy.pattern.complex.simduck.Quackable;
import org.ybygjy.pattern.complex.simduck.impl.DuckCall;
import org.ybygjy.pattern.complex.simduck.impl.MallardDuck;
import org.ybygjy.pattern.complex.simduck.impl.QuackCounter;
import org.ybygjy.pattern.complex.simduck.impl.RedheadDuck;
import org.ybygjy.pattern.complex.simduck.impl.RubberDuck;

public class CountingDuckFactory extends AbstractDuckFactory {

    @Override
    public Quackable createDuckCall() {
        // TODO Auto-generated method stub
        return new QuackCounter(new DuckCall());
    }

    @Override
    public Quackable createMallardDuck() {
        // TODO Auto-generated method stub
        return new QuackCounter(new MallardDuck());
    }

    @Override
    public Quackable createRedheadDuck() {
        // TODO Auto-generated method stub
        return new QuackCounter(new RedheadDuck());
    }

    @Override
    public Quackable createRubberDuck() {
        // TODO Auto-generated method stub
        return new QuackCounter(new RubberDuck());
    }
}
