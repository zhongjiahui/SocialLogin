package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.api.entity.auth.Scope;
import com.huawei.hms.support.api.entity.common.CommonConstant;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.HuaweiParams;
import com.zjh.social.util.RequestCode;
import com.zjh.social.util.ResultCode;

import java.util.List;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class HuaweiLogin extends SocialAuthenticator<HuaweiParams>{

    private static final String TAG = "HuaWei";
    // 华为帐号登录授权服务，提供静默登录接口silentSignIn，获取前台登录视图getSignInIntent，登出signOut等接口
    private AccountAuthService mAuthService;
    // 华为帐号登录授权参数
    private AccountAuthParams mAuthParam;
    private AuthCallback<Object> callback;

    private HuaweiLogin() {
    }

    public static HuaweiLogin getInstance() {
        return HuaWeiInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class HuaWeiInstanceHolder {
        static final HuaweiLogin mInstance = new HuaweiLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, HuaweiParams params, @NonNull AuthCallback<Object> callback) {
        this.callback = callback;
        // 1、配置登录请求参数AccountAuthParams，包括请求用户的id(openid、unionid)、email、profile(昵称、头像)等;
        // 2、DEFAULT_AUTH_REQUEST_PARAM默认包含了id和profile（昵称、头像）的请求;
        // 3、如需要再获取用户邮箱，需要setEmail();
        // 4、通过setAuthorizationCode()来选择使用code模式，最终所有请求的用户信息都可以调服务器的接口获取；
        List<Scope> scopes = params.getScopes();
        if (scopes == null) {
            mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                    .setEmail()
                    .setProfile()
                    //.setMobileNumber()
                    .setAuthorizationCode()
                    .createParams();
        } else {
            mAuthParam = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                    .setScopeList(scopes)
                    .setAuthorizationCode()
                    .createParams();
        }

        // 使用请求参数构造华为帐号登录授权服务AccountAuthService
        mAuthService = AccountAuthManager.getService(context, mAuthParam);

        // 使用静默登录进行华为帐号登录
        Task<AuthAccount> task = mAuthService.silentSignIn();
        task.addOnSuccessListener(authAccount -> {
            // 静默登录成功，处理返回的帐号对象AuthAccount，获取帐号信息并处理
            if (authAccount.getAuthorizationCode() == null) {
                Log.e(TAG, "Auth Failed, code is null");
                callback.call(ResultCode.ERROR_HUAWEI, "Auth Failed, code is null", null);
                return;
            }
            callback.call(ResultCode.AUTH_SUCCESS, "Huawei auth success", authAccount.getAuthorizationCode());
        });
        task.addOnFailureListener(e -> {
            // 静默登录失败，使用getSignInIntent()方法进行前台显式登录
            if (e instanceof ApiException) {
                ApiException apiException = (ApiException) e;
                Intent signInIntent = mAuthService.getSignInIntent();
                // 如果应用是全屏显示，即顶部无状态栏的应用，需要在Intent中添加如下参数：
                // intent.putExtra(CommonConstant.RequestParams.IS_FULL_SCREEN, true);
                // 具体详情可以参见应用调用登录接口的时候是全屏页面，为什么在拉起登录页面的过程中顶部的状态栏会闪一下？应该如何解决？
                signInIntent.putExtra(CommonConstant.RequestParams.IS_FULL_SCREEN, true);
                ((Activity) context).startActivityForResult(signInIntent, RequestCode.REQUEST_HUAWEI);
            } else {
                Log.e(TAG, "Auth Failed, onError = " + e.toString());
                callback.call(ResultCode.ERROR_HUAWEI, "Auth by Huawei failed", null);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCode.REQUEST_HUAWEI) {
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                // 登录成功，获取到登录帐号信息对象authAccount
                Log.i(TAG, "Auth onSuccess");
                AuthAccount authAccount = authAccountTask.getResult();
                if (authAccount.getAuthorizationCode() == null) {
                    Log.e(TAG, "Auth Failed, code is null");
                    callback.call(ResultCode.ERROR_HUAWEI, "Auth Failed, code is null", null);
                    return;
                }
                callback.call(ResultCode.AUTH_SUCCESS, "Huawei auth success", authAccount.getAuthorizationCode());
            } else {
                // 登录失败，status code标识了失败的原因，请参见API参考中的错误码了解详细错误原因
                Log.e(TAG, "Auth Failed, onError errorCode = " + ((ApiException) authAccountTask.getException()).getStatusCode()
                        + " errorMsg = " + ((ApiException) authAccountTask.getException()).getStatusMessage());
                if (callback != null) {
                    callback.call(ResultCode.ERROR_HUAWEI, "Login by Huawei failed", null);
                }
            }
        }
    }
}
