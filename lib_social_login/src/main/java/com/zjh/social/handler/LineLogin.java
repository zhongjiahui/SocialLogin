package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linecorp.linesdk.Scope;
import com.linecorp.linesdk.auth.LineAuthenticationParams;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.LineParams;
import com.zjh.social.util.RequestCode;
import com.zjh.social.util.ResultCode;

import java.util.ArrayList;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class LineLogin extends SocialAuthenticator<LineParams>{

    private static final String TAG = "LineLogin";
    private AuthCallback<Object> callback;

    private LineLogin() {
    }

    public static LineLogin getInstance() {
        return LineInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class LineInstanceHolder {
        static final LineLogin mInstance = new LineLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, LineParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;

        String channelID = params.getChannelID();

        if (channelID == null) {
            callback.call(ResultCode.ERROR_PARAMS, "channelID 不能为空", null);
            return;
        }

        ArrayList<Scope> scopes = params.getScopes();
        try {
            // App-to-app login
            if (scopes == null || scopes.isEmpty()) {
                scopes = new ArrayList<>();
                scopes.add(Scope.PROFILE);
                scopes.add(Scope.OPENID_CONNECT);
                scopes.add(Scope.OC_EMAIL);
                //scopes.add(Scope.OC_PHONE_NUMBER);
            }

            Intent loginIntent = LineLoginApi.getLoginIntent(
                    context,
                    channelID,
                    new LineAuthenticationParams.Builder()
                            .scopes(scopes)
                            .nonce("1234") // nonce can be used to improve security
                            .build());
            ((Activity) context).startActivityForResult(loginIntent, RequestCode.REQUEST_LINE);

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
            callback.call(ResultCode.ERROR_LINE, e.toString(), null);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCode.REQUEST_LINE && data != null) {
            LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);
            switch (result.getResponseCode()) {
                case SUCCESS:
                    // Login successful
                    if (result.getLineCredential() == null) {
                        if (callback != null) {
                            callback.call(ResultCode.ERROR_LINE, "Auth by Line failed", null);
                        }
                        return;
                    }
                    String accessToken = result.getLineCredential().getAccessToken().getTokenString();
                    if (TextUtils.isEmpty(accessToken)) {
                        if (callback != null) {
                            callback.call(ResultCode.ERROR_LINE, "Auth by Line failed", null);
                        }
                        return;
                    }
                    Log.i(TAG, "Auth onSuccess");
                    String idToken = (result.getLineIdToken() == null ? null : result.getLineIdToken().getRawString());
                    callback.call(ResultCode.AUTH_SUCCESS, "Auth onSuccess", "accessToken = " + accessToken + " idToken = " + idToken);
                    break;
                case CANCEL:
                    // Login canceled by user
                    Log.e(TAG, "Auth Canceled");
                    if (callback != null) {
                        callback.call(ResultCode.ERROR_LINE, "Auth by Line canceled", null);
                    }
                    break;
                default:
                    // Login canceled due to other error
                    Log.e(TAG, "Auth Failed, onError errorCode = " + result.getResponseCode()
                            + " errorMsg = " + result.getErrorData());
                    if (callback != null) {
                        callback.call(ResultCode.ERROR_LINE, "Auth by Line failed", null);
                    }
            }
        }
    }
}
