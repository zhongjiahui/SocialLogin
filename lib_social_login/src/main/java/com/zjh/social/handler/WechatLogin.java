package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.callback.wechat.WXCallbackActivity;
import com.zjh.social.params.WechatParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/9/19
 * @description 微信登录逻辑处理
 **/
public class WechatLogin extends SocialAuthenticator<WechatParams> {

    public static IWXAPI api;
    public static String appId;

    private WechatLogin() {
    }

    @SuppressLint("StaticFieldLeak")
    private static final class WechatInstanceHolder {
        static final WechatLogin mInstance = new WechatLogin();
    }

    public static WechatLogin getInstance() {
        return WechatLogin.WechatInstanceHolder.mInstance;
    }

    public void startAuth(@NonNull Context context, @NonNull WechatParams params, @NonNull AuthCallback<Object> callback) {
        appId = params.getAppId();
        if (TextUtils.isEmpty(appId)){
            callback.call(ResultCode.ERROR_PARAMS, "appId 不能为空", null);
            return;
        }

        api = WXAPIFactory.createWXAPI(context, appId, true);
        api.registerApp(appId);

        WXCallbackActivity.setCallback(callback);

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//只能填 snsapi_userinfo
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }

}
