package org.ybygjy.basic.annotation.brfw.entity;

import org.ybygjy.basic.annotation.brfw.anno.Exportable;
import org.ybygjy.basic.annotation.brfw.anno.Persistent;

/**
 * Address Entity
 * @author WangYanCheng
 * @version 2009-12-16
 */
@Exportable(
        name = "Address",
        description = "Address Class Description",
        value = "Address Class Defined"
)
public class Address {
    /**country*/
    @Persistent
    private String country;
    /**province*/
    private String province;
    /**city*/
    private String city;
    /**street*/
    private String street;
    /**doorplate*/
    private String doorplate;
    /**
     * Address constructor
     * @param country country to set
     * @param province province to set
     * @param city city to set
     * @param street street to set
     * @param doorplate doorplate to set
     */
    public Address(String country, String province, String city, String street, String doorplate) {
        super();
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.doorplate = doorplate;
    }
}
