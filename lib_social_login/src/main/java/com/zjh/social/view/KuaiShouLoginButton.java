package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.KuaiShouLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.KuaiShouParams;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class KuaiShouLoginButton extends SocialLoginButton<KuaiShouParams>{

    public KuaiShouLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public KuaiShouLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KuaiShouLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<KuaiShouParams> createAuthenticator() {
        return KuaiShouLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_kuaishou;
    }
}
