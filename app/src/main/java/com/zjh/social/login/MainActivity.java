package com.zjh.social.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zjh.social.callback.AuthCallback;
import com.zjh.social.handler.FacebookLogin;
import com.zjh.social.handler.GiteeLogin;
import com.zjh.social.handler.GithubLogin;
import com.zjh.social.handler.GoogleLogin;
import com.zjh.social.handler.LinkedinLogin;
import com.zjh.social.handler.QQLogin;
import com.zjh.social.handler.WeiboLogin;
import com.zjh.social.login.databinding.ActivityMainBinding;
import com.zjh.social.params.AlipayParams;
import com.zjh.social.params.BaiduParams;
import com.zjh.social.params.FacebookParams;
import com.zjh.social.params.GiteeParams;
import com.zjh.social.params.GithubParams;
import com.zjh.social.params.GoogleParams;
import com.zjh.social.params.LinkedinParams;
import com.zjh.social.params.QQParams;
import com.zjh.social.params.WechatParams;
import com.zjh.social.params.WeiboParams;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initWechatLogin();
        initAlipayLogin();
        initQQLogin();
        initWeiboLogin();
        initBaiduLogin();
        initGiteeLogin();

        initGoogleLogin();
        initFacebookLogin();
        initLinkedinLogin();
        initGithubLogin();
    }




    private void initWechatLogin(){
        WechatParams wechatParams = new WechatParams();
        wechatParams.setAppId("wx1cddb15e280c0f67");
        binding.wechatLogin.setOnLoginListener(wechatParams, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initAlipayLogin() {
        AlipayParams params = new AlipayParams();
        params.setAppId("wx1cddb15e280c0f67");
        binding.alipayLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initQQLogin() {
        QQParams params = new QQParams();
        params.setAppId("wx1cddb15e280c0f67");
        binding.qqLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initWeiboLogin() {
        WeiboParams params = new WeiboParams();
        params.setAppKey("wx1cddb15e280c0f67");
        binding.weiboLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initBaiduLogin() {
        BaiduParams params = new BaiduParams();
        params.setAppKey("wx1cddb15e280c0f67");
        binding.baiduLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initGiteeLogin() {
        GiteeParams params = new GiteeParams();
        params.setClientId("wx1cddb15e280c0f67");
        binding.giteeLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }



    private void initGoogleLogin() {
        GoogleParams params = new GoogleParams();
        params.setServerClientIdl("wx1cddb15e280c0f67");
        binding.googleLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initFacebookLogin() {
        FacebookParams params = new FacebookParams();
        binding.facebookLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initLinkedinLogin() {
        LinkedinParams params = new LinkedinParams();
        params.setAppKey("wx1cddb15e280c0f67");
        binding.linkedinLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }


    private void initGithubLogin() {
        GithubParams params = new GithubParams();
        params.setClientId("wx1cddb15e280c0f67");
        binding.githubLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }


    private void printLog(int code, String message, Object data){
        Log.e(TAG, "call: code = " + code + " \nmessage = " + message + " \ndata = " + data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleLogin.getInstance().onActivityResult(requestCode, resultCode, data);
        QQLogin.getInstance().onActivityResult(requestCode, resultCode, data);
        FacebookLogin.getInstance().onActivityResult(requestCode, resultCode, data);
        WeiboLogin.getInstance().onActivityResult(this, requestCode, resultCode, data);
        LinkedinLogin.getInstance().onActivityResult(requestCode, resultCode, data);
        GithubLogin.getInstance().onActivityResult(requestCode, resultCode, data);
        GiteeLogin.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}