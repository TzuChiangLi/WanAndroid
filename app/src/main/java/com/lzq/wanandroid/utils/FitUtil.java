package com.lzq.wanandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class FitUtil {
    private static final String TAG = "FitUtil";

    /**
     * 创    建:  lt  2018/8/15--14:45
     * 作    用:  使用并优化今日头条的适配方案的工具类
     * 注意事项:  在Activity的onCreate里,并在setContextView之上调用,可以直接放在Base里
     */

    private static float width = 720;//todo 手动设置为设计图的宽(px),适配将根据宽为基准,也可以设置高,但是推荐设置宽,如果不需要px=dp则不设置也行
    private static int dpi = 420;//todo 手动设置设计图的dpi,一般 xhdpi是宽/2 xxhdpi 是宽/3
    private static float nativeWidth = 0;//真实屏幕的宽,不需要手动改
    private Activity activity;
    /**
     * 在Activity的onCreate中调用,修改该Activity的density,即可完成适配,使用宽高直接使用设计图上px相等的dp值
     *
     * @param activity     需要改变的Activity
     * @param isPxEqualsDp 是否需要设置为设计图上的px直接在xml上写dp值(意思就是不需要自己计算dp值,直接写设计图上的px值,并改单位为dp),但开启后可能需要手动去设置ToolBar的大小,如果不用可以忽略
     */
    public static void autoFit(Activity activity, boolean isPxEqualsDp) {
        if (nativeWidth == 0) {
            nativeWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
        }
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        dpi= displayMetrics.densityDpi;
        displayMetrics.density = isPxEqualsDp ? nativeWidth / dpi / (width / dpi) : nativeWidth / dpi;
        displayMetrics.densityDpi = (int) (displayMetrics.density * 160);
    }

    public static void getAndroiodScreenProperty(Activity activity) {

        WindowManager wm = (WindowManager)activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)


        Log.d(TAG, "----屏幕宽度（像素）：" + width);
        Log.d(TAG, "----屏幕高度（像素）：" + height);
        Log.d(TAG, "----屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d(TAG, "----屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d(TAG, "----屏幕宽度（dp）：" + screenWidth);
        Log.d(TAG, "----屏幕高度（dp）：" + screenHeight);
    }

}