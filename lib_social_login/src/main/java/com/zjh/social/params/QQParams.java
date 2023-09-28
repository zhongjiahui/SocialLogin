package com.zjh.social.params;

/**
 * @author Administrator
 * @date 2023/9/28
 * @description QQ登录参数
 **/
public class QQParams {

    private String appId;
    private String scope = "get_user_info,list_photo,add_album,list_album,upload_pic,get_vip_rich_info,get_vip_info";

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
