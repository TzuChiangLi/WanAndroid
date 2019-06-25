package com.lzq.wanandroid.View.Animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

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

    public static void showLogo(final View... views) {
        if (views.length != 0) {
            ObjectAnimator show = ObjectAnimator.ofFloat(views[0], "alpha", 0, 1);
            final ObjectAnimator hide = ObjectAnimator.ofFloat(views[0], "alpha", 1, 0);
            final ObjectAnimator translationY = ObjectAnimator.ofFloat(views[0], "translationY", views[0].getY(), -80f);
            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500).playTogether(hide, translationY);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    views[0].setVisibility(View.GONE);
                    showMain(views[1], views[2], views[3]);
                }
            });
            show.setDuration(800).addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    animatorSet.start();
                }
            });
            show.start();

        }
    }

    public static void showMain(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.VISIBLE);
        }
    }

}
