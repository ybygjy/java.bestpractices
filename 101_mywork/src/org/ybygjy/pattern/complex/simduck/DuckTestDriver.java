package org.ybygjy.pattern.complex.simduck;

import org.ybygjy.pattern.complex.simduck.fact.AbstractDuckFactory;
import org.ybygjy.pattern.complex.simduck.fact.CountingDuckFactory;
import org.ybygjy.pattern.complex.simduck.impl.Flock;
import org.ybygjy.pattern.complex.simduck.impl.GooseAdapter;
import org.ybygjy.pattern.complex.simduck.impl.QuackCounter;

/**
 * 负责测试驱动
 * @author WangYanCheng
 * @version 2011-1-24
 */
public class DuckTestDriver {
    public static void main(String[] args) {
        new DuckTestDriver().simulate(new CountingDuckFactory());
    }

    void simulate(AbstractDuckFactory adfInst) {
        Quackable mallardDuck = adfInst.createMallardDuck();
        Quackable redheadDuck = adfInst.createRedheadDuck();
        redheadDuck.registerObserver(new Observer() {
            public void update(Quackable duck) {
                System.out.println("监听：" + duck);
            }
        });
        Quackable duckCall = adfInst.createDuckCall();
        Quackable rubberDuck = adfInst.createRubberDuck();
        Quackable gooseAdapter = new GooseAdapter(new Goose());
        Flock duckFlock = new Flock();
        duckFlock.add(mallardDuck);
        duckFlock.add(redheadDuck);
        Quackable mallardDuckOne = adfInst.createMallardDuck();
        Quackable mallardDuckTwo = adfInst.createMallardDuck();
        Quackable mallardDuckThree = adfInst.createMallardDuck();
        Quackable mallardDuckFour = adfInst.createMallardDuck();
        Flock mallardDuckFlock = new Flock();
        mallardDuckFlock.add(mallardDuckOne);
        mallardDuckFlock.add(mallardDuckTwo);
        mallardDuckFlock.add(mallardDuckThree);
        mallardDuckFlock.add(mallardDuckFour);
        duckFlock.add(mallardDuckFlock);
        simulate(gooseAdapter);
        simulate(duckFlock);
        System.out.println("NumberOfQuack:" + QuackCounter.getNumberOfQuack());
    }

    void simulate(Quackable quackInst) {
        quackInst.quack();
    }
}
