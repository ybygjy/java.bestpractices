package org.ybygjy.pattern.complex.simduck;

import java.util.ArrayList;
import java.util.List;

public class Observable implements QuackObservable {
    private List<Observer> observerArray = new ArrayList<Observer>();
    private Quackable duck;

    public Observable(Quackable duck) {
        this.duck = duck;
    }

    public void notifyObservers() {
        for (Observer ob : observerArray) {
            ob.update(duck);
        }
    }

    public void registerObserver(Observer observer) {
        observerArray.add(observer);
    }

}
