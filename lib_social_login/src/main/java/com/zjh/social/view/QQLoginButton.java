package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.QQLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.QQParams;

/**
 * @author Administrator
 * @date 2023/9/28
 * @description
 **/
public class QQLoginButton extends SocialLoginButton<QQParams>{


    public QQLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public QQLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<QQParams> createAuthenticator() {
        return QQLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_qq;
    }
}
