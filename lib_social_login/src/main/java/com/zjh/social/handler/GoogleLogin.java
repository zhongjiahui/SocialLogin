package com.zjh.social.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.GoogleParams;
import com.zjh.social.util.RequestCode;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/9/28
 * @description Google登录逻辑处理
 **/
public class GoogleLogin extends SocialAuthenticator<GoogleParams>{

    private AuthCallback<Object> callback;

    private GoogleLogin() {
    }

    public static GoogleLogin getInstance() {
        return GoogleInstanceHolder.mInstance;
    }

    private static class GoogleInstanceHolder {
        private static final GoogleLogin mInstance = new GoogleLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, GoogleParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;
        String serverClientId = params.getServerClientIdl();
        if (TextUtils.isEmpty(serverClientId)) {
            callback.call(ResultCode.ERROR_PARAMS, "serverClientId 不能为空", null);
            return;
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .requestId()
                .requestProfile()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context, gso);

        Intent intent = googleSignInClient.getSignInIntent();
        ((Activity) context).startActivityForResult(intent, RequestCode.REQUEST_GOOGLE);
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCode.REQUEST_GOOGLE && data != null) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                callback.call(ResultCode.AUTH_SUCCESS, "Google auth success", account.getServerAuthCode());
            } catch (ApiException e) {
                Log.e("GoogleLogin", e.toString());
                callback.call(e.getStatusCode(), e.getMessage(), null);
            }
        }
    }

}
