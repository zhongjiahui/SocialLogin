package com.zjh.social.login;

import android.app.Application;

import com.zjh.social.SocialLogin;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class SocialApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SocialLogin.getInstance().initKuaiShou(this);
    }
}
