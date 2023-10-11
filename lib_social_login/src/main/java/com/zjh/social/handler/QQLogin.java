package com.zjh.social.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.QQParams;
import com.zjh.social.util.ResultCode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Administrator
 * @date 2023/9/28
 * @description QQ登录逻辑处理
 **/
public class QQLogin extends SocialAuthenticator<QQParams>{

    private static final String TAG = "QQLogin";
    private Tencent mTencent;
    private BaseUiListener baseUiListener;

    private QQLogin() {
    }

    private static final class QQInstanceHolder {
        static final QQLogin mInstance = new QQLogin();
    }

    public static QQLogin getInstance() {
        return QQInstanceHolder.mInstance;
    }

    @Override
    public void startAuth(@NonNull Context context,@NonNull QQParams params, @NonNull AuthCallback<Object> callback) {
        String appId = params.getAppId();
        if (TextUtils.isEmpty(appId)) {
            callback.call(ResultCode.ERROR_PARAMS, "appId 不能为空", null);
            return;
        }
        String scope = params.getScope();
        mTencent = Tencent.createInstance(appId, context.getApplicationContext());
        if (mTencent != null && !mTencent.isSessionValid()) {
            baseUiListener = new BaseUiListener(mTencent, callback);
            mTencent.login((Activity) context, scope, baseUiListener);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mTencent != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
        }
    }

    public static class BaseUiListener implements IUiListener {
        private final Tencent mTencent;
        private final AuthCallback<Object> callback;

        public BaseUiListener(Tencent mTencent, AuthCallback<Object> callback) {
            this.mTencent = mTencent;
            this.callback = callback;
        }

        @Override
        public void onComplete(Object response) {
            Log.i(TAG, "Auth onSuccess");
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken, expires);
                callback.call(ResultCode.AUTH_SUCCESS, "QQ auth success", accessToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError e) {
            Log.e(TAG, "Auth Failed, errorMessage is" + e.errorMessage);
            if (callback != null) {
                callback.call(ResultCode.ERROR_QQ, "Auth by QQ failed", null);
            }
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "Auth Failed, onCancel");
            callback.call(ResultCode.ERROR_QQ, "Auth by QQ canceled", null);
        }

        @Override
        public void onWarning(int i) {
            Log.e(TAG, "Auth Failed, onWarning");
            callback.call(ResultCode.ERROR_QQ, "Auth by QQ onWarning", null);
        }

    }
}
