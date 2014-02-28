package org.ybygjy.pattern.complex.simduck.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ybygjy.pattern.complex.simduck.Observer;
import org.ybygjy.pattern.complex.simduck.Quackable;

public class Flock implements Quackable {
    private List<Quackable> quackers = new ArrayList<Quackable>();

    public void add(Quackable quack) {
        quackers.add(quack);
    }

    public void quack() {
        System.out.println("Flock Begin:");
        for (Iterator<Quackable> iterator = quackers.iterator(); iterator.hasNext();) {
            iterator.next().quack();
        }
        System.out.println("Flock End;");
    }

    public void notifyObservers() {
    }

    public void registerObserver(Observer observer) {
        for (Iterator<Quackable> iterator = quackers.iterator(); iterator.hasNext();) {
            iterator.next().registerObserver(observer);
        }
    }

}
