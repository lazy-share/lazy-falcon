package com.lazy.falcon.example.entity;

import com.lazy.falcon.common.annotaion.ApiParamItem;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/8.
 */
public class UserEntity implements Serializable {

    @ApiParamItem(value = "登录账号")
    private String loginName;
    @ApiParamItem(value = "密码")
    private String password;
    @ApiParamItem(value = "手机")
    private String mobile;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
