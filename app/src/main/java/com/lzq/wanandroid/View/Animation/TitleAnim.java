package com.lzq.wanandroid.View.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lzq.wanandroid.R;

/**
 * @author LZQ
 * @content 本类负责Activity标题部分的渐变效果
 */
public class TitleAnim {
    private static TitleAnim INSTANCE;

    public TitleAnim() {
    }

    public static TitleAnim getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TitleAnim();
        }
        return INSTANCE;
    }

    //title文字及右部图标的渐现
    public static void show(View tv, View imgBtn, String text, int page) {
        TextView mTextView = (TextView) tv;
        ImageButton mImgBtn = (ImageButton) imgBtn;
        ObjectAnimator alpha_tv = ObjectAnimator.ofFloat(tv, "alpha", 0, 1);
        ObjectAnimator alpha_imgBtn = ObjectAnimator.ofFloat(imgBtn, "alpha", 0, 1);
        mTextView.setText(text);
        switch (page) {
            case 0:
                mImgBtn.setImageResource(R.mipmap.search);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                mImgBtn.setImageResource(R.mipmap.setting);
                break;
            default:
                mImgBtn.setImageResource(R.mipmap.search);
                break;
        }
        AnimatorSet alphaSet = new AnimatorSet();
        alphaSet.playTogether(alpha_imgBtn,alpha_tv);
        alphaSet.setDuration(500).start();
    }

    //title文字及右部图标的渐隐
    public static void hide(View...views) {
        ObjectAnimator alpha_Title = ObjectAnimator.ofFloat(views, "alpha", 1, 0);
        alpha_Title.setDuration(500)
                .start();
    }
    //title文字的渐隐
    public static void hide(View view) {
        ObjectAnimator alpha_Title = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        alpha_Title.setDuration(500)
                .start();
    }
}
