package com.lzq.wanandroid.Presenter;

import android.app.Activity;

import com.lzq.wanandroid.Contract.LoginContract;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Net.LoginTask;
import com.lzq.wanandroid.Utils.AnimationUtil;

public class LoginPresenter implements LoginContract.LoginPresenter, LoadTasksCallBack<Data> {
    private static final String TAG = "LoginPresenter";
    private LoginTask mTask;
    private LoginContract.LoginView mView;
    private int oldHeight = 0;


    public LoginPresenter(LoginContract.LoginView mView, LoginTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    public static LoginPresenter createPresenter(LoginContract.LoginView mView, LoginTask mTask){
        return new LoginPresenter(mView,mTask);
    }


    @Override
    public void doLogin(String username, String password) {
        mTask.execute(username, password, this);
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

    }

    @Override
    public void onFinish() {

    }
}
