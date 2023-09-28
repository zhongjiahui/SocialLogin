package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.AlipayLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.AlipayParams;

/**
 * @author Administrator
 * @date 2023/9/21
 * @description 支付宝登录按钮
 **/
public class AlipayLoginButton extends SocialLoginButton<AlipayParams>{

    public AlipayLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public AlipayLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlipayLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<AlipayParams> createAuthenticator() {
        return AlipayLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_alipay;
    }
}
