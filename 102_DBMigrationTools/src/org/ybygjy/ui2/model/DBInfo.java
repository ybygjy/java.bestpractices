package org.ybygjy.ui2.model;

/**
 * 数据库信息
 * @author WangYanCheng
 * @version 2012-10-29
 */
public class DBInfo {
    /**驱动参数*/
    private String driver;
    /**连接串*/
    private String connURL;
    /**用户名*/
    private String userName;
    /**密码*/
    private volatile String password;
    
    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }
    /**
     * @param connURL the connURL to set
     */
    public void setConnURL(String connURL) {
        this.connURL = connURL;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }
    /**
     * @return the connURL
     */
    public String getConnURL() {
        return connURL;
    }
    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
