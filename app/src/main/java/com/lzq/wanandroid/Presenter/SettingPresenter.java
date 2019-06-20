package com.lzq.wanandroid.Presenter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Base.BasePresenter;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import okhttp3.HttpUrl;

public class SettingPresenter extends BasePresenter implements Contract.SettingPresenter {
    private static final String TAG = "SettingPresenter";
    private Contract.SettingView mView;
    private Context mContext;

    public SettingPresenter(Contract.SettingView mView, Context mContext) {
        this.mView = mView;
        this.mContext=mContext;
    }

    public static SettingPresenter createPresenter(Contract.SettingView mView, Context mContext) {
        return new SettingPresenter(mView,mContext);
    }


    @Override
    public void getVersion() {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            String version = packInfo.versionName;
            mView.setVersion(version);
        } catch (Exception e) {
        }
    }

    @Override
    public void changeDisplayMode(boolean flag) {
        SPUtils.getInstance(StringUtils.CONFIG_SETTINGS).put(StringUtils.KEY_NIGHT_MODE, flag);
        mView.afterChangeMode();
    }

    @Override
    public void Logout() {
        SPUtils.getInstance("userinfo").put("isLogin",false);
        SPUtils.getInstance("userinfo").put("username","游客");
        SPUtils.getInstance("userinfo").put("id","******");

        HttpUrl httpUrl = HttpUrl.parse(StringUtils.URL);
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.removeCookie(httpUrl);
        mView.afterLogout();
    }
}
