package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.dingtalk.openauth.AuthLoginParam;
import com.android.dingtalk.openauth.DDAuthApiFactory;
import com.android.dingtalk.openauth.IDDAuthApi;
import com.android.dingtalk.openauth.utils.DDAuthConstant;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.DingTalkParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class DingTalkLogin extends SocialAuthenticator<DingTalkParams>{

    private static final String TAG = "DingTalkLogin";

    private AuthCallback<Object> callback;

    private DingTalkLogin() {
    }

    @SuppressLint("StaticFieldLeak")
    private static final class LinkedinInstanceHolder {
        static final DingTalkLogin mInstance = new DingTalkLogin();
    }

    public static DingTalkLogin getInstance() {
        return LinkedinInstanceHolder.mInstance;
    }

    @Override
    public void startAuth(@NonNull Context context, DingTalkParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;


        String appKey= params.getAppKey();
        if (TextUtils.isEmpty(appKey)) {
            callback.call(ResultCode.ERROR_PARAMS, "appId appKey", null);
            return;
        }
        
        String redirectUrl = params.getRedirectUrl();
        String responseType = params.getResponseType();
        String scope = params.getScope();
        String nonce = params.getNonce();
        String state = params.getState();
        String prompt = params.getPrompt();

        AuthLoginParam.AuthLoginParamBuilder builder = AuthLoginParam.AuthLoginParamBuilder.newBuilder();
        builder.appId(appKey);
        builder.redirectUri(redirectUrl);
        builder.responseType(responseType);
        builder.scope(scope);
        builder.nonce(nonce);
        builder.state(state);
        builder.prompt(prompt);
        IDDAuthApi authApi = DDAuthApiFactory.createDDAuthApi(context, builder.build());
        authApi.authLogin();
    }


    public void onActivityResult(Intent intent) {
        String authCode = intent.getStringExtra(DDAuthConstant.CALLBACK_EXTRA_AUTH_CODE);
        String state = intent.getStringExtra(DDAuthConstant.CALLBACK_EXTRA_STATE);
        String error = intent.getStringExtra(DDAuthConstant.CALLBACK_EXTRA_ERROR);
        if (!TextUtils.isEmpty(authCode)) {
            // 授权成功
            Log.i(TAG, "Auth onSuccess");
            callback.call(ResultCode.AUTH_SUCCESS, "Auth onSuccess", authCode);
        } else {
            // 授权失败
            Log.e(TAG, "Auth Failed, errorMessage is" + error);
            if (callback != null) {
                callback.call(ResultCode.ERROR_DING_TALK, "Auth by DingTalk failed", null);
            }
        }
    }

}
