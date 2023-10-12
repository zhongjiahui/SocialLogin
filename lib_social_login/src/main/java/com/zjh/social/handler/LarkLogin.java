package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ss.android.larksso.CallBackData;
import com.ss.android.larksso.IGetDataCallback;
import com.ss.android.larksso.LarkSSO;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.LarkParams;
import com.zjh.social.util.ResultCode;

import java.util.ArrayList;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class LarkLogin extends SocialAuthenticator<LarkParams>{

    private static final String TAG = "LarkLogin";
    private LarkLogin() {
    }

    public static LarkLogin getInstance() {
        return LarkLogin.LarkInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class LarkInstanceHolder {
        static final LarkLogin mInstance = new LarkLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, LarkParams params, @NonNull AuthCallback<Object> callback) {
        String appId = params.getAppId();
        if (TextUtils.isEmpty(appId)) {
            callback.call(ResultCode.ERROR_PARAMS, "appId 不能为空", null);
            return;
        }

        ArrayList<String> scopeList = new ArrayList<>();
        scopeList.add(params.getScope());
        LarkSSO.Builder builder = new LarkSSO.Builder().setAppId(appId)
                .setServer("Feishu")
                .setScopeList(scopeList)
                .setContext((Activity) context);

        LarkSSO.inst().startSSOVerify(builder, new IGetDataCallback() {

            @Override
            public void onSuccess(CallBackData callBackData) {
                if (null == callBackData) {
                    Log.e(TAG, "Auth Failed, callBackData is null");
                    return;
                }
                Log.i(TAG, "Auth success");
                callback.call(ResultCode.AUTH_SUCCESS, "Auth success", callBackData.code);
            }

            @Override
            public void onError(CallBackData callBackData) {
                String errorMessage = "Auth failed";
                if ("-1".equals(callBackData.code)) {
                    errorMessage = "状态码校验失败，非当前SDK请求的响应";
                } else if ("-2".equals(callBackData.code)) {
                    errorMessage = "没有获得有效的授权码";
                } else if ("-3".equals(callBackData.code)) {
                    errorMessage = "Cancelled";
                } else if ("-4".equals(callBackData.code)) {
                    errorMessage = "跳转飞书失败";
                } else if ("-5".equals(callBackData.code)) {
                    errorMessage = "授权失败";
                } else if ("-6".equals(callBackData.code)) {
                    errorMessage = "请求参数错误";
                }
                Log.e(TAG, "Auth Failed, errorCode is: " + callBackData.code + ",errorMessage is: " + errorMessage);
                callback.call(Integer.parseInt(callBackData.code), errorMessage, null);
            }
        });
    }

    public void onResume(Activity activity) {
        try {
            Class.forName("com.ss.android.larksso.LarkSSO");
            LarkSSO.inst().parseIntent(activity, activity.getIntent());
        } catch (ClassNotFoundException e) {
            //ALog.e(TAG, e.toString());
        }
    }

    public void onNewIntent(Activity activity, Intent intent) {
        try {
            Class.forName("com.ss.android.larksso.LarkSSO");
            LarkSSO.inst().parseIntent(activity, intent);
        } catch (ClassNotFoundException e) {
            //ALog.e(TAG, e.toString());
        }
    }

    public void onActivityResult(Activity activity, @Nullable Intent data) {
        try {
            Class.forName("com.ss.android.larksso.LarkSSO");
            LarkSSO.inst().parseIntent(activity, data);
        } catch (ClassNotFoundException e) {
            //ALog.e(TAG, e.toString());
        }
    }
}
