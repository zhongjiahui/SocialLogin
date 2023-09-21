package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.handler.WechatLogin;
import com.zjh.social.params.WechatParams;

/**
 * @author Administrator
 * @date 2023/9/19
 * @description 微信登录按钮
 **/
public class WechatLoginButton extends SocialLoginButton<WechatParams> {

    public WechatLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public WechatLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WechatLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<WechatParams> createAuthenticator() {
        return WechatLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_wechat;
    }
}
