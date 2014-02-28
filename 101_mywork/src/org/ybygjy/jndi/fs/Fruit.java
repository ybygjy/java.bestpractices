package org.ybygjy.jndi.fs;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;

/**
 * fruit
 * @author WangYanCheng
 * @version 2011-1-6
 */
public class Fruit implements Referenceable {
    /** fruit */
    String fruit;

    /**
     * Constructor
     * @param f f
     */
    public Fruit(String f) {
        fruit = f;
    }

    /**
     * {@inheritDoc}
     */
    public Reference getReference() throws NamingException {
        return new Reference(Fruit.class.getName(), new StringRefAddr("fruit", fruit), FruitFactory.class
            .getName(), null); // factory location
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return fruit;
    }
}
