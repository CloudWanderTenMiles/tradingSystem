package com.frye.trading.config;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken {

    /**
     * 当前登录用户类型
     */
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserToken(String username, String password, String userType) {
        super(username, password);
        this.userType = userType;
    }
}
