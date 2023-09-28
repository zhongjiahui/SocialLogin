package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/9/21
 * @description 支付宝登录参数
 **/
public class AlipayParams {

    private String appId;
    private String scope;
    private String state;

    public AlipayParams() {
        scope = "auth_user";
        state = "init";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
