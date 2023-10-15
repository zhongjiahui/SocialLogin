package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.SlackLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.SlackParams;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
**/
public class SlackLoginButton extends SocialLoginButton<SlackParams>{

    public SlackLoginButton(@NonNull Context context) {
        super(context);
    }

    public SlackLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlackLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<SlackParams> createAuthenticator() {
        return SlackLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_slack;
    }
}
