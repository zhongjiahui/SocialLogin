package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.LarkLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.LarkParams;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class LarkLoginButton extends SocialLoginButton<LarkParams>{

    public LarkLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public LarkLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LarkLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<LarkParams> createAuthenticator() {
        return LarkLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_lark;
    }
}
