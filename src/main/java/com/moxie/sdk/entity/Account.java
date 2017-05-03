package com.moxie.sdk.entity;

/**
 * Created by MX on 2017/4/27.
 */
public class Account {

    private String account;
    private String password;
    private String userId;
    private String origin;
    private String realName;
    private String idCard;
    private String loginType;

    public Account() {
    }

    public Account(String account, String password, String userId, String origin, String realName, String idCard, String loginType) {
        this.account = account;
        this.password = password;
        this.userId = userId;
        this.origin = origin;
        this.realName = realName;
        this.idCard = idCard;
        this.loginType = loginType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", origin='" + origin + '\'' +
                ", realName='" + realName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", loginType='" + loginType + '\'' +
                '}';
    }


}
