package com.lzq.wanandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lzq.wanandroid.Base.BaseActivity;
import com.lzq.wanandroid.Service.InitService;

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
