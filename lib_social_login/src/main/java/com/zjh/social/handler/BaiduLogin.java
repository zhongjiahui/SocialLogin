package com.zjh.social.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.baidu.api.Baidu;
import com.baidu.api.BaiduDialog;
import com.baidu.api.BaiduDialogError;
import com.baidu.api.BaiduException;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.BaiduParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/10/11
 * @description
 **/
public class BaiduLogin extends SocialAuthenticator<BaiduParams>{

    private static final String TAG = "BaiduLogin";
    private Baidu baidu;

    private BaiduLogin() {
    }

    public static BaiduLogin getInstance() {
        return BaiduInstanceHolder.mInstance;
    }

    private static final class BaiduInstanceHolder {
        static final BaiduLogin mInstance = new BaiduLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, BaiduParams params, @NonNull AuthCallback<Object> callback) {
        String appKey = params.getAppKey();
        if (TextUtils.isEmpty(appKey)) {
            callback.call(ResultCode.ERROR_PARAMS, "appKey 不能为空", null);
            return;
        }
        baidu = new Baidu(appKey, context);
        baidu.authorize((Activity) context, false, true, new BaiduDialog.BaiduDialogListener() {
            @Override
            public void onComplete(Bundle bundle) {
                Log.i(TAG, "Auth onSuccess");
                String access_token;
                if (bundle.containsKey("access_token")) {
                    access_token = bundle.getString("access_token");
                } else {
                    access_token = baidu.getAccessToken();
                }
                callback.call(ResultCode.AUTH_SUCCESS, "Baidu auth success", access_token);
            }

            @Override
            public void onBaiduException(BaiduException e) {
                Log.e(TAG, "Auth Failed, onError");
                callback.call(ResultCode.ERROR_BAIDU, "Auth by Baidu failed", null);
            }

            @Override
            public void onError(BaiduDialogError baiduDialogError) {
                Log.e(TAG, "Auth Failed, onError");
                callback.call(ResultCode.ERROR_BAIDU, "Auth by Baidu failed", null);
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "Auth Failed, onCancel");
                callback.call(ResultCode.ERROR_BAIDU, "Auth by Baidu canceled", null);
            }
        });
    }
}
