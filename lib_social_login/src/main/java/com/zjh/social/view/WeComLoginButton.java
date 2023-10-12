package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.handler.WeComLogin;
import com.zjh.social.params.WeComParams;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class WeComLoginButton extends SocialLoginButton<WeComParams>{

    public WeComLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public WeComLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeComLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<WeComParams> createAuthenticator() {
        return WeComLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_wecom;
    }
}
