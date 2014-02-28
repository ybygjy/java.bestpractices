package org.ybygjy.pattern.proxy.test3.impl;

import org.ybygjy.pattern.proxy.test3.Dao;
/**
 * HibernateDao
 * @author WangYanCheng
 * @version 2009-12-31
 */
public class HibernateDao implements Dao {
    /**
     * {@inheritDoc}
     */
    public void insert() {
        System.out.println("HibernateDao doInsert");
    }
    /**
     * {@inheritDoc}
     */
    public void update() {
        System.out.println("HibernateDao doUpdate");
    }

}
