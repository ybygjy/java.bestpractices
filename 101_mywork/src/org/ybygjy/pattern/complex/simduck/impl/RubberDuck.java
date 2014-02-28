package org.ybygjy.pattern.complex.simduck.impl;

import org.ybygjy.pattern.complex.simduck.Observable;
import org.ybygjy.pattern.complex.simduck.Observer;
import org.ybygjy.pattern.complex.simduck.Quackable;

public class RubberDuck implements Quackable {
    private Observable observable;
    public RubberDuck() {
        this.observable = new Observable(this);
    }
    public void quack() {
        System.out.println("RubberDuck..");
        notifyObservers();
    }
    public void notifyObservers() {
        this.observable.notifyObservers();
    }
    public void registerObserver(Observer observer) {
        this.observable.registerObserver(observer);
    }

}
