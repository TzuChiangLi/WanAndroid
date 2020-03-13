package com.lzq.wanandroid.view.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author LZQ
 * @content 本类负责item收藏按钮的闪烁效果
 */
public class CollectAnim {
    private static CollectAnim INSTANCE;

    public CollectAnim() {
    }

    public static CollectAnim getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CollectAnim();
        }
        return INSTANCE;
    }


    public static void show(View view) {
        ObjectAnimator scaleCollectX = ObjectAnimator.ofFloat(view, "scaleX", 1f,1.4f, 1f);
        ObjectAnimator scaleCollectY = ObjectAnimator.ofFloat(view, "scaleY", 1f,1.4f, 1f);
        AnimatorSet scaleSet=new AnimatorSet();
        scaleSet.playTogether(scaleCollectX,scaleCollectY);
        scaleSet.setDuration(200).start();
    }
}
