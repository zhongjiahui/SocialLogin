package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.handler.XiaomiLogin;
import com.zjh.social.params.XiaomiParams;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class XiaomiLoginButton extends SocialLoginButton<XiaomiParams>{

    public XiaomiLoginButton(@NonNull Context context) {
        super(context);
    }

    public XiaomiLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XiaomiLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<XiaomiParams> createAuthenticator() {
        return XiaomiLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_xiaomi;
    }
}
