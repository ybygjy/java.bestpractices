package org.ybygjy.basic.algorithms.sort;

import java.util.Arrays;

/**
 * 对象排序
 * @author WangYanCheng
 * @version 2014-3-27
 */
public class ObjectSort {
    public static void main(String[] args) {
        ArrayInOb personArray = new ArrayInOb(100);
        personArray.insert("A", "John", 10);
        personArray.insert("B", "Jams", 40);
        personArray.insert("D", "Jason", 20);
        personArray.insert("C", "William", 40);
        System.out.println(personArray.toString());
        personArray.insertionSort();
        System.out.println(personArray.toString());
    }
}
class Person {
    private String lastName;
    private String firstName;
    private int age;
    public Person(String lastName, String firstName, int age) {
        super();
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person [lastName=" + lastName + ", firstName=" + firstName + ", age=" + age + "]";
    }
}
class ArrayInOb {
    private Person[] array;
    //Number of data items
    private int nElems;
    public ArrayInOb(int nElems) {
        array = new Person[nElems];
        this.nElems = 0;
    }
    public void insert(String lastName, String firstName, int age) {
        array[nElems++] = new Person(lastName, firstName, age);
    }
    /**
     * 插入排序
     */
    public void insertionSort() {
        int out;
        int in;
        int len = nElems;
        Person tmpPerson = null;
        for (out = 1;out < len; out++) {
            tmpPerson = this.array[out];
            in = out;
            while (in > 0 && this.array[in - 1].getLastName().compareTo(tmpPerson.getLastName()) > 0) {
                this.array[in] = this.array[in - 1];
                --in;
            }
            this.array[in] = tmpPerson;
        }
    }
    @Override
    public String toString() {
        return "ArrayInOb [array=" + Arrays.toString(array) + "]";
    }
}
