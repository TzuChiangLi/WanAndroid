package com.lzq.wanandroid;

import android.content.Intent;
import android.os.Bundle;

import com.lzq.wanandroid.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    private static final String TAG="SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //跳转页面
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
