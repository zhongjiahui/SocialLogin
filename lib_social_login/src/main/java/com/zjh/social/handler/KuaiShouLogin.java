package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kwai.auth.ILoginListener;
import com.kwai.auth.KwaiAuthAPI;
import com.kwai.auth.common.InternalResponse;
import com.kwai.auth.common.KwaiConstants;
import com.kwai.auth.login.kwailogin.KwaiAuthRequest;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.KuaiShouParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class KuaiShouLogin extends SocialAuthenticator<KuaiShouParams> implements ILoginListener {

    private static final String TAG = "KuaiShouLogin";
    private AuthCallback<Object> callback;
    private KuaiShouLogin() {
    }

    public static KuaiShouLogin getInstance() {
        return KuaiShouInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class KuaiShouInstanceHolder {
        static final KuaiShouLogin mInstance = new KuaiShouLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, KuaiShouParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;
        String state = params.getState();

        KwaiAuthRequest request = new KwaiAuthRequest.Builder()
                .setState(state)
                .setAuthMode(KwaiConstants.AuthMode.AUTHORIZE)
                .setLoginType(KwaiConstants.LoginType.APP)
                .setPlatformArray(new String[]{KwaiConstants.Platform.KWAI_APP, KwaiConstants.Platform.NEBULA_APP})
                .build();
        KwaiAuthAPI.getInstance().sendRequest((Activity) context, request, this);

    }

    @Override
    public void onSuccess(@NonNull InternalResponse internalResponse) {
        if (internalResponse.getCode() == null) {
            Log.e(TAG, "Auth Failed, code is null");
            if (callback != null) {
                callback.call(ResultCode.ERROR_KUAI_SHOU, "Auth by Kuaishou failed", null);
            }
            return;
        }
        callback.call(ResultCode.AUTH_SUCCESS, "Kuai shou auth success", internalResponse.getCode());
    }

    @Override
    public void onFailed(String s, int i, String s1) {
        Log.e(TAG, "Auth Failed");
        if (callback != null) {
            callback.call(ResultCode.ERROR_KUAI_SHOU, "Auth by Xiaomi Kuaishou failed, errCode = " + i + " errMessage = " + s1, null);
        }
    }

    @Override
    public void onCancel() {
        Log.e(TAG, "Auth Canceled");
        if (callback != null) {
            callback.call(ResultCode.ERROR_KUAI_SHOU, "Auth by Xiaomi Kuaishou canceled", null);
        }
    }
}
