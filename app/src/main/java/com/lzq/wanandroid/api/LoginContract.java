package com.lzq.wanandroid.Api;

import android.app.Activity;

import com.lzq.wanandroid.Base.BaseView;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.Datas;

import java.util.List;

public interface LoginContract {
    //与M层交互
    interface LoginPresenter {
        void doLogin(String username, String password);

        void doRegister(String username, String password);

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

        void LoginSuccess(Data data);

        void RegisterResult(String... infos);

        void setLoginLocation(int height);

        void refreshLocation(int height);
    }


    //与V层交互，需要将获取的信息展示出来
    interface LogoutView extends BaseView<LoginPresenter> {

        void setEmptyContent(List<Datas> mList);

        void setContent(List<Datas> mList, int flag);
    }

}
