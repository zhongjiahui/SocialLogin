package com.zjh.social.view;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import com.zjh.social.R;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.util.ToastUtil;


/**
 * @author Administrator
 * @date 2023/9/19
 * @description
 **/
public abstract class SocialLoginButton<P> extends AppCompatImageButton {

    protected SocialAuthenticator<P> authenticator;

    protected P params;
    protected AuthCallback<Object> callback;
    protected AnimatedVectorDrawable backgroundDrawable;
    public static final int AUTH_SUCCESS = 666;

    public SocialLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public SocialLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SocialLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setScaleType(ScaleType.CENTER_INSIDE);
        if (attrs == null || attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "background") == null) {
            setBackgroundResource(R.drawable.ic_rectangle);
        }
        setImageResource(getImageRes());

        backgroundDrawable = (AnimatedVectorDrawable) context.getDrawable(R.drawable.ic_animated_loading_blue);
        setOnClickListener((v -> {
            if (authenticator == null) {
                authenticator = createAuthenticator();
            }
            authenticator.startAuth(context, params, this::loginDone);
        }));
    }


    protected abstract SocialAuthenticator<P> createAuthenticator();

    protected abstract int getImageRes();

    private void loginDone(int code, String message, Object result) {
        if (code == AUTH_SUCCESS) {
            post(this::startLoading);
            return;
        }
        post(this::stopLoading);

        if (callback != null) {
            callback.call(code, message, result);
        } else {
            if (!TextUtils.isEmpty(message)
                    && getContext().getString(R.string.cancelled_by_user).equals(message)) {
                post(() -> ToastUtil.showCenter(getContext(), message));
            }
            Log.e("SocialLoginButton", "loginDone: errCode = " + code + " errMessage = " + message);
        }
    }

    private void startLoading() {
        setImageDrawable(backgroundDrawable);
        backgroundDrawable.start();
    }

    private void stopLoading() {
        backgroundDrawable.stop();
        setImageResource(getImageRes());
    }

    public void setOnLoginListener(P params, AuthCallback<Object> callback) {
        this.params = params;
        this.callback = callback;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (authenticator != null) {
            authenticator.onDetachedFromWindow();
        }
    }
}
