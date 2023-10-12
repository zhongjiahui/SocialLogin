package com.zjh.social.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.LinkedinParams;
import com.zjh.social.util.RequestCode;
import com.zjh.social.util.ResultCode;
import com.zjh.social.web.WebAuthBuilder;
import com.zjh.social.web.helpers.WebAuthUser;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class LinkedinLogin extends SocialAuthenticator<LinkedinParams>{

    private static final String TAG = "Linkedin";

    private AuthCallback<Object> callback;

    private LinkedinLogin() {
    }

    public static LinkedinLogin getInstance() {
        return LinkedinInstanceHolder.mInstance;
    }

    private static final class LinkedinInstanceHolder {
        static final LinkedinLogin mInstance = new LinkedinLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, LinkedinParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;
        String appKey = params.getAppKey();
        if (TextUtils.isEmpty(appKey)) {
            callback.call(ResultCode.ERROR_PARAMS, "appKey 不能为空", null);
            return;
        }
        String redirectUrl = params.getRedirectUrl();
        String scope = params.getScope();

        WebAuthBuilder.getInstance((Activity) context)
                .setAuthorizationUrl("https://www.linkedin.com/uas/oauth2/authorization")
                .setAccessTokenUrl("https://www.linkedin.com/uas/oauth2/accessToken")
                .setClientID(appKey)
                .setRedirectURI(redirectUrl)
                .setScope(scope)
                .authenticate(RequestCode.REQUEST_LINKEDIN);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode ==RequestCode.REQUEST_LINKEDIN && data != null) {
            if (resultCode == Activity.RESULT_OK && data.hasExtra("social_login")) {
                Log.i(TAG, "Auth onSuccess");
                WebAuthUser user = data.getParcelableExtra("social_login");
                if (user != null && user.getCode() != null) {
                    callback.call(ResultCode.AUTH_SUCCESS, "Linkedin auth success", user.getCode());
                }
            } else {
                Log.e(TAG, "Auth Failed, onError errorCode = " + data.getIntExtra("err_code", 0)
                        + " errorMsg = " + data.getStringExtra("err_message"));
                if (callback != null) {
                    callback.call(ResultCode.ERROR_LINKEDIN, "Auth by Linkedin failed", null);
                }
            }
        }
    }
}
