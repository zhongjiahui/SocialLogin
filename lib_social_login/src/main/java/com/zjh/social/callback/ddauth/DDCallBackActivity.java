package com.zjh.social.callback.ddauth;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zjh.social.handler.DingTalkLogin;


public class DDCallBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DingTalkLogin.getInstance().onActivityResult(getIntent());
        finish();
    }
}
