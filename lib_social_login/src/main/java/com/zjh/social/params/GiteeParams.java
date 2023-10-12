package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class GiteeParams {

    private String clientId;
    private String redirectUrl;
    private String scope;

    public GiteeParams() {
        scope = "user_info emails";
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
