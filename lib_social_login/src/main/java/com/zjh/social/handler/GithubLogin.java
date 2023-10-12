package com.zjh.social.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.GithubParams;
import com.zjh.social.util.RequestCode;
import com.zjh.social.util.ResultCode;
import com.zjh.social.web.WebAuthBuilder;
import com.zjh.social.web.helpers.WebAuthUser;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class GithubLogin extends SocialAuthenticator<GithubParams>{

    private static final String TAG = "GithubLogin";
    private AuthCallback<Object> callback;

    private GithubLogin() {
    }

    public static GithubLogin getInstance() {
        return GithubInstanceHolder.mInstance;
    }

    private static final class GithubInstanceHolder {
        static final GithubLogin mInstance = new GithubLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, GithubParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;

        String clientId = params.getClientId();
        if (TextUtils.isEmpty(clientId)) {
            callback.call(ResultCode.ERROR_PARAMS, "clientId 不能为空", null);
            return;
        }
        String redirectUrl = params.getRedirectUrl();
        String scope = params.getScope();

        WebAuthBuilder.getInstance((Activity) context)
                .setAuthorizationUrl("https://github.com/login/oauth/authorize")
                .setAccessTokenUrl("https://github.com/login/oauth/access_token")
                .setClientID(clientId)
                .setRedirectURI(redirectUrl)
                .setScope(scope)
                .authenticate(RequestCode.REQUEST_GITHUB);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCode.REQUEST_GITHUB && data != null) {
            if (resultCode == Activity.RESULT_OK && data.hasExtra("social_login")) {
                Log.i(TAG, "Auth onSuccess");
                WebAuthUser user = data.getParcelableExtra("social_login");
                if (user != null && user.getCode() != null) {
                    callback.call(ResultCode.AUTH_SUCCESS, "Github auth success", user.getCode());
                }
            } else {
                Log.e(TAG, "Auth Failed, onError errorCode = " + data.getIntExtra("err_code", 0)
                        + " errorMsg = " + data.getStringExtra("err_message"));
                if (callback != null) {
                    callback.call(ResultCode.ERROR_GITHUB, "Auth by Github failed", null);
                }
            }
        }
    }
}
