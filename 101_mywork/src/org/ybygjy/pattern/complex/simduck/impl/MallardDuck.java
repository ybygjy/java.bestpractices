package org.ybygjy.pattern.complex.simduck.impl;

import org.ybygjy.pattern.complex.simduck.Observable;
import org.ybygjy.pattern.complex.simduck.Observer;
import org.ybygjy.pattern.complex.simduck.Quackable;

public class MallardDuck implements Quackable {
    private Observable observable;

    public MallardDuck() {
        this.observable = new Observable(this);
    }

    public void quack() {
        System.out.println("MallardDuck");
        this.notifyObservers();
    }

    public void notifyObservers() {
        observable.notifyObservers();
    }

    public void registerObserver(Observer observer) {
        observable.registerObserver(observer);
    }
}
