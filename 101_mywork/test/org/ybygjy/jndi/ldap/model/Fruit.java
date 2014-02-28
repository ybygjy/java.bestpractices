package org.ybygjy.jndi.ldap.model;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;

import org.ybygjy.jndi.ldap.Constant;

/**
 * @author WangYanCheng
 * @version 2011-4-29
 */
public class Fruit implements Referenceable {
    /**dn*/
    public static final String dn = "cn=fruit,".concat(Constant.BASEDN);
    /** innerType */
    private String fruitType;
    /**
     * Constructor
     * @param fruitType 水果类型^_^
     */
    public Fruit(String fruitType) {
        this.fruitType = fruitType;
    }

    @Override
    public String toString() {
        return "Fruit [fruitType=" + fruitType + "]";
    }

    /**
     * {@inheritDoc}
     */
    public Reference getReference() throws NamingException {
        Reference refObj = new Reference(Fruit.class.getName(), new StringRefAddr("fruit", fruitType),
            FruitFactory.class.getName(), null);
        return refObj;
    }
    /**
     * getFruitType
     * @return
     */
    public String getFruitType() {
        return this.fruitType;
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Fruit ? this.fruitType.equals(((Fruit)obj).getFruitType()) : false;
    }
}
