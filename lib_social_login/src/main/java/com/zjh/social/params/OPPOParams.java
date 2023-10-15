package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class OPPOParams {

    private String appId;
    private String scope;

    public OPPOParams() {
        scope = "profile openid";
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
}
