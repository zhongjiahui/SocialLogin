package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.GiteeLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.GiteeParams;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class GiteeLoginButton extends SocialLoginButton<GiteeParams>{

    public GiteeLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public GiteeLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GiteeLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<GiteeParams> createAuthenticator() {
        return GiteeLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_gitee;
    }
}
