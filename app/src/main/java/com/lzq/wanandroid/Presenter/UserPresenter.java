package com.lzq.wanandroid.Presenter;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.R;

public class UserPresenter implements Contract.UserPresenter {
    private static final String TAG = "UserPresenter";
    private Contract.UserView mView;

    public UserPresenter(Contract.UserView mView) {
        this.mView = mView;
    }

    public static UserPresenter createPresenter(Contract.UserView mView) {
        return new UserPresenter(mView);
    }

    @Override
    public void initView() {
        if (SPUtils.getInstance("userinfo").getBoolean("isLogin")){
            mView.setUserInfo(SPUtils.getInstance("userinfo").getString("id"),SPUtils.getInstance("userinfo").getString("username"));
        }else {
            mView.setUserInfo("******","游客");
        }
    }

    @Override
    public void initTabView() {
        String[] tabName={"收藏","ToDo"};
        int[] imgTab={R.mipmap.collect,R.mipmap.todo};
        mView.setTabView(tabName,imgTab);
    }

}
