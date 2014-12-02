package org.ybygjy.basic.object;

import java.util.Hashtable;

import org.ybygjy.basic.TestInterface;

/**
 * 学习Java#对象的#clone机制
 * <li>影子Clone问题</li>
 * <li>深度Clone问题</li>
 * @author WangYanCheng
 * @version 2010-9-25
 */
public class CloneTest implements TestInterface {
    /** innerObj */
    private Obj objInst = null;
    /** intParam */
    private int intParam = 0;

    /**
     * Constructor
     */
    public CloneTest() {
        this.objInst = new Obj();
    }

    /**
     * {@inheritDoc}
     */
    public void doTest() {
    }

    /**
     * hashTable真的能存储对象么?
     * @return table table
     */
    public java.util.Hashtable initHashTable() {
        java.util.Hashtable table = new Hashtable();
        StringBuilder sbud = new StringBuilder();
        sbud.append("abc");
        table.put("1", sbud);
        sbud.append("def");
        table.put("2", sbud);
        sbud.append("ghi");
        table.put("3", sbud);
        sbud.append("jkl");
        table.put("4", sbud);
        return table;
    }

    /**
     * doChange
     * @param obj obj
     */
    public void doChange(Obj obj) {
        obj.str = "Changed Value";
    }

    /**
     * doChange
     * @param intParam intParam
     */
    public void doChange(int intParam) {
        this.intParam = intParam;
        intParam = 0;
    }

    /**
     * getObjStr
     * @return objStr objStr
     */
    public String getObjStr() {
        return objInst.str;
    }

    /**
     * getIntParam
     * @return intParam/0
     */
    public int getIntParam() {
        return this.intParam;
    }

    /**
     * getObj
     * @return objInst/null
     */
    public Obj getObj() {
        return this.objInst;
    }

    /**
     * 实例对象
     * @author WangYanCheng
     * @version 2010-9-25
     */
    protected class Obj implements Cloneable {
        /** shadowInst */
        private ObjShadow objShadow = new ObjShadow();
        /** 内部变量 */
        String str = "Init Value";

        @Override
        public String toString() {
            return this.str;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Obj rtnObj = (Obj) super.clone();
            rtnObj.objShadow = (ObjShadow) rtnObj.objShadow.clone();
            return rtnObj;
        }

        /**
         * getShadow
         * @return objShadow objShadow
         */
        public String getShadow() {
            return this.objShadow.toString();
        }
    }

    /**
     * ShadowCloneInstance
     * @author WangYanCheng
     * @version 2010-9-25
     */
    protected class ObjShadow implements Cloneable {
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return ((ObjShadow) super.clone());
        }
    }
}
