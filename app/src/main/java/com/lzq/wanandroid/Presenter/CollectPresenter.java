package com.lzq.wanandroid.Presenter;

import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.Net.AccountTask;

import java.util.ArrayList;
import java.util.List;

public class CollectPresenter implements Contract.CollectPresenter {
    private Contract.CollectView mView;
    private AccountTask mTask;

    public CollectPresenter(Contract.CollectView mView, AccountTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    public static CollectPresenter createPresenter(Contract.CollectView mView, AccountTask mTask) {
        return new CollectPresenter(mView, mTask);
    }

    @Override
    public void initView() {
        List<Datas> mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mList.add(new Datas("", "", "", "", ""));
        }
        mView.setEmptyList(mList);
    }

    @Override
    public void getCollectList() {

    }

    @Override
    public void cancelCollect() {

    }
}
