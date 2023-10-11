package com.zjh.social.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.FacebookParams;
import com.zjh.social.util.ResultCode;

import java.util.Arrays;

/**
 * @author Administrator
 * @date 2023/10/11
 * @description
 **/
public class FacebookLogin extends SocialAuthenticator<FacebookParams>{

    private static final String TAG = "FacebookLogin";
    private CallbackManager callbackManager;

    private FacebookLogin() {
    }

    public static FacebookLogin getInstance() {
        return FacebookInstanceHolder.mInstance;
    }

    private static final class FacebookInstanceHolder {
        static final FacebookLogin mInstance = new FacebookLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, FacebookParams params, @NonNull AuthCallback<Object> callback) {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.i(TAG, "Auth onSuccess");
                        callback.call(ResultCode.AUTH_SUCCESS, "Facebook auth success", loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "Auth Failed, onCancel");
                        callback.call(ResultCode.ERROR_FACEBOOK, "Auth by FaceBook canceled", null);
                    }

                    @Override
                    public void onError(@NonNull FacebookException exception) {
                        Log.e(TAG, "Auth Failed, errorMessage is" + exception.getMessage());
                        callback.call(ResultCode.ERROR_FACEBOOK, "Auth by FaceBook failed", null);
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions((Activity) context, Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
