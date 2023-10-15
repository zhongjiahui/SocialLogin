package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class SlackParams {

    private String clientId;
    private String redirectUrl;
    private String scope;

    public SlackParams() {
        scope = "openid%20profile%20email";
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
