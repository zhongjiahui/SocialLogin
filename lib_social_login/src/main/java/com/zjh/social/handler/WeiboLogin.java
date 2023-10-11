package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.WeiboParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/10/11
 * @description
 **/
public class WeiboLogin extends SocialAuthenticator<WeiboParams>{
    private static final String TAG = "WeiboLogin";
    private IWBAPI mWBAPI;

    private WeiboLogin() {
    }

    public static WeiboLogin getInstance() {
        return WeiboInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class WeiboInstanceHolder {
        static final WeiboLogin mInstance = new WeiboLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, WeiboParams params, @NonNull AuthCallback<Object> callback) {
        String appKey = params.getAppKey();
        if (TextUtils.isEmpty(appKey)) {
            callback.call(ResultCode.ERROR_PARAMS, "appKey 不能为空", null);
            return;
        }
        String redirectUrl = params.getRedirectUrl();
        String scope = params.getScope();
        AuthInfo authInfo = new AuthInfo(context, appKey, redirectUrl, scope);
        mWBAPI = WBAPIFactory.createWBAPI(context);
        mWBAPI.registerApp(context, authInfo);
        mWBAPI.authorizeClient((Activity) context, new WbAuthListener() {
            @Override
            public void onComplete(Oauth2AccessToken token) {
                Log.i(TAG, "Auth onSuccess");
                callback.call(ResultCode.AUTH_SUCCESS, "Facebook auth success", token.getAccessToken());
            }

            @Override
            public void onError(UiError error) {
                Log.e(TAG, "Auth Failed, errorMessage is" + error.errorMessage);
                callback.call(ResultCode.ERROR_WEIBO, "Auth by Weibo failed", null);
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "Auth Failed, onCancel");
                callback.call(ResultCode.ERROR_WEIBO, "Auth by Weibo canceled", null);
            }
        });
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, @Nullable Intent data) {
        if (mWBAPI != null) {
            mWBAPI.authorizeCallback(activity, requestCode, resultCode, data);
        }
    }
}
