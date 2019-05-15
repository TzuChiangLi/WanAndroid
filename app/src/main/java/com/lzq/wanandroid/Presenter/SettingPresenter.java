package com.lzq.wanandroid.Presenter;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class SettingPresenter implements Contract.SettingPresenter {
    private static final String TAG = "SettingPresenter";
    private Contract.SettingView mView;

    public SettingPresenter(Contract.SettingView mView) {
        this.mView = mView;
    }

    public static SettingPresenter createPresenter(Contract.SettingView mView) {
        return new SettingPresenter(mView);
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
