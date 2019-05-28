package com.lzq.wanandroid.Presenter;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.Api.Contract;

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
