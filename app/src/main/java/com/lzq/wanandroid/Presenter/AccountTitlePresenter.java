package com.lzq.wanandroid.Presenter;

import android.util.Log;

import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.Contract.OffAccountContract;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Net.AccountTask;

import java.util.ArrayList;
import java.util.List;

public class AccountTitlePresenter implements OffAccountContract.AccountTitlePresenter, LoadTasksCallBack<List<Data>> {
    private static final String TAG = "AccountTitlePresenter";
    private AccountTask mTask;
    private OffAccountContract.AccountTitleView mView;
    private List<Data> mList = new ArrayList<>();

    public AccountTitlePresenter(OffAccountContract.AccountTitleView mView, AccountTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    @Override
    public void onSuccess(List<Data> mList, int flag) {
        switch (flag) {
            case StringUtils.TYPE_ACCOUNT_TITLE:
                mView.setTitleText(mList);
                break;
        }
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


    @Override
    public void initView() {
        if (mList == null || mList.size() == 0) {
            mList.clear();
        }
        for (int i = 0; i < 5; i++) {
            mList.add(new Data("", "", "", "", ""));
        }
        mView.setEmptyContent(mList);
    }

    @Override
    public void getTitleText() {
        mTask.execute(null, null, this);
    }

    @Override
    public void getContent(int ID, int page) {
    }

}
