package com.zjh.social.handler;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.netease.nis.quicklogin.QuickLogin;
import com.netease.nis.quicklogin.helper.UnifyUiConfig;
import com.netease.nis.quicklogin.listener.LoginListener;
import com.netease.nis.quicklogin.listener.QuickLoginPreMobileListener;
import com.netease.nis.quicklogin.listener.QuickLoginTokenListener;
import com.zjh.social.R;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.OneClickParams;
import com.zjh.social.util.ResultCode;
import com.zjh.social.util.Util;
import com.zjh.social.widget.TitleLayout;

import org.jetbrains.annotations.NotNull;

/**
 * @author Administrator
 * @date 2023/10/15
 * @description
 **/
public class OneClickLogin extends SocialAuthenticator<OneClickParams> {

    private static final String TAG = "OneClick";
    private static final int MSG_LOGIN = 1;

    private String businessId;

    private Context context;
    private final Handler handler;
    private UnifyUiConfig uiConfig;
    private AuthCallback<Object> callback;
    protected AnimatedVectorDrawable loadingDrawable;

    private int screenWidth; // dp

    private OneClickLogin() {
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == MSG_LOGIN)
                    startLogin(uiConfig, callback);
            }
        };
    }

    public static OneClickLogin getInstance() {
        return OneClickLogin.OneClickInstanceHolder.mInstance;
    }

    private static final class OneClickInstanceHolder {
        static final OneClickLogin mInstance = new OneClickLogin();
    }


    @Override
    public void startAuth(@NonNull Context context, OneClickParams params, @NonNull AuthCallback<Object> callback) {
        this.context = context.getApplicationContext();
        businessId = params.getBusinessId();
        start(callback);
    }

    public void start(@NotNull AuthCallback<Object> callback) {
        start(businessId, null, callback);
    }

    public void start(UnifyUiConfig config, @NotNull AuthCallback<Object> callback) {
        start(businessId, config, callback);
    }

    public void start(String bid, UnifyUiConfig uiConfig, @NotNull AuthCallback<Object> callback) {
        String _bid = TextUtils.isEmpty(bid) ? businessId : bid;
        this.uiConfig = uiConfig;
        this.callback = callback;

        getAndroidScreenProperty();
        prefetchMobileNumber(_bid, null);
    }

    public void getPhoneNumber(@NotNull AuthCallback<String> callback) {
        getAndroidScreenProperty();
        prefetchMobileNumber(null,  callback);
    }

    private void prefetchMobileNumber(String businessId, AuthCallback<String> callBack) {
        QuickLogin.getInstance().init(context, businessId);
        QuickLogin.getInstance().setPrefetchNumberTimeout(3);
        QuickLogin.getInstance().prefetchMobileNumber(new QuickLoginPreMobileListener() {
            @Override
            public void onGetMobileNumberSuccess(String YDToken, String mobileNumber) {
                //预取号成功
                Log.d(TAG, "Got phone:" + mobileNumber);
                if (callBack == null) {
                    handler.sendEmptyMessage(MSG_LOGIN);
                } else {
                    callBack.call(200, "", mobileNumber);
                }
            }

            @Override
            public void onGetMobileNumberError(String YDToken, String msg) {
                Log.e(TAG, "Got phone error:" + msg);
                callback.call(ResultCode.ERROR_ONE_CLICK, msg, null);
            }
        });
    }

    public void startLogin(@NotNull AuthCallback<Object> callback) {
        startLogin(null, callback);
    }

    private void startLogin(UnifyUiConfig uiConfig, @NotNull AuthCallback<Object> callback) {
        this.uiConfig = uiConfig;
        this.callback = callback;
        if (uiConfig != null) {
            QuickLogin.getInstance().setUnifyUiConfig(uiConfig);
            startOnePass();
            return;
        }

        config(context.getDrawable(R.drawable.ic_phone));
        startOnePass();
    }

    private void startOnePass() {
        QuickLogin.getInstance().onePass(new QuickLoginTokenListener() {
            @Override
            public void onGetTokenSuccess(String YDToken, String accessCode) {
                //一键登录成功 运营商token：accessCode获取成功
                //拿着获取到的运营商token二次校验（建议放在自己的服务端）
                Log.d(TAG, "onGetTokenSuccess");
                callback.call(ResultCode.AUTH_SUCCESS, "", "YDToken = " + YDToken + " accessCode = " + accessCode);
            }

            @Override
            public void onGetTokenError(String YDToken, String msg) {
                quit();
                Log.e(TAG, "onGetTokenError:" + msg);
                callback.call(ResultCode.ERROR_ONE_CLICK, msg, null);
            }

            @Override
            public void onCancelGetToken() {
                callback.call(ResultCode.ERROR_ONE_CLICK_CANCEL, "OnClick login cancelled", null);
            }
        });
    }

    private void fireCallback(int code, String message, Object userInfo) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            if (callback != null) {
                callback.call(code, message, userInfo);
            }
            quit();
        });
    }

    private void getAndroidScreenProperty() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        //px
        int width = dm.widthPixels;         // 屏幕宽度（像素）
//        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        screenWidth = (int) (width / density);  // 屏幕宽度(dp)
//        int screenHeight = (int) (height / density);// 屏幕高度(dp)
//
//        Log.d(TAG, "屏幕宽度（像素）：" + width);
//        Log.d(TAG, "屏幕高度（像素）：" + height);
//        Log.d(TAG, "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
//        Log.d(TAG, "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
//        Log.d(TAG, "屏幕宽度（dp）：" + screenWidth);
//        Log.d(TAG, "屏幕高度（dp）：" + screenHeight);
    }

    private void config(Drawable logo) {
        TitleLayout titleLayout = inflateTitleLayout();
        RelativeLayout otherLoginRel = inflateOtherLayout();
        //LinearLayout socialRel = inflateSocialLayout();
        int uiMode = context.getResources().getConfiguration().uiMode;
        UnifyUiConfig.Builder builder = new UnifyUiConfig.Builder()
                .setStatusBarColor(context.getColor(R.color.color_status_bar_bg))
                .setStatusBarDarkColor((uiMode & Configuration.UI_MODE_NIGHT_MASK) != Configuration.UI_MODE_NIGHT_YES)
                .setHideNavigation(true)
                .setLogoIconDrawable(logo)
                .setLogoTopYOffset(160)
                .setLogoHeight(52)
                .setLogoWidth(46)
                .setSloganColor(0)
                .setMaskNumberColor(context.getColor(R.color.color_text_black))
                .setMaskNumberTopYOffset(233)//160+52+21=233
                .setSloganTopYOffset(265)//160+52+21+24+8=265
                //.setPrivacyTopYOffset(263)
                .setPrivacyBottomYOffset(188)//126+48+12
                .setPrivacyMarginLeft(24)
                .setPrivacyMarginRight(24)
                .setPrivacyLineSpacing(1, 1)
                .setPrivacyTextGravityCenter(false)
                .setCheckBoxGravity(Gravity.TOP)
                .setPrivacyState(false)
                .setPrivacyCheckBoxWidth(14)
                .setPrivacyCheckBoxHeight(14)
                .setCheckedImageName("ic_authing_checkbox_selected") // 设置隐私栏复选框选中时的图片资源
                .setUnCheckedImageName("ic_authing_checkbox_normal") // 设置隐私栏复选框未选中时的图片资源
                .setPrivacyTextMarginLeft(9)
                .setPrivacyTextEnd("") // 设置隐私栏声明部分尾部文案
                .setPrivacyTextColor(context.getColor(R.color.color_text_gray)) // 设置隐私栏文本颜色，不包括协议
                .setPrivacyProtocolColor(context.getColor(R.color.color_text_black)) // 设置隐私栏协议颜色
                .setPrivacySize(12) // 设置隐私栏区域字体大小
                .setHidePrivacySmh(true)
                .setLoginBtnText(context.getString(R.string.current_phone_login))
                //.setLoginBtnTopYOffset(365)//160+52+21+24+102=359
                .setLoginBtnBottomYOffset(126)//48+12+66 = 126
                .setLoginBtnWidth(screenWidth - 24 * 2)
                .setLoginBtnHeight(48)
                .setLoginBtnBackgroundRes("authing_button_background")
                .setLoginBtnTextSize(16)
                .addCustomView(titleLayout, "titleLayout", UnifyUiConfig.POSITION_IN_BODY, null)
                .addCustomView(otherLoginRel, "otherBtn", UnifyUiConfig.POSITION_IN_BODY, null)
                //.addCustomView(socialRel, "socialList", UnifyUiConfig.POSITION_IN_BODY, null)
                .setBackgroundImageDrawable(new ColorDrawable(context.getColor(R.color.color_page_bg)))
                .setLoadingVisible(true)
                .setLoadingView(inflateLoadingLayout())
                .setLoginListener(new LoginListener() {
                    @Override
                    public boolean onDisagreePrivacy(TextView privacyTv, Button btnLogin) {
                        //showPrivacyBottomDiLog(privacyTv, btnLogin);
                        QuickLogin.getInstance().setPrivacyState(true);
                        btnLogin.performClick();
                        return true;
                    }
                });

        UnifyUiConfig uiConfig = builder.build(context);
        QuickLogin.getInstance().setUnifyUiConfig(uiConfig);
    }

    private TitleLayout inflateTitleLayout() {
        TitleLayout titleLayout = new TitleLayout(context);
        RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, (int) Util.dp2px(context, 44));
        titleLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        titleLayoutParams.addRule(RelativeLayout.ALIGN_TOP, com.netease.nis.quicklogin.R.id.yd_iv_logo);
        titleLayout.setLayoutParams(titleLayoutParams);
        titleLayout.setShowBackIcon(true);
        titleLayout.setCheckNetWork(true);
        titleLayout.setPadding((int) Util.dp2px(context, 24), 0, (int) Util.dp2px(context, 12), 0);
        titleLayout.setBackIconClickListener(v -> {
            quit();
            callback.call(ResultCode.ERROR_ONE_CLICK_CANCEL, "OnClick login cancelled", null);
        });
        titleLayout.initView();
        return titleLayout;
    }

    private RelativeLayout inflateOtherLayout() {
        RelativeLayout otherLoginRel = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParamsOther = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, (int) Util.dp2px(context, 48));
        layoutParamsOther.setMargins(0, -(int) Util.dp2px(context, 114), 0, 0);
        layoutParamsOther.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParamsOther.addRule(RelativeLayout.BELOW, com.netease.nis.quicklogin.R.id.yd_btn_oauth);
        otherLoginRel.setLayoutParams(layoutParamsOther);

        Button other = new Button(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        int m = (int) Util.dp2px(context, 24);
        lp.setMargins(m, 0, m, 0);
        other.setLayoutParams(lp);
        otherLoginRel.addView(other);
        other.setText(context.getString(R.string.other_login));
        other.setTextAppearance(android.R.style.Widget_TextView);
        other.setAllCaps(false);
        other.setStateListAnimator(null);
        other.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_large_size));
        other.setTextColor(context.getColor(R.color.color_text_black));
        other.setBackgroundResource(R.drawable.button_background_gray);
        other.setMinimumWidth((int) Util.dp2px(context, screenWidth - 24 * 2));
        other.setMinimumHeight((int) Util.dp2px(context, 48));
        other.setOnClickListener((v) -> {
            clear();
            callback.call(ResultCode.ERROR_ONE_CLICK_CANCEL, "OnClick login cancelled", null);
        });
        return otherLoginRel;
    }

    private LinearLayout inflateLoadingLayout() {
        LinearLayout loadingLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams loadingParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        loadingLayout.setLayoutParams(loadingParams);
        loadingLayout.setOrientation(LinearLayout.VERTICAL);
        loadingLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        loadingLayout.setBackgroundColor(Color.parseColor("#00000000"));

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageParams.topMargin = (int) Util.dp2px(context, 320);
        loadingDrawable = (AnimatedVectorDrawable) context.getDrawable(R.drawable.ic_animated_loading_blue);
        imageView.setImageDrawable(loadingDrawable);
        loadingDrawable.start();
        imageView.setLayoutParams(imageParams);
        loadingLayout.addView(imageView);

        return loadingLayout;
    }


    private void quit() {
        clear();
        QuickLogin.getInstance().quitActivity();
    }

    private void clear(){
        if (loadingDrawable != null) {
            if (loadingDrawable.isRunning()) {
                loadingDrawable.stop();
            }
            loadingDrawable = null;
        }
    }

}
