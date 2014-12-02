package org.ybygjy.web.entity;
/**
 * UserEntity
 * @author WangYanCheng
 * @version 2010-1-10
 */
public class UserEntity {
    /**userName*/
    private String userName;
    /**password*/
    private String password;
    /**
     * constructor
     */
    public UserEntity() {
    }
    /**
     * constructor
     * @param userName 用户名
     * @param password 密码
     */
    public UserEntity(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
