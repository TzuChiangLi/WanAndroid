package com.lzq.wanandroid.Service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.Utils.StringUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;

import org.litepal.LitePal;

public class InitService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.lzq.wanandroid.Service.action.INIT";

    public InitService(String name) {
        super(name);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    private void performInit() {
        //SDK初始化的操作
        QbSdk.initX5Environment(this, null);


        Bugly.init(this, "2aa3615a5e", false);
        Beta.autoInit = true;
        Beta.initDelay = 2 * 1000;

        boolean nightMode = SPUtils.getInstance(StringUtils.CONFIG_SETTINGS).getBoolean
                (StringUtils.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }
}
