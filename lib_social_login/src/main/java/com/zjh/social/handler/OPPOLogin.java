package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.heytap.msp.oauth.OAuthConstants;
import com.heytap.msp.oauth.bean.OAuthCodeResponse;
import com.heytap.msp.oauth.bean.OAuthRequest;
import com.heytap.msp.result.BaseErrorCode;
import com.heytap.msp.sdk.OAuthSdk;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.OPPOParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class OPPOLogin extends SocialAuthenticator<OPPOParams>{
    private static final String TAG = "OPPOLogin";
    private OPPOLogin() {
    }

    public static OPPOLogin getInstance() {
        return OppoInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class OppoInstanceHolder {
        static final OPPOLogin mInstance = new OPPOLogin();
    }
    @Override
    public void startAuth(@NonNull Context context, OPPOParams params, @NonNull AuthCallback<Object> callback) {
        String appId = params.getAppId();
        if (TextUtils.isEmpty(appId)) {
            callback.call(ResultCode.ERROR_PARAMS, "appId 不能为空", null);
            return;
        }
        String scope = params.getScope();
        OAuthRequest request = new OAuthRequest();
        //设置您的AppID(开放平台创建应用时生成的)
        request.setAppId(appId);
        //设置您创建的应用类型(APP,H5,FAST)
        request.setAppType(OAuthConstants.AuthAppType.APP);
        //设置您的请求Tag(可以为空)
        request.setRequestTag("oppo");
        //设置您的授权Scope(openid,profile)
        //request.setScope(OAuthConstants.AuthScope.AUTH_SCOPE_PROFILE);
        request.setScope(scope);
        //设置您的授权显示界面类型(popup，page)
        request.setDisplay(OAuthConstants.AuthShowType.AUTH_SHOW_TYPE_PAGE);
        //设置您的授权prompt,prompt的详细介绍请参考表格----prompt字段介绍
        request.setPrompt(OAuthConstants.Prompt.NONE);
        //传入授权请求参数，调用授权API
        OAuthSdk.requestOauthCode(request, response -> {
            if (response.getCode() == BaseErrorCode.ERROR_SUCCESS) {
                OAuthCodeResponse oAuthCodeResponse = response.getResponse();
                if (oAuthCodeResponse.getCode() == null) {
                    Log.e(TAG, "Auth Failed, code is null");
                    callback.call(ResultCode.ERROR_OPPO, "Auth Failed, code is null", null);
                    return;
                }
                Log.i(TAG, "Auth success");
                callback.call(ResultCode.AUTH_SUCCESS, "Alipay auth success", oAuthCodeResponse.getCode());
            } else {
                Log.e(TAG, "Auth Failed, errorCode is: " + response.getMessage() + ",errorMessage is: " + response.getMessage());
                callback.call(Integer.parseInt(response.getMessage()), response.getMessage(), null);
            }
        });
    }
}
