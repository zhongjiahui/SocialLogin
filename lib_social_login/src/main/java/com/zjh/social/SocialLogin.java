package com.zjh.social;

import android.annotation.SuppressLint;
import android.app.Application;

import com.heytap.msp.sdk.SdkAgent;
import com.kwai.auth.KwaiAuthAPI;

/**
 * @author Administrator
 * @date 2023/10/12
 * @description
 **/
public class SocialLogin {

    private SocialLogin() {
    }

    public static SocialLogin getInstance() {
        return SocialLogin.SocialLoginInstanceHolder.mInstance;
    }

    @SuppressLint("StaticFieldLeak")
    private static final class SocialLoginInstanceHolder {
        static final SocialLogin mInstance = new SocialLogin();
    }

    public void initKuaiShou(Application context){
        KwaiAuthAPI.init(context);
    }

    public void initOPPO(Application context){
        SdkAgent.init(context);
    }
}
