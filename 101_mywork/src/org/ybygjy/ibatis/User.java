package org.ybygjy.ibatis;

import java.util.List;

/**
 * POJO
 * @author WangYanCheng
 * @version 2010-11-26
 */
public class User {
    /** userId */
    private Integer userId;
    /** userName */
    private String userName;
    /** password */
    private String passWord;
    /** userNo */
    private String userNo;
    /** companyName */
    private String companyName;
    /** 借书记录 */
    private List<LoanLog> loanLoges;

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
     * @return the userNo
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * @param userNo the userNo to set
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * @return the loanLoges
     */
    public List<LoanLog> getLoanLoges() {
        return loanLoges;
    }

    /**
     * @param loanLoges the loanLoges to set
     */
    public void setLoanLoges(List<LoanLog> loanLoges) {
        this.loanLoges = loanLoges;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [passWord=");
        builder.append(passWord);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", userName=");
        builder.append(userName);
        builder.append("]");
        return builder.toString();
    }
}
