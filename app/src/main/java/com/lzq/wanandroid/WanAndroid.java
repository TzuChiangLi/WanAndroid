package com.lzq.wanandroid;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import okhttp3.OkHttpClient;

public class WanAndroid extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        OkGo.getInstance().setOkHttpClient(builder.build()).init(this);
        boolean nightMode = SPUtils.getInstance(StringUtils.CONFIG_SETTINGS).getBoolean
                (StringUtils.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
        QbSdk.initX5Environment(this, null);
        CrashReport.initCrashReport(getApplicationContext(), "2aa3615a5e", false);
    }


}