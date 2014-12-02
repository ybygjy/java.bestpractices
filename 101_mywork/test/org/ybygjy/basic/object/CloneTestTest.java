package org.ybygjy.basic.object;

import java.util.Hashtable;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.ybygjy.basic.object.CloneTest.Obj;

public class CloneTestTest {
    /** innerInstance */
    private CloneTest tiInst = null;

    @Before
    public void setUp() throws Exception {
        tiInst = new CloneTest();
    }

    @Test
    public void testDoTest() {
        tiInst.doTest();
        // 引用传递
        tiInst.doChange(tiInst.getObj());
        Assert.assertEquals(tiInst.getObjStr(), "Changed Value");
        // 值传递
        int initParam = 10;
        tiInst.doChange(initParam);
        Assert.assertEquals(tiInst.getIntParam(), initParam);
        // HashTable真的能存储对象么?
        Hashtable table = tiInst.initHashTable();
        for (Iterator iterator = table.entrySet().iterator(); iterator.hasNext();) {
            System.out.println(iterator.next());
        }
        Assert.assertEquals((table.get("1")).toString(), "abcdefghijkl");
        // Object#clone
        CloneTest.Obj obj = null;
        try {
            obj = (Obj) (tiInst.getObj().clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        Assert.assertNotSame(obj, tiInst.getObj());
        //深层clone
        Assert.assertNotSame(obj.getShadow(), tiInst.getObj().getShadow());
    }
}
