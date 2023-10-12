package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class LinkedinParams {

    private String appKey;

    private String redirectUrl;

    private String scope;

    public LinkedinParams() {
        scope = "r_liteprofile%20r_emailaddress";
    }


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
