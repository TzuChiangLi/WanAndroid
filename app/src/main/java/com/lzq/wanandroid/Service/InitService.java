package com.lzq.wanandroid.Service;

import android.Manifest;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;

import com.hjq.toast.ToastUtils;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
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
        initPermission();
        //SDK初始化的操作
        QbSdk.initX5Environment(this, null);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WanAndroid/");
        if (checkPermission()) {
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        Bugly.init(this, "2aa3615a5e", false);
        Beta.autoInit = true;
        Beta.storageDir = file;
        Beta.initDelay = 3000;
    }

        private boolean checkPermission() {
            Permission checkResult = SoulPermission.getInstance().checkSinglePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return checkResult.isGranted() ? true : false;
        }

    private void initPermission() {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        ToastUtils.show("如果你拒绝文件读写权限，那么很可能将无法及时获取更新版本！");
                    }
                });

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
