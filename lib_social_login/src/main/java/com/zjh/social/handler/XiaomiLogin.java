package com.zjh.social.handler;

import android.accounts.OperationCanceledException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.xiaomi.account.openauth.XMAuthericationException;
import com.xiaomi.account.openauth.XiaomiOAuthFuture;
import com.xiaomi.account.openauth.XiaomiOAuthResults;
import com.xiaomi.account.openauth.XiaomiOAuthorize;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.XiaomiParams;
import com.zjh.social.util.ResultCode;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class XiaomiLogin extends SocialAuthenticator<XiaomiParams>{
    private static final String TAG = "Xiaomi";

    private XiaomiOAuthResults results;
    private AsyncTask waitResultTask;
    private final Executor mExecutor = Executors.newCachedThreadPool();
    private AuthCallback<Object> callback;

    private XiaomiLogin() {
    }

    public static XiaomiLogin getInstance() {
        return XiaomiInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class XiaomiInstanceHolder {
        static final XiaomiLogin mInstance = new XiaomiLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, XiaomiParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;
        long appId = params.getAppId();
        String redirectUri = params.getRedirectUri();
        int[] scope = params.getScope();
        String state = params.getState();

        XiaomiOAuthFuture<XiaomiOAuthResults> future = new XiaomiOAuthorize()
                .setAppId(appId)
                .setRedirectUrl(redirectUri)
                .setScope(scope)
                .setState(state)
                //.setKeepCookies(false) // 不调的话默认是false
                //.setNoMiui(false) // 不调的话默认是false
                //.setSkipConfirm(false) // 不调的话默认是false
                //.setPhoneNumAutoFill(context.getApplicationContext(), true)
                .setSkipConfirm(true)
                .startGetOAuthCode((Activity) context);
        waitAndShowFutureResult(future);
    }

    @SuppressLint("StaticFieldLeak")
    private <V> void waitAndShowFutureResult(final XiaomiOAuthFuture<V> future) {
        waitResultTask = new AsyncTask<Void, Void, V>() {
            Exception e;

            @Override
            protected void onPreExecute() {
            }

            @Override
            protected V doInBackground(Void... params) {
                V v = null;
                try {
                    v = future.getResult();
                } catch (IOException | OperationCanceledException | XMAuthericationException e1) {
                    this.e = e1;
                }
                return v;
            }

            @Override
            protected void onPostExecute(V v) {
                if (v != null) {
                    if (v instanceof XiaomiOAuthResults) {
                        results = (XiaomiOAuthResults) v;
                    }
                    Log.i(TAG, "Auth success");
                    callback.call(ResultCode.AUTH_SUCCESS, "Xiaomi auth success", results.getCode());
                } else if (e != null) {
                    Log.e(TAG, "Auth Failed, errorMsg  = " + e);
                    if (callback != null) {
                        callback.call(ResultCode.ERROR_XIAOMI, "Auth by Xiaomi failed", null);
                    }
                } else {
                    Log.e(TAG, "Auth Canceled");
                    if (callback != null) {
                        callback.call(ResultCode.ERROR_XIAOMI, "Auth by Xiaomi canceled", null);
                    }
                }
            }
        }.executeOnExecutor(mExecutor);
    }
}
