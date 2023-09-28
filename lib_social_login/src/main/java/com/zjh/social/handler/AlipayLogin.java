package com.zjh.social.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alipay.sdk.app.OpenAuthTask;
import com.zjh.social.callback.AuthCallback;
import com.zjh.social.params.AlipayParams;
import com.zjh.social.util.ResultCode;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2023/9/21
 * @description
 **/
public class AlipayLogin extends SocialAuthenticator<AlipayParams> {

    private static final String TAG = "AlipayLogin";
    private String appId;

    private AlipayLogin() {
    }

    @SuppressLint("StaticFieldLeak")
    private static final class AlipayInstanceHolder {
        static final AlipayLogin mInstance = new AlipayLogin();
    }

    public static AlipayLogin getInstance() {
        return AlipayLogin.AlipayInstanceHolder.mInstance;
    }

    @Override
    public void startAuth(@NonNull Context context, AlipayParams params, @NonNull AuthCallback<Object> callback) {
        appId = params.getAppId();
        if (TextUtils.isEmpty(appId)) {
            callback.call(ResultCode.ERROR_PARAMS, "appId 不能为空", null);
            return;
        }
        Activity activity = (Activity) context;

        // 传递给支付宝应用的业务参数
        final Map<String, String> bizParams = new HashMap<>();
        bizParams.put("url", "https://authweb.alipay.com/auth?auth_type=PURE_OAUTH_SDK&app_id=" + appId + "&scope=auth_user&state=init");

        // 支付宝回跳到您的应用时使用的 Intent Scheme。
        // 请设置为一个不和其它应用冲突的值，并在 AndroidManifest.xml 中为 AlipayResultActivity 的 android:scheme 属性
        // 指定相同的值。实际使用时请勿设置为 __alipaysdkdemo__ 。
        // 如果不设置，OpenAuthTask.execute() 在用户未安装支付宝，使用网页完成业务流程后，将无法回跳至您的应用。
        final String scheme = "__social__";

        // 防止在支付宝 App 被强行退出等意外情况下，OpenAuthTask.Callback 一定时间内无法释放，导致
        // Activity 泄漏
        final WeakReference<Activity> ctxRef = new WeakReference<>(activity);

        // 唤起授权业务
        final OpenAuthTask task = new OpenAuthTask(activity);
        task.execute(
                scheme,    // Intent Scheme
                OpenAuthTask.BizType.AccountAuth, // 业务类型
                bizParams, // 业务参数
                (i, s, bundle) -> {
                    final Context ref = ctxRef.get();
                    if (ref != null) {
                        handleResult(bundle, callback);
                    } else {
                        Log.e(TAG, "Alipay auth failed, errorCode is: " + i + ",errorMessage is: " + s);
                        callback.call(ResultCode.ERROR_ALIPAY, "Alipay auth failed", null);
                    }
                }, true); // 是否需要在用户未安装支付宝 App 时，使用 H5 中间页中转。建议设置为 true。
    }


    private void handleResult(Bundle bundle, @NotNull AuthCallback<Object> callback) {
        if (bundle == null) {
            callback.call(ResultCode.ERROR_ALIPAY, "Alipay auth failed", null);
            return;
        }

        if (!"SUCCESS".equals(bundle.get("result_code"))) {
            callback.call(ResultCode.ERROR_ALIPAY, "Alipay auth failed", null);
            return;
        }
        Log.i(TAG, "Alipay auth success");
        String code = bundle.get("auth_code").toString();
        callback.call(ResultCode.AUTH_SUCCESS, "Alipay auth success", code);
    }

}
