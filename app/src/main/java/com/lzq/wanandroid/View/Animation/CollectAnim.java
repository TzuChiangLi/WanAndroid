package com.lzq.wanandroid.View.Animation;

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
        ObjectAnimator scaleCollect = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f,1.5f,1.7f,1.5f,1.2f, 1f);
        scaleCollect.setDuration(200);
        scaleCollect.start();
    }
}
