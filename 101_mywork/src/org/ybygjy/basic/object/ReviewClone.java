package org.ybygjy.basic.object;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * clone机制的复习
 * <p>
 * 1、注意Object#clone与Cloneable的关系
 * </p>
 * <p>
 * 2、深层次clone：主要机制就是所有实例的嵌套实例都需要做clone
 * <p>
 * 3、浅层次clone：默认机制，实例嵌套对象引用还是共享的。浅层次clone可简单理解为对象引用如<br/>
 * <code>Employee emp = new Employee();Employee emp2 = emp; emp2.doSomething();</code>
 * @author WangYanCheng
 * @version 2011-2-24
 */
public class ReviewClone {
    /**
     * 测试入口
     * @param args args
     * @throws CloneNotSupportedException clone目标实例关联实例可能未实现Cloneable接口
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        Employee empOne = new Employee("Mr.Wang", 1000);
        Employee empTwo = (Employee) empOne.clone();
        empTwo.setEmpName("Mrs.Wang");
        empTwo.setHireDay(new GregorianCalendar(2011, 1, 24).getTime());
        System.out.println(empOne);
        System.out.println(empTwo);
    }
}

/**
 * 深层次clone
 * @author WangYanCheng
 * @version 2011-2-24
 */
class Employee implements Cloneable {
    /**
     * name
     */
    private String empName;
    /**
     * salary
     */
    private double salary;
    /**
     * hireDay
     */
    private Date hireDay;

    /**
     * Contructor
     * @param empName 员工姓名
     * @param salary 薪水
     */
    public Employee(String empName, double salary) {
        super();
        this.empName = empName;
        this.salary = salary;
        this.hireDay = new Date();
    }

    /**
     * 设置员工名称
     * @param empName 员工名称
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * 设置日期
     * @param hireDay 日期
     */
    public void setHireDay(Date hireDay) {
        this.hireDay = hireDay;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected Employee clone() throws CloneNotSupportedException {
        Employee rtnEmp = (Employee) super.clone();
        rtnEmp.hireDay = (Date) this.hireDay.clone();
        return rtnEmp;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Employee [empName=");
        builder.append(empName);
        builder.append(", hireDay=");
        builder.append(hireDay);
        builder.append(", salary=");
        builder.append(salary);
        builder.append("]");
        return builder.toString();
    }

}
