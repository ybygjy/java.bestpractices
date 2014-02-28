package org.ybygjy.jndi.fs;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

/**
 * FruitFactory
 * @author WangYanCheng
 * @version 2011-1-6
 */
public class FruitFactory implements ObjectFactory {
    /**
     * {@inheritDoc}
     */
    public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable env) throws Exception {
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
