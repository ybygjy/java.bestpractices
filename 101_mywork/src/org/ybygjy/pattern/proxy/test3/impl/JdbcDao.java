package org.ybygjy.pattern.proxy.test3.impl;

import org.ybygjy.pattern.proxy.test3.Dao;
/**
 * jdbcDao
 * @author WangYanCheng
 * @version 2009-12-31
 */
public class JdbcDao implements Dao {
    /**
     * {@inheritDoc}
     */
    public void insert() {
        System.out.println("jdbcDao");
    }
    /**
     * {@inheritDoc}
     */
    public void update() {
        System.out.println("jdbcUpdate");
    }
}
