package com.zjh.social.callback.douyinapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bytedance.sdk.open.aweme.CommonConstants;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.util.ResultCode;
import com.zjh.social.view.SocialLoginButton;


public class DouYinCallBackActivity extends Activity implements IApiEventHandler {

    public static final String TAG = DouYinCallBackActivity.class.getSimpleName();
    private static AuthCallback<Object> callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DouYinOpenApi douYinOpenApi = DouYinOpenApiFactory.create(this);
        if (douYinOpenApi != null){
            douYinOpenApi.handleIntent(getIntent(), this);
        }
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        // 授权成功可以获得authCode
        if (resp.getType() == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            Authorization.Response response = (Authorization.Response) resp;
            if (resp.isSuccess()) {
                Log.i(TAG, "Auth success");
                callback.call(SocialLoginButton.AUTH_SUCCESS, "Auth success", null);
                callback.call(ResultCode.AUTH_SUCCESS, "Dou yin auth success", response.authCode);
            } else {
                Log.e(TAG, "Auth Failed: errorCode = " + response.errorCode + " errorMsg = " + response.errorMsg);
                callback.call(ResultCode.ERROR_DOU_YIN, "Auth by dou yin failed", null);
            }
            finish();
        }
    }

    @Override
    public void onErrorIntent(@Nullable Intent intent) {
        // 错误数据
        if (callback != null) {
            Log.e(TAG, "Auth Failed");
            callback.call(ResultCode.ERROR_DOU_YIN, "Auth by dou yin failed", null);
        }
        finish();
    }

    public static void setCallback(AuthCallback<Object> callback) {
        DouYinCallBackActivity.callback = callback;
    }

}
