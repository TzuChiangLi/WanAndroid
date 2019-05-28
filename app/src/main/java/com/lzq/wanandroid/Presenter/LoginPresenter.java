package com.lzq.wanandroid.Presenter;

import android.app.Activity;

import com.lzq.wanandroid.Api.LoginContract;
import com.lzq.wanandroid.Api.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Utils.AnimationUtil;
import com.lzq.wanandroid.Utils.StringUtils;

public class LoginPresenter implements LoginContract.LoginPresenter, LoadTasksCallBack<Data> {
    private static final String TAG = "LoginPresenter";
    private WebTask mTask;
    private LoginContract.LoginView mView;
    private int oldHeight = 0;


    public LoginPresenter(LoginContract.LoginView mView, WebTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    public static LoginPresenter createPresenter(LoginContract.LoginView mView, WebTask mTask) {
        return new LoginPresenter(mView, mTask);
    }


    @Override
    public void doLogin(String username, String password) {
        mTask.execute(this, StringUtils.TYPE_LOGIN, username, password);
    }

    @Override
    public void doRegister(String username, String password) {
        mTask.execute(this, StringUtils.TYPE_REGISTER, username, password);
    }

    @Override
    public void getUserInfo() {

    }


    @Override
    public void resetLoginLocation(Activity activity) {
        int heightDifference = AnimationUtil.getInputHeight(activity);
        if (oldHeight != heightDifference) {
            oldHeight = heightDifference;
            boolean isExistBottomBar = AnimationUtil.checkDeviceHasNavigationBar(activity);
            if (isExistBottomBar) {
                heightDifference = heightDifference - AnimationUtil.getBottomBarHeight(activity);
            }
            if (heightDifference > 0) {
                mView.setLoginLocation(heightDifference);
            } else {
                mView.refreshLocation(heightDifference);
            }

        }
    }


    @Override
    public void onSuccess(Data data, int flag) {
        mView.LoginSuccess(data);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onError(int code, String msg) {
        mView.RegisterResult(msg);
    }

    @Override
    public void onFinish() {

    }
}
