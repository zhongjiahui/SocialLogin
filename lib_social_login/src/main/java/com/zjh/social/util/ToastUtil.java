package com.zjh.social.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjh.social.R;

import java.util.Timer;
import java.util.TimerTask;

public class ToastUtil {

    public static void controlToastTime(final Toast toast, int duration) {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }

    public static void showTop(Context context, String text) {
        showTop(context, text, 0);
    }

    public static void showTop(Context context, String text, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout_top, null);
        TextView tv_msg = view.findViewById(R.id.toast_text);
        tv_msg.setText(text);
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        if (duration != 0){
            controlToastTime(toast, duration);
        }
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setView(view);
        toast.show();
    }

    public static void showTopError(Context context, String text) {
        showTopError(context, text, 0);
    }

    public static void showTopError(Context context, String text, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout_top, null);
        view.setBackgroundResource(R.drawable.background_toast_error);
        TextView tv_msg = view.findViewById(R.id.toast_text);
        tv_msg.setText(text);
        tv_msg.setTextColor(context.getColor(R.color.color_text_yellow));
        ImageView tv_img = view.findViewById(R.id.toast_image);
        tv_img.setImageResource(R.drawable.ic_prompt);
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        if (duration != 0){
            controlToastTime(toast, duration);
        }
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setView(view);
        toast.show();
    }

    public static void showCenter(Context context, String text) {
        showCenter(context, text, 0);
    }

    public static void showCenter(Context context, String text, int imageResId) {
        showCenter(context, text, imageResId, 0);
    }

    public static void showCenter(Context context, String text, int imageResId, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout_center, null);
        ImageView imageView = view.findViewById(R.id.toast_image);
        if (imageResId != 0){
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(imageResId);
        }
        TextView tv_msg = view.findViewById(R.id.toast_text);
        tv_msg.setText(text);
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        if (duration != 0){
            controlToastTime(toast, duration);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }

    public static void showCenterWarning(Context context, String text) {
        showCenterWarning(context, text, 0);
    }

    public static void showCenterWarning(Context context, String text, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout_center_warning, null);
        TextView tv_msg = view.findViewById(R.id.toast_text);
        tv_msg.setText(text);
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        if (duration != 0){
            controlToastTime(toast, duration);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }

}
