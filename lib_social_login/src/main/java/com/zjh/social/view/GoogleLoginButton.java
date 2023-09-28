package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.GoogleLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.GoogleParams;

/**
 * @author Administrator
 * @date 2023/9/28
 * @description
 **/
public class GoogleLoginButton extends SocialLoginButton<GoogleParams>{

    public GoogleLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public GoogleLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoogleLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<GoogleParams> createAuthenticator() {
        return GoogleLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_google;
    }
}
