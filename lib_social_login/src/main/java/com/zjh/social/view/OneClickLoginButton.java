package com.zjh.social.view;


import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.OneClickLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.OneClickParams;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class OneClickLoginButton extends SocialLoginButton<OneClickParams> {

    public OneClickLoginButton(@NonNull Context context) {
        super(context);
    }

    public OneClickLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OneClickLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<OneClickParams> createAuthenticator() {
        return OneClickLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_phone;
    }
}
