package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.AmazonLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.AmazonParams;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class AmazonLoginButton extends SocialLoginButton<AmazonParams>{

    public AmazonLoginButton(@NonNull Context context) {
        super(context);
    }

    public AmazonLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AmazonLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<AmazonParams> createAuthenticator() {
        return AmazonLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_amazon;
    }
}
