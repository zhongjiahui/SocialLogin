package com.zjh.social.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.GitLabParams;
import com.zjh.social.util.RequestCode;
import com.zjh.social.util.ResultCode;
import com.zjh.social.web.WebAuthBuilder;
import com.zjh.social.web.helpers.WebAuthUser;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class GitLabLogin extends SocialAuthenticator<GitLabParams>{
    private static final String TAG = "GitLabLogin";

    private AuthCallback<Object> callback;

    private GitLabLogin() {
    }

    public static GitLabLogin getInstance() {
        return GitLibInstanceHolder.mInstance;
    }

    private static final class GitLibInstanceHolder {
        static final GitLabLogin mInstance = new GitLabLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, GitLabParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;

        String appId = params.getAppId();
        if (TextUtils.isEmpty(appId)) {
            callback.call(ResultCode.ERROR_PARAMS, "appId 不能为空", null);
            return;
        }
        String redirectUrl = params.getRedirectUrl();
        String scope = params.getScope();

        WebAuthBuilder.getInstance((Activity) context)
                .setAuthorizationUrl("https://gitlab.com/oauth/authorize")
                .setAccessTokenUrl("https://gitlab.com/oauth/token")
                .setClientID(appId)
                .setRedirectURI(redirectUrl)
                .setScope(scope)
                .authenticate(RequestCode.REQUEST_GITLAB);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCode.REQUEST_GITLAB && data != null) {
            if (resultCode == Activity.RESULT_OK && data.hasExtra("social_login")) {
                Log.i(TAG, "Auth onSuccess");
                WebAuthUser user = data.getParcelableExtra("social_login");
                if (user != null && user.getCode() != null) {
                    callback.call(ResultCode.AUTH_SUCCESS, "GitLab auth success", user.getCode());
                }
            } else {
                Log.e(TAG, "Auth Failed, onError errorCode = " + data.getIntExtra("err_code", 0)
                        + " errorMsg = " + data.getStringExtra("err_message"));
                if (callback != null) {
                    callback.call(ResultCode.ERROR_GITLAB, "Auth by GitLab failed", null);
                }
            }
        }
    }
}
