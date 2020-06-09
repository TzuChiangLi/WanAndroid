package com.lzq.wanandroid.service;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.lzq.wanandroid.R;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;

public class InitService extends IntentService {
    private static final String TAG = "INIT";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.lzq.wanandroid.Service.action.INIT";

    public InitService() {
        super(ACTION_INIT_WHEN_APP_CREATE);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    private void performInit() {
        initPermission();
        //SDK初始化的操作
        QbSdk.initX5Environment(this, null);
        Bugly.init(this, "2aa3615a5e", false);
        Beta.autoInit = true;
        Beta.initDelay = 3000;
        Beta.largeIconId = R.mipmap.icon_launcher;
    }

        private boolean checkPermission() {
            Permission checkResult = SoulPermission.getInstance().checkSinglePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return checkResult.isGranted() ? true : false;
        }

    private void initPermission() {
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
