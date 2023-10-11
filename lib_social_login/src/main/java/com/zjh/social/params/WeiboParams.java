package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/10/11
 * @description
 **/
public class WeiboParams {
    private String appKey;
    private String scope;
    private String redirectUrl;

    public WeiboParams() {
        scope = "email,direct_messages_read,direct_messages_write,friendships_groups_read," +
                "friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
