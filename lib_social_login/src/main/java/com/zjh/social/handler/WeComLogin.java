package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tencent.wework.api.IWWAPI;
import com.tencent.wework.api.WWAPIFactory;
import com.tencent.wework.api.model.WWAuthMessage;
import com.zjh.social.R;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.WeComParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class WeComLogin extends SocialAuthenticator<WeComParams>{

    private static final String TAG = "WeComLogin";

    private WeComLogin() {
    }

    @SuppressLint("StaticFieldLeak")
    private static final class WeComInstanceHolder {
        static final WeComLogin mInstance = new WeComLogin();
    }

    public static WeComLogin getInstance() {
        return WeComLogin.WeComInstanceHolder.mInstance;
    }

    @Override
    public void startAuth(@NonNull Context context, WeComParams params, @NonNull AuthCallback<Object> callback) {
        IWWAPI iwwapi = WWAPIFactory.createWWAPI(context);

        String sch = params.getSchema();
        if (TextUtils.isEmpty(sch)){
            callback.call(ResultCode.ERROR_PARAMS, "schema 不能为空", null);
            return;
        }
        iwwapi.registerApp(sch);

        final WWAuthMessage.Req req = new WWAuthMessage.Req();
        req.sch = sch;
        req.agentId = params.getAgentId();
        req.appId = params.getCorpId();
        req.state = params.getState();
        iwwapi.sendMessage(req, resp -> {
            if (resp instanceof WWAuthMessage.Resp) {
                WWAuthMessage.Resp rsp = (WWAuthMessage.Resp) resp;
                if (rsp.errCode == WWAuthMessage.ERR_CANCEL) {
                    Log.i(TAG, "Cancelled");
                    fireCallback(callback, "Cancelled");
                } else if (rsp.errCode == WWAuthMessage.ERR_FAIL) {
                    Log.i(TAG, "Auth failed");
                    fireCallback(callback, "Auth failed");
                } else if (rsp.errCode == WWAuthMessage.ERR_OK) {
                    Log.i(TAG, "Auth success");
                    callback.call(ResultCode.AUTH_SUCCESS, "Auth success", rsp.code);
                } else {
                    Log.e(TAG, "Auth failed");
                    fireCallback(callback, "Auth failed");
                }
            } else {
                Log.e(TAG, "Auth failed, resp is null");
                fireCallback(callback, "Auth failed");
            }
        });
    }

    private void fireCallback(AuthCallback<Object> callback, String message) {
        if (callback != null) {
            callback.call(500, message, null);
        }
    }
}
