package com.zjh.social.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.TwitterParams;
import com.zjh.social.util.ResultCode;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class TwitterLogin extends SocialAuthenticator<TwitterParams>{

    private static final String TAG = "TwitterLogin";
    private TwitterAuthClient mTwitterAuthClient;

    private TwitterLogin() {
    }

    public static TwitterLogin getInstance() {
        return TwitterInstanceHolder.mInstance;
    }

    private static final class TwitterInstanceHolder {
        static final TwitterLogin mInstance = new TwitterLogin();
    }

    @Override
    public void startAuth(@NonNull Context context, TwitterParams params, @NonNull AuthCallback<Object> callback) {
        String consumerKey = params.getConsumerKey();
        if (TextUtils.isEmpty(consumerKey)) {
            callback.call(ResultCode.ERROR_PARAMS, "consumerKey 不能为空", null);
            return;
        }
        String consumerSecret = params.getConsumerSecret();
        TwitterConfig twitterConfig = new TwitterConfig.Builder(context)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(consumerKey, consumerSecret))
                .debug(true)
                .build();
        com.twitter.sdk.android.core.Twitter.initialize(twitterConfig);
        if (mTwitterAuthClient != null) {
            mTwitterAuthClient.cancelAuthorize();
        }
        mTwitterAuthClient = new TwitterAuthClient();
        mTwitterAuthClient.authorize((Activity) context, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.i(TAG, "Auth onSuccess");
                TwitterAuthToken authToken = result.data.getAuthToken();
                String token = authToken.token;
                String tokenSecret = authToken.secret;
                callback.call(ResultCode.AUTH_SUCCESS, "Auth onSuccess", "token = " + token + " tokenSecret = " + tokenSecret);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.i(TAG, "Auth Failed, errorMessage is ：" + exception.toString());
                callback.call(ResultCode.ERROR_TWITTER, "Login by Twitter failed", null);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mTwitterAuthClient != null) {
            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
        }
    }
}
