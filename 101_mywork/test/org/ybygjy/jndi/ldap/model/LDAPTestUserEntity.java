package org.ybygjy.jndi.ldap.model;

import java.io.Serializable;

import org.ybygjy.jndi.ldap.Constant;

/**
 * 负责定义测试时绑定的对象实例
 * @author WangYanCheng
 * @version 2011-4-28
 */
public class LDAPTestUserEntity implements Serializable {
    /** RootDN */
    private static final String tmpUserDN = "cn=userEntry,".concat(Constant.BASEDN);
    /**
     * serialized
     */
    private static final long serialVersionUID = 1128787189702206396L;
    String name, age;

    public LDAPTestUserEntity(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public static String getDN() {
        return tmpUserDN;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((age == null) ? 0 : age.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LDAPTestUserEntity other = (LDAPTestUserEntity) obj;
        if (age == null) {
            if (other.age != null)
                return false;
        } else if (!age.equals(other.age))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MethodClass [age=");
        builder.append(age);
        builder.append(", name=");
        builder.append(name);
        builder.append("]");
        return builder.toString();
    }
}
