package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class XiaomiParams {

    private long appId;
    private String redirectUri;
    private int[] scope;
    private String state;

    public XiaomiParams() {
        state = "1234";
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public int[] getScope() {
        return scope;
    }

    public void setScope(int[] scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
