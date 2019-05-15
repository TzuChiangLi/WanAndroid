package com.lzq.wanandroid.Presenter;

import com.blankj.utilcode.util.SPUtils;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class MainPresenter implements Contract.MainPresenter {
    private static final String TAG = "MainPresenter";
    private Contract.MainView mView;

    public MainPresenter(Contract.MainView mView) {
        this.mView = mView;
    }

    public static MainPresenter createMainPresenter(Contract.MainView mView) {
        return new MainPresenter(mView);
    }

    @Override
    public void isLogin() {
        mView.afterCheckLogin(SPUtils.getInstance("userinfo").getBoolean("isLogin",false));
    }
}
