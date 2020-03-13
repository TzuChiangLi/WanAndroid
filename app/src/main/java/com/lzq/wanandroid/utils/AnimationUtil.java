package com.lzq.wanandroid.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 本工具类为动画工具类
 * author:lzq
 * date:2019/04/13
 * update content:更新为静态内部单例
 */
public class AnimationUtil {
    private static String TAG = "YSActivity";

    private AnimationUtil() {
    }

    public static AnimationUtil getInstance() {
        return AnimationInstance.animationUtil;
    }

    private static class AnimationInstance {
        private static AnimationUtil animationUtil = new AnimationUtil();
    }


    //本方法用于获取输入法的高度，辅助解决按钮被遮挡
    public static int getInputHeight(Activity activity) {
        Rect r = new Rect();
        //获取当前界面可视部分
        int heightDifference = 0;
        try {
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
            //获取屏幕的高度
            int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();
            //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
            heightDifference = screenHeight - r.bottom;

        } catch (Exception e) {
        }
        return heightDifference;
    }

    //本方法用于获取输入法的高度，辅助解决按钮被遮挡
    public static String getScreenHeight(Activity activity) {
        WindowManager wm = activity.getWindowManager();
        int height = wm.getDefaultDisplay().getHeight();
        Log.d(TAG, "----doAnimator: " + height);
        return String.valueOf(height);
    }

    //本方法解决布局底部的按钮被遮挡的问题
    public static void setViewAlignBottom(View mView, int value) {
        ObjectAnimator translationX = new ObjectAnimator().ofFloat(mView, "translationX", 0, 0);
        ObjectAnimator translationY = new ObjectAnimator().ofFloat(mView, "translationY", 0, value);

        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translationX, translationY); //设置动画
        animatorSet.setDuration(100);  //设置动画时间
        animatorSet.start(); //启动
    }


    /*获取底部导航栏的高度*/
    public static int getBottomBarHeight(Context context) {
        int result = 0;
        int resourceId = 0;
        int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            Log.d(TAG, "---------getBottomBarHeight: " + context.getResources().getDimensionPixelSize(resourceId) + "");
            return context.getResources().getDimensionPixelSize(resourceId);
        } else
            return 0;
    }


    /*获取状态栏高度*/
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    //是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                //不显示
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                //显示
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

}