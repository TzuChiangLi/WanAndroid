package com.lzq.wanandroid.Contract;

import android.app.Activity;
import android.content.Intent;

import com.lzq.wanandroid.BaseView;
import com.lzq.wanandroid.Model.Datas;

import java.util.List;

public interface LoginContract {
    //与M层交互
    interface LoginPresenter {
        void doLogin(String username,String password);

        void getUserInfo();

        void resetLoginLocation(Activity activity);

    }

    //与M层交互
    interface LogoutPresenter {
        void initView();

        void setUserInfo();

    }

    //与V层交互，需要将获取的信息展示出来
    interface LoginView extends BaseView<LoginPresenter> {

        void LoginSuccess();

        void setLoginLocation(int height);

        void refreshLocation(int height);
    }


    //与V层交互，需要将获取的信息展示出来
    interface LogoutView extends BaseView<LoginPresenter> {

        void setEmptyContent(List<Datas> mList);

        void setContent(List<Datas> mList, int flag);
    }

}
