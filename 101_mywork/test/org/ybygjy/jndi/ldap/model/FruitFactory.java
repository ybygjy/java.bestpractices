package org.ybygjy.jndi.ldap.model;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class FruitFactory implements ObjectFactory {
    public FruitFactory() {
    }
    /**
     * {@inheritDoc}
     */
    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment)
        throws Exception {
        if (obj instanceof Reference) {
            Reference ref = (Reference) obj;
            if (ref.getClassName().equals(Fruit.class.getName())) {
                RefAddr addr = ref.get("fruit");
                if (addr != null) {
                    return new Fruit((String) addr.getContent());
                }
            }
        }
        return null;
    }

}
