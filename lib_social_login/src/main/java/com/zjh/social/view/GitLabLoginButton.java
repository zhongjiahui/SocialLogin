package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.GitLabLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.GitLabParams;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class GitLabLoginButton extends SocialLoginButton<GitLabParams>{
    public GitLabLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public GitLabLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GitLabLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<GitLabParams> createAuthenticator() {
        return GitLabLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_gitlab;
    }
}
