package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.callback.douyinapi.DouYinCallBackActivity;
import com.zjh.social.params.DouYinParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class DouYinLogin extends SocialAuthenticator<DouYinParams>{

    private DouYinLogin() {
    }

    public static DouYinLogin getInstance() {
        return DouYinInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class DouYinInstanceHolder {
        static final DouYinLogin mInstance = new DouYinLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, DouYinParams params, @NonNull AuthCallback<Object> callback) {
        String clientKey = params.getClientKey();
        if (TextUtils.isEmpty(clientKey)) {
            callback.call(ResultCode.ERROR_PARAMS, "clientKey 不能为空", null);
            return;
        }
        String state = params.getState();
        String scope = params.getScope();
        String callerLocalEntry = params.getCallerLocalEntry();

        DouYinCallBackActivity.setCallback(callback);
        DouYinOpenApiFactory.init(new DouYinOpenConfig(clientKey));

        DouYinOpenApi douyinOpenApi = DouYinOpenApiFactory.create((Activity) context);
        Authorization.Request request = new Authorization.Request();
        // 用户授权时必选权限
        request.scope = scope;
        // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        //request.state = "ww";
        if (state != null) {
            request.state = state;
        }
        // 第三方指定自定义的回调类 Activity
        if (callerLocalEntry != null) {
            request.callerLocalEntry = callerLocalEntry;
        }
        // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用web页授权
        douyinOpenApi.authorize(request);

    }
}
