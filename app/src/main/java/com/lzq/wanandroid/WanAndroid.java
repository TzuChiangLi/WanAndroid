package com.lzq.wanandroid;

import android.Manifest;
import android.app.Application;
import android.os.Environment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.Service.InitService;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.smtt.sdk.QbSdk;

import org.litepal.LitePal;

import java.io.File;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import okhttp3.OkHttpClient;

public class WanAndroid extends Application {
    private static  final String TAG="WanAndroid";
    @Override
    public void onCreate() {
        super.onCreate();
        boolean nightMode = SPUtils.getInstance(StringUtils.CONFIG_SETTINGS).getBoolean
                (StringUtils.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES :
                AppCompatDelegate.MODE_NIGHT_NO);
        ToastUtils.init(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        OkGo.getInstance().setOkHttpClient(builder.build()).init(this);
        LitePal.initialize(this);
        LitePal.getDatabase();
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.REQUEST_INSTALL_PACKAGES,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE),
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
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(this, null);
        InitService.start(this);
    }

}