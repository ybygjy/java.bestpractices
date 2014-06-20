package org.ybygjy.basic.ch_110_generic;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 泛型学习
 * @author WangYanCheng
 * @version 2014-6-16
 */
public class Pair2 {
    /**
     * 测试入口
     * @param args 参数列表
     */
    public static void main(String[] args) {
        Manager ceo = new Manager("Gus Greedy", 800000, 2003, 12, 15);
        Manager cfo = new Manager("Sid Sneaky", 800000, 2003, 12, 15);
        Pair<Manager> buddies = new Pair<Manager>(ceo, cfo);
        printBuddies(buddies);
        
        ceo.setBonus(100000);
        cfo.setBonus(500000);
        Manager[] managers = {ceo, cfo};
        
        Pair<Employee> result = new Pair<Employee>();
        minmaxBonus(managers, result);
        System.out.println("first:" + result.getFirst().getName() + ", second:" + result.getSecond().getName());
        maxminBonus(managers, result);
        System.out.println("first:" + result.getFirst().getName() + ", second:" + result.getSecond().getName());
    }
    /**
     * 泛型限定参数
     * @param p
     */
    public static void printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee second = p.getSecond();
        System.out.println(first.getName() + " and " + second.getName() + " are buddies.");
    }
    /**
     * 泛型限定参数
     * @param a
     * @param result
     */
    public static void minmaxBonus(Manager[] a, Pair<? super Manager> result) {
        if (a == null || a.length == 0) {
            return;
        }
        Manager min = a[0];
        Manager max = a[0];
        for (int i = 0; i < a.length; i++) {
            if (min.getBonus() > a[i].getBonus()) {
                min = a[i];
            }
            if (max.getBonus() < a[i].getBonus()) {
                max = a[i];
            }
        }
        result.setFirst(min);
        result.setSecond(max);
    }
    /**
     * 泛型超类型限定参数
     * @param a
     * @param result
     */
    public static void maxminBonus(Manager[] a, Pair<? super Manager> result) {
        minmaxBonus(a, result);
        PairAlg.swapHelper(result);
    }
    public static <T> Pair<T> makePair(Class<T> c) throws InstantiationException, IllegalAccessException {
        return new Pair<T>(c.newInstance(), c.newInstance());
    }
}
class PairAlg {
    public static boolean hasNulls(Pair<?> p) {
        return p.getFirst() == null || p.getSecond() == null;
    }
    public static void swap(Pair<?> p) {
        swapHelper(p);
    }
    public static <T> void swapHelper(Pair<T> p) {
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);
    }
}
class Employee {
    private String name;
    private double salary;
    private Date hireDay;
    public Employee(String n, double s, int year, int month, int day) {
        this.name = n;
        this.salary = s;
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
        hireDay = calendar.getTime();
    }
    public String getName() {
        return name;
    }
    public double getSalary() {
        return salary;
    }
    public Date getHireDay() {
        return hireDay;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public void setHireDay(Date hireDay) {
        this.hireDay = hireDay;
    }
}
class Manager extends Employee {
    private double bonus;
    public Manager(String n, double s, int year, int month, int day) {
        super(n, s, year, month, day);
        this.bonus = 0;
    }
    public double getSalary() {
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }
    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}