package com.zjh.social.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zjh.social.R;
import com.zjh.social.handler.LineLogin;
import com.zjh.social.handler.SocialAuthenticator;
import com.zjh.social.params.LineParams;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class LineLoginButton extends SocialLoginButton<LineParams>{

    public LineLoginButton(@NonNull Context context) {
        this(context, null);
    }

    public LineLoginButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineLoginButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected SocialAuthenticator<LineParams> createAuthenticator() {
        return LineLogin.getInstance();
    }

    @Override
    protected int getImageRes() {
        return R.drawable.ic_line;
    }
}
