package org.ybygjy.basic.collect;

import java.util.List;

/**
 * InnerEntity
 * @author WangYanCheng
 * @version 2010-9-8
 */
public class InnerEntity {
    /** name */
    private String name;
    /** items */
    private List<InnerEntity> items;
    /** parentEntity */
    private InnerEntity parentEntity;

    /**
     * Constructor
     * @param name name
     * @param items items
     * @param parentEntity parentEntity
     */
    public InnerEntity(String name, List<InnerEntity> items, InnerEntity parentEntity) {
        super();
        this.name = name;
        this.items = items;
        this.parentEntity = parentEntity;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * setter name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the items
     */
    public List<InnerEntity> getItems() {
        return items;
    }

    /**
     * setter items
     * @param items items
     */
    public void setItems(List items) {
        this.items = items;
    }

    /**
     * @return the parentEntity
     */
    public InnerEntity getParentEntity() {
        return parentEntity;
    }
}
