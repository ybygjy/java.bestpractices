package org.ybygjy.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * 深度克隆
 * <p>1、必须实现Cloneable接口</p>
 * <p>2、注意引用类型对象的copy
 * @author WangYanCheng
 * @version 2014-05-03
 */
public class DepthObjectClone {
    public static void main(String[] args) {
        List<Employee> childs = new ArrayList<Employee>();
        for (int i = 0; i < 10; i++) {
            childs.add(new Employee("Employee_" + i, null));
        }
        Employee employee = new Employee("Depth Clone", childs);
        System.out.println(employee.toString());
        try {
            Employee cloneEmpInst = (Employee) employee.clone();
            System.out.println(cloneEmpInst);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
class Employee implements Cloneable {
    private String userName;
    private List<Employee> childs;
    public Employee(String userName, List<Employee> childs) {
        super();
        this.userName = userName;
        this.childs = childs;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public List<Employee> getChilds() {
        return childs;
    }
    public void setChilds(List<Employee> childs) {
        this.childs = childs;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Employee rtnObj = null;
        try {
            rtnObj = (Employee) super.clone();
            if (null != childs) {
                List<Employee> tmpArray = new ArrayList<Employee>();
                for (Employee emp : childs) {
                    tmpArray.add((Employee) emp.clone());
                }
                rtnObj.setChilds(tmpArray);
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return rtnObj;
    }
    @Override
    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("Employee [childs=");
        if (null != childs) {
            for (Employee emp : childs) {
                sbuf.append(Integer.toHexString(emp.hashCode())).append(",");
            }
        }
        sbuf.append(", userName=" + userName + "]");
        return sbuf.toString();
    }
}
