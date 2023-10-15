package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import androidx.annotation.NonNull;

import com.amazon.identity.auth.device.AuthError;
import com.amazon.identity.auth.device.api.Listener;
import com.amazon.identity.auth.device.api.authorization.AuthCancellation;
import com.amazon.identity.auth.device.api.authorization.AuthorizationManager;
import com.amazon.identity.auth.device.api.authorization.AuthorizeListener;
import com.amazon.identity.auth.device.api.authorization.AuthorizeRequest;
import com.amazon.identity.auth.device.api.authorization.AuthorizeResult;
import com.amazon.identity.auth.device.api.authorization.ProfileScope;
import com.amazon.identity.auth.device.api.workflow.RequestContext;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.AmazonParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class AmazonLogin extends SocialAuthenticator<AmazonParams>{

    private static final String TAG = "Amazon";
    private RequestContext requestContext;
    private Activity activity;

    private AmazonLogin() {
    }

    public static AmazonLogin getInstance() {
        return AmazonInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class AmazonInstanceHolder {
        static final AmazonLogin mInstance = new AmazonLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, AmazonParams params, @NonNull AuthCallback<Object> callback) {
        if (requestContext == null) {
            requestContext = RequestContext.create(context);
        }
        requestContext.registerListener(new AuthorizeListener() {

            /*授权已成功完成。*/
            @Override
            public void onSuccess(AuthorizeResult result) {
                /*您的应用现已获得请求范围授权*/
                Log.i(TAG, "Auth success");
                callback.call(ResultCode.AUTH_SUCCESS, "Auth success", result.getAccessToken());
            }

            /*尝试授权
            应用时出错。*/
            @Override
            public void onError(AuthError ae) {
                /*提示用户发生错误*/
                 Log.e(TAG, "Auth Failed, errCode = " + ae.getType() + " errMessage" + ae.getMessage());
                callback.call(ResultCode.ERROR_AMAZON, "Login by Amazon failed", null);
            }

            /*授权未完成便已取消。*/
            @Override
            public void onCancel(AuthCancellation cancellation) {
                /*将UI重新设置为随时登录状态*/
                Log.i(TAG, "Auth canceled");
                callback.call(ResultCode.ERROR_AMAZON, "Login by Amazon canceled", null);
            }
        });
        AuthorizationManager.authorize(new AuthorizeRequest
                .Builder(requestContext)
                .addScopes(ProfileScope.profile(), ProfileScope.userId(), ProfileScope.postalCode())
                .build());
    }


    public void onCreate(Context context) {
        this.activity = (Activity) context;
        try {
            Class.forName("com.amazon.identity.auth.device.api.workflow.RequestContext");
            requestContext = RequestContext.create(context);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.toString());
        }
    }

    public void onResume() {
        if (requestContext != null && activity != null && !activity.isDestroyed()) {
            requestContext.onResume();
        }
    }

    public void signOut(Context context) {
        AuthorizationManager.signOut(context.getApplicationContext(), new Listener<Void, AuthError>() {
            @Override
            public void onSuccess(Void response) {
                // 设置退出状态UI
            }

            @Override
            public void onError(AuthError authError) {
                // 记录错误
            }
        });
    }
}
