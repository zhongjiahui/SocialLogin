package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.handler.TwitterLogin;
import com.zjh.social.params.TwitterParams;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class TwitterLoginButton extends SocialLoginButton<TwitterParams> {


    public TwitterLoginButton(@NonNull Context context) {
        super(context);
    }

    public TwitterLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TwitterLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<TwitterParams> createAuthenticator() {
        return TwitterLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_twitter;
    }
}
