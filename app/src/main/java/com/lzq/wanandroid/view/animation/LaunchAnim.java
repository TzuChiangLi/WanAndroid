package com.lzq.wanandroid.View.Animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.lzq.wanandroid.R;

public class LaunchAnim {
    private static LaunchAnim INSTANCE;

    public LaunchAnim() {
    }

    public static LaunchAnim getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LaunchAnim();
        }
        return INSTANCE;
    }

    public static void showLogo(final Activity activity, final View... views) {
        if (views.length != 0) {
            final ObjectAnimator hide = ObjectAnimator.ofFloat(views[0], "alpha", 1, 0);
            final ObjectAnimator translationY = ObjectAnimator.ofFloat(views[0], "translationY", views[0].getY(), -80f);
            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(1000).playTogether(hide, translationY);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    views[0].setVisibility(View.GONE);
                    views[4].setVisibility(View.GONE);
                    showMain(activity,views[1], views[2], views[3]);
                }
            });
            animatorSet.start();
        }
    }

    public static void showMain(Activity activity,View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.VISIBLE);
        }
        ImmersionBar.with(activity).statusBarColor(R.color.bg_daily_mode).autoDarkModeEnable(true).fitsSystemWindows(true).keyboardEnable(true).init();
    }

}
