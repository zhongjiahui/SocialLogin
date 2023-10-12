package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.DouYinLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.DouYinParams;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class DouYinLoginButton extends SocialLoginButton<DouYinParams>{
    public DouYinLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public DouYinLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DouYinLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<DouYinParams> createAuthenticator() {
        return DouYinLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_douyin;
    }
}
