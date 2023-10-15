package com.zjh.social.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zjh.social.callback.AuthCallback;
import com.zjh.social.handler.AmazonLogin;
import com.zjh.social.handler.FacebookLogin;
import com.zjh.social.handler.GitLabLogin;
import com.zjh.social.handler.GiteeLogin;
import com.zjh.social.handler.GithubLogin;
import com.zjh.social.handler.GoogleLogin;
import com.zjh.social.handler.LarkLogin;
import com.zjh.social.handler.LineLogin;
import com.zjh.social.handler.LinkedinLogin;
import com.zjh.social.handler.QQLogin;
import com.zjh.social.handler.SlackLogin;
import com.zjh.social.handler.WeiboLogin;
import com.zjh.social.login.databinding.ActivityMainBinding;
import com.zjh.social.params.AlipayParams;
import com.zjh.social.params.AmazonParams;
import com.zjh.social.params.BaiduParams;
import com.zjh.social.params.DingTalkParams;
import com.zjh.social.params.DouYinParams;
import com.zjh.social.params.FacebookParams;
import com.zjh.social.params.GitLabParams;
import com.zjh.social.params.GiteeParams;
import com.zjh.social.params.GithubParams;
import com.zjh.social.params.GoogleParams;
import com.zjh.social.params.KuaiShouParams;
import com.zjh.social.params.LarkParams;
import com.zjh.social.params.LineParams;
import com.zjh.social.params.LinkedinParams;
import com.zjh.social.params.QQParams;
import com.zjh.social.params.SlackParams;
import com.zjh.social.params.WeComParams;
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
        initDouYinLogin();
        initKuaiShouLogin();
        initWeComLogin();
        initLarkLogin();
        initDingTalkLogin();

        initGoogleLogin();
        initFacebookLogin();
        initLinkedinLogin();
        initGithubLogin();
        initGitLabLogin();
        initLineLogin();
        initSlackLogin();
        initAmazonLogin();
        AmazonLogin.getInstance().onCreate(this);

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

    private void initDouYinLogin() {
        DouYinParams params = new DouYinParams();
        params.setClientKey("wx1cddb15e280c0f67");
        binding.douyinLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initKuaiShouLogin() {
        KuaiShouParams params = new KuaiShouParams();
        binding.kuaishouLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initWeComLogin() {
        WeComParams params = new WeComParams();
        binding.wecomLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initLarkLogin() {
        LarkParams params = new LarkParams();
        binding.larkLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initDingTalkLogin() {
        DingTalkParams params = new DingTalkParams();
        binding.dingtalkLogin.setOnLoginListener(params, new AuthCallback<Object>() {
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

    private void initGitLabLogin() {
        GitLabParams params = new GitLabParams();
        params.setAppId("wx1cddb15e280c0f67");
        binding.gitlabLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initLineLogin() {
        LineParams params = new LineParams();
        params.setChannelID("wx1cddb15e280c0f67");
        binding.lineLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }

    private void initSlackLogin() {
        SlackParams params = new SlackParams();
        params.setClientId("wx1cddb15e280c0f67");
        binding.slackLogin.setOnLoginListener(params, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                printLog(code, message, data);

            }
        });
    }


    private void initAmazonLogin() {
        AmazonParams params = new AmazonParams();
        binding.amazonLogin.setOnLoginListener(params, new AuthCallback<Object>() {
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
    protected void onResume() {
        super.onResume();
        LarkLogin.getInstance().onResume(this);
        AmazonLogin.getInstance().onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LarkLogin.getInstance().onNewIntent(this, intent);
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
        GitLabLogin.getInstance().onActivityResult(requestCode, resultCode, data);
        LarkLogin.getInstance().onActivityResult(this, data);
        LineLogin.getInstance().onActivityResult(requestCode, resultCode, data);
        SlackLogin.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}