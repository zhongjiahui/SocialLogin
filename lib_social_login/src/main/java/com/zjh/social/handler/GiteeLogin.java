package com.zjh.social.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.GiteeParams;
import com.zjh.social.util.RequestCode;
import com.zjh.social.util.ResultCode;
import com.zjh.social.web.WebAuthBuilder;
import com.zjh.social.web.helpers.WebAuthUser;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class GiteeLogin extends SocialAuthenticator<GiteeParams>{

    private static final String TAG = "GiteeLogin";
    private AuthCallback<Object> callback;

    private GiteeLogin() {
    }

    public static GiteeLogin getInstance() {
        return GithubInstanceHolder.mInstance;
    }

    private static final class GithubInstanceHolder {
        static final GiteeLogin mInstance = new GiteeLogin();
    }
    @Override
    public void startAuth(@NonNull Context context, GiteeParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;

        String clientId = params.getClientId();
        if (TextUtils.isEmpty(clientId)) {
            callback.call(ResultCode.ERROR_PARAMS, "clientId 不能为空", null);
            return;
        }
        String redirectUrl = params.getRedirectUrl();
        String scope = params.getScope();

        WebAuthBuilder.getInstance((Activity) context)
                .setAuthorizationUrl("https://gitee.com/oauth/authorize")
                .setAccessTokenUrl("https://gitee.com/oauth/token")
                .setClientID(clientId)
                .setRedirectURI(redirectUrl)
                .setScope(scope)
                .authenticate(RequestCode.REQUEST_GITEE);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCode.REQUEST_GITEE && data != null) {
            if (resultCode == Activity.RESULT_OK && data.hasExtra("social_login")) {
                Log.i(TAG, "Auth onSuccess");
                WebAuthUser user = data.getParcelableExtra("social_login");
                if (user != null && user.getCode() != null) {
                    callback.call(ResultCode.AUTH_SUCCESS, "Gitee auth success", user.getCode());
                }
            } else {
                Log.e(TAG, "Auth Failed, onError errorCode = " + data.getIntExtra("err_code", 0)
                        + " errorMsg = " + data.getStringExtra("err_message"));
                if (callback != null) {
                    callback.call(ResultCode.ERROR_GITEE, "Auth by Gitee failed", null);
                }
            }
        }
    }
}
