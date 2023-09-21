package com.zjh.social.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;
import android.util.Log;

import com.zjh.social.callback.AuthCallback;
import com.zjh.social.handler.WechatLogin;
import com.zjh.social.login.databinding.ActivityMainBinding;
import com.zjh.social.params.WechatParams;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initWechatLogin();

    }

    private void initWechatLogin(){
        WechatParams wechatParams = new WechatParams();
        wechatParams.setAppId("wx1cddb15e280c0f67");
        binding.wechatLogin.setOnLoginListener(wechatParams, new AuthCallback<Object>() {
            @Override
            public void call(int code, String message, Object data) {
                Log.e(TAG, "call: code = " + code + " \nmessage = " + message + " \ndata = " + data);

            }
        });
    }

}