package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.DingTalkLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.DingTalkParams;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class DingTalkLoginButton extends SocialLoginButton<DingTalkParams>{

    public DingTalkLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public DingTalkLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DingTalkLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<DingTalkParams> createAuthenticator() {
        return DingTalkLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_ding_talk;
    }
}
