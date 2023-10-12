package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.LinkedinLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.LinkedinParams;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class LinkedinLoginButton extends SocialLoginButton<LinkedinParams> {

    public LinkedinLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public LinkedinLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinkedinLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<LinkedinParams> createAuthenticator() {
        return LinkedinLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_linkedin;
    }
}
