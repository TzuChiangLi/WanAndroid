package com.lzq.wanandroid.Base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.lzq.wanandroid.R;

public abstract class BaseFragment extends Fragment {

    public void startActivity(View view, Class<?> className) {
        ActivityUtils.getActivityByView(view).startActivity(new Intent(ActivityUtils.getActivityByView(view), className));
        //参数(进入动画，退出动画)
        ActivityUtils.getActivityByView(view).overridePendingTransition(R.anim.enter_fade_out,R.anim.enter_fade_in);
    }

    public void startActivity(View view,Intent intent){
        ActivityUtils.getActivityByView(view).startActivity(intent);
        //参数(进入动画，退出动画)
        ActivityUtils.getActivityByView(view).overridePendingTransition(R.anim.enter_fade_out,R.anim.enter_fade_in);
    }

    //获取焦点并且显示输入法
    public static void showSoftInputUtil(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }

}
