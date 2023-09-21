package com.zjh.social.callback.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.handler.WechatLogin;
import com.zjh.social.view.SocialLoginButton;

/**
 * @author Administrator
 * @date 2023/9/19
 * @description
 **/
public class WXCallbackActivity extends AppCompatActivity implements IWXAPIEventHandler {

    public static final String TAG = WXCallbackActivity.class.getSimpleName();

    private static AuthCallback<Object> callBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WechatLogin.api = WXAPIFactory.createWXAPI(this, WechatLogin.appId);
        WechatLogin.api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "onReq: ");
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.i(TAG, "wechat auth success");
                callBack.call(SocialLoginButton.AUTH_SUCCESS, "Auth success", null);
                handlerWechat(resp);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.d(TAG, "wechat user canceled");
                if (callBack != null) {
                    callBack.call(BaseResp.ErrCode.ERR_USER_CANCEL, "canceled", null);
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.w(TAG, "wechat user denied auth");
                if (callBack != null) {
                    callBack.call(BaseResp.ErrCode.ERR_AUTH_DENIED, "denied", null);
                }
                break;
            default:
                break;
        }

        finish();
    }

    private void handlerWechat(BaseResp resp){
        SendAuth.Resp sendAuthResp = (SendAuth.Resp) resp;
        String code = sendAuthResp.code;
        Log.d(TAG, "wechat code: " + code);
        if (callBack != null) {
            callBack.call(200, "success", code);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        callBack = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WechatLogin.api.handleIntent(intent, this);
    }

    public static void setCallback(AuthCallback<Object> callback) {
        WXCallbackActivity.callBack = callback;
    }
}
