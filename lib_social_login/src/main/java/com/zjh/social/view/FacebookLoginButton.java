package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.FacebookParams;

/**
 * @author Administrator
 * @date 2023/10/11
 * @description
 **/
public class FacebookLoginButton extends SocialLoginButton<FacebookParams>{

    public FacebookLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public FacebookLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FacebookLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<FacebookParams> createAuthenticator() {
        return null;
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_facebook;
    }
}
