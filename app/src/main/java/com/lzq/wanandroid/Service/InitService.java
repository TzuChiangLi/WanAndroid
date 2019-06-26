package com.lzq.wanandroid.Service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;

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
        //SDK初始化的操作
        QbSdk.initX5Environment(this, null);
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WanAndroid/");
        if (!file.exists()){
            file.mkdirs();
        }
        Bugly.init(this, "2aa3615a5e", false);
        Beta.autoInit = true;
        Beta.storageDir = file;
        Beta.initDelay = 1000;
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
