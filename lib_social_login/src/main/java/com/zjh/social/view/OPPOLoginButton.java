package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.OPPOLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.OPPOParams;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class OPPOLoginButton extends SocialLoginButton<OPPOParams>{

    public OPPOLoginButton(@NonNull Context context) {
        super(context);
    }

    public OPPOLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OPPOLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<OPPOParams> createAuthenticator() {
        return OPPOLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_oppo;
    }
}
