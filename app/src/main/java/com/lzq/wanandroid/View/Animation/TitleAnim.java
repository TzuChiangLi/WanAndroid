package com.lzq.wanandroid.View.Animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.just.agentwebX5.IFileUploadChooser;
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

    private ObjectAnimator alpha_tv,alpha_imgBtn;
    //title文字及右部图标的渐现
    public static void show(View tv, final View imgBtn, String text, final int page) {
        TextView mTextView = (TextView) tv;
        ImageButton mImgBtn = (ImageButton) imgBtn;
        ObjectAnimator alpha_tv = ObjectAnimator.ofFloat(tv, "alpha", 0, 1);
        ObjectAnimator alpha_imgBtn = ObjectAnimator.ofFloat(imgBtn, "alpha", 0, 1);
        mTextView.setText(text);
        switch (page) {
            case 0:
                mImgBtn.setVisibility(View.VISIBLE);
                mImgBtn.setImageResource(R.mipmap.search);
                break;
            case 1:
            case 2:
                if (imgBtn.getAlpha()!=0||mImgBtn.getVisibility()==View.VISIBLE){
                alpha_imgBtn = ObjectAnimator.ofFloat(imgBtn, "alpha", 1, 0);
                }
                break;
            case 3:
                mImgBtn.setVisibility(View.VISIBLE);
                mImgBtn.setImageResource(R.mipmap.setting);
                break;
        }
        AnimatorSet alphaSet = new AnimatorSet();
        alphaSet.playTogether(alpha_imgBtn, alpha_tv);
        alphaSet.setDuration(500).start();
        alphaSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (page==1||page==2){
                    if (imgBtn.getVisibility()!=View.GONE) imgBtn.setVisibility(View.GONE);
                }
                if (page==0||page==3){
                    if (imgBtn.getVisibility()!=View.VISIBLE) imgBtn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //title文字及右部图标的渐隐
    public static void hide(View... views) {
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
