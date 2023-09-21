package com.zjh.social.handler;

import android.content.Context;

import com.zjh.social.callback.AuthCallback;

import org.jetbrains.annotations.NotNull;

/**
 * @author Administrator
 * @date 2023/9/19
 * @description
 **/
public abstract class SocialAuthenticator<P> {

    public abstract void startAuth(@NotNull Context context, P params, @NotNull AuthCallback<Object> callback);

    public void onDetachedFromWindow() {

    }
}
