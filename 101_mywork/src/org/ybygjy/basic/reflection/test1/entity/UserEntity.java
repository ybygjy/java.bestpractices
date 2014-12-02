package org.ybygjy.basic.reflection.test1.entity;

import java.util.Arrays;
import java.util.List;

/**
 * UserEntity
 * @author WangYanCheng
 * @version 2009-12-17
 */
public class UserEntity {
    /**userName*/
    private String userName;
    /**userPassword*/
    private String userPassword;
    /**电话号码*/
    private String[] telPhone;
    /**stepValue*/
    public int stepValue;
    /**address*/
    private List<String> address = null;
    /**
     * Constructor
     */
    public UserEntity() {
    }
    /**
     * Contructor
     * @param userName userName to set
     * @param userPassword userPassword to set
     */
    public UserEntity(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }
    /**
     * userEntity
     * @param userName userName to set
     * @param userPassword userPassword to set
     * @param flag flag to set
     */
    protected UserEntity(String userName, String userPassword, String flag) {
        this.userName = userName;
        this.userPassword = userPassword;
    }
    /**
     * userName
     * @return userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * userName to set
     * @param userName userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * getStepValue
     * @return stepValue
     */
    public int getStepValue() {
        return stepValue;
    }
    /**
     * setStepValue
     * @param stepValue stepValue
     */
    public void setStepValue(int stepValue) {
        this.stepValue = stepValue;
    }
    /**
     * userPassword
     * @return userPassword
     */
    public String getUserPassword() {
        return userPassword;
    }
    /**
     * userPassword to set
     * @param userPassword userPassword
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    /**
     * getTelphone
     * @return telPhone
     */
    public String[] getTelPhone() {
        return telPhone;
    }
    /**
     * setTelphone
     * @param telPhone telPhone
     */
    public void setTelPhone(String[] telPhone) {
        this.telPhone = telPhone;
    }
    /**
     * getAddress
     * @return addressList
     */
    public List<String> getAddress() {
        return address;
    }
    /**
     * setAddress
     * @param address address
     */
    public void setAddress(List<String> address) {
        this.address = address;
    }
    /**
     * doIncrementV
     * @param value value
     * @return rtnValue
     */
    public int doIncrementV(int value) {
        return (++value);
    }
    @Override
    public String toString() {
        return "UserEntity [address=" + address + ", telPhone=" + Arrays.toString(telPhone)
                + ", userName=" + userName + ", userPassword=" + userPassword + "]";
    }
}
