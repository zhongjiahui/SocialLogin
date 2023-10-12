package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class GitLabParams {

    private String appId;
    private String redirectUrl;
    private String scope;

    public GitLabParams() {
        scope = "read_user openid profile email";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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
