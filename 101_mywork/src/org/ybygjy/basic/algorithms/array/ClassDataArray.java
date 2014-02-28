package org.ybygjy.basic.algorithms.array;


/**
 * 对象存储
 * @author WangYanCheng
 * @version 2011-8-3
 */
public class ClassDataArray {
    public static void main(String[] args) {
        DataArray dataArray = new DataArray(100);
        dataArray.insert("Evans", "Patty", 24);
        dataArray.insert("Smith", "Lorriane", 37);
        dataArray.insert("Yee", "Tom", 28);
        dataArray.insert("Hashmoto", "Sato", 19);
        dataArray.insert("Stimson", "Henry", 28);
        dataArray.insert("Valasquez", "Jose", 72);
        dataArray.insert("Lamarque", "Henry", 54);
        dataArray.insert("Vang", "Minh", 18);
        dataArray.insert("Creswell", "Lucida", 18);
        dataArray.display();
        String searchKey = "Stimson";
        Person fondPerson = dataArray.find(searchKey);
        if (null != fondPerson) {
            System.out.print("Found ");
            fondPerson.displayPerson();
        } else {
            System.out.println("Can't find " + searchKey);
        }
        dataArray.display();
        System.out.println("Deleting Smith, Yee, and Creswell");
        dataArray.delete("Smith");
        dataArray.delete("Yee");
        dataArray.delete("Creswell");
        dataArray.display();
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
    public void displayPerson() {
        System.out.println("LastName:" + this.lastName + "\tFirstName:" + this.firstName + "\tage:" + this.age);
    }
    public String getLast() {
        return this.lastName;
    }
}
class DataArray {
    private Person[] a;
    private int nElems;
    public DataArray(int max) {
        a = new Person[max];
        nElems = 0;
    }
    public Person find(String searchName) {
        //使用线性查找
        int j = 0;
        for (j = 0; j < nElems; j++) {
            if (a[j].getLast().equals(searchName)) {
                break;
            }
        }
        if (j == nElems) {
            return null;
        } else {
            return a[j];
        }
    }
    public void insert(String lastName, String firstName, int age) {
        a[nElems++] = new Person(lastName, firstName, age);
    }
    public boolean delete(String searchKey) {
        int j = 0;
        for (;j < nElems; j++) {
            if (a[j].getLast().equals(searchKey)) {
                break;
            }
        }
        if (j == nElems) {
            return false;
        } else {
            for (int k = j; k < nElems; k++) {
                a[k] = a[k + 1];
            }
            nElems--;
            return true;
        }
    }
    public void display() {
        for (int j = 0; j < nElems; j++) {
            a[j].displayPerson();
        }
    }
}