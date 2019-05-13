package com.lzq.wanandroid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.Utils.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;


public abstract class BaseActivity extends AppCompatActivity {
    //默认禁止屏幕旋转
    private boolean isAllowScrRoate = false;
    public int height;
    public static Toast toast = null;
    static InputMethodManager inputManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        setScreenRoate(isAllowScrRoate);


    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }

    public void ToastUtils(String msg, int... arg) {
        ToastUtils.show(msg);
    }

    public void startActivity(Class<?> className) {
        startActivity(new Intent(BaseActivity.this, className));
        overridePendingTransition(R.anim.enter_fade_out,R.anim.enter_fade_in);
    }

    public void finishActivity(){
        finish();
        overridePendingTransition(R.anim.exit_fade_out, R.anim.exit_fade_in);
    }


    public void setScreenRoate(boolean isAllowScrRoate) {
        this.isAllowScrRoate = isAllowScrRoate;
        if (isAllowScrRoate) {
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    //隐藏软键盘
    public void hideSoftInputUtil() {
        try {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("HideInput", e.getMessage());
        }
    }

    //获取焦点并且显示输入法
    public static void showSoftInputUtil(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        inputManager =
                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }


    //获取顶部状态栏高度
    public void getStatusBarHeight() {
        /**
         * 获取状态栏高度
         * */
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        height = resources.getDimensionPixelSize(resourceId);

    }


    //获取屏幕原始尺寸，包括顶部状态栏，APP界面，以及底部导航栏
    public int getScreenHeight() {
        int screenHeight = 0;
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            screenHeight = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenHeight;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.gc();
        super.onActivityResult(requestCode, resultCode, data);
    }


}
