package org.ybygjy.util;

import org.ybygjy.BusinessException;

/**
 * 数据库连接信息
 * @author WangYanCheng
 * @version 2012-11-15
 */
public class DBConfEntry {
    private DB_TYPE dbType;
    private String connUrl;
    private String userName;
    private String passWord;
    /** 标记该实例是否已经验证通过，能正常创建数据库连接 */
    private boolean vaild;

    /**
     * @return the dbType
     */
    public DB_TYPE getDbType() {
        return dbType;
    }

    /**
     * @param dbType the dbType to set
     */
    public void setDbType(DB_TYPE dbType) {
        this.dbType = dbType;
    }

    /**
     * @return the connUrl
     */
    public String getConnUrl() {
        return connUrl;
    }

    /**
     * @param connUrl the connUrl to set
     */
    public void setConnUrl(String connUrl) {
        this.connUrl = connUrl;
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
     * @return the passWord
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * @param passWord the passWord to set
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * @return the vaild
     */
    public boolean isVaild() {
        return vaild;
    }

    /**
     * @param vaild the vaild to set
     */
    public void setVaild(boolean vaild) {
        this.vaild = vaild;
    }

    /**
     * 测试与数据库的连接
     * @return rtnFlag {true:连接正常;false:连接失败}
     * @throws BusinessException 业务级异常
     */
    public boolean test() throws BusinessException {
        return preTest() ? DBUtils.testConnection(this) : false;
    }

    private boolean preTest() throws BusinessException {
        if (getDbType() == DB_TYPE.UNKNOW) {
            throw new BusinessException("数据库驱动类型错误！");
        } else if (getConnUrl() == null || "".equals(getConnUrl())) {
            throw new BusinessException("数据库连接串为空！");
        } else if (getUserName() == null || "".equals(getUserName())) {
            throw new BusinessException("用户名为空！");
        } else if (getPassWord() == null || "".equals(getPassWord())) {
            throw new BusinessException("密码为空！");
        }
        return true;
    }

    @Override
    public String toString() {
        return "DBConfEntry [dbType=" + dbType + ", connUrl=" + connUrl + ", userName=" + userName
            + ", passWord=" + passWord + ", vaild=" + vaild + "]";
    }
}
