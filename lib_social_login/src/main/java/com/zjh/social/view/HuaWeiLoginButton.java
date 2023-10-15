package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.HuaweiLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.HuaweiParams;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class HuaWeiLoginButton extends SocialLoginButton<HuaweiParams>{
    public HuaWeiLoginButton(@NonNull Context context) {
        super(context);
    }

    public HuaWeiLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HuaWeiLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<HuaweiParams> createAuthenticator() {
        return HuaweiLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_huawei;
    }
}
