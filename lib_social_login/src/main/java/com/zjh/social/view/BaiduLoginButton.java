package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.BaiduLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.BaiduParams;

/**
 * @author Administrator
 * @date 2023/10/11
 * @description
 **/
public class BaiduLoginButton extends SocialLoginButton<BaiduParams>{
    public BaiduLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public BaiduLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaiduLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<BaiduParams> createAuthenticator() {
        return BaiduLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_baidu;
    }
}
