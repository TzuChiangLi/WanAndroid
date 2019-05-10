package com.lzq.wanandroid.Presenter;

import android.util.Log;

import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.Contract.OffAccountContract;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.Net.AccountTask;

import java.util.ArrayList;
import java.util.List;

public class AccountContentPresenter implements OffAccountContract.AccountContentPresenter, LoadTasksCallBack<List<Datas>> {
    private static final String TAG = "AccountContentPresenter";
    private AccountTask mTask;
    private OffAccountContract.AccountContentView mView;
    private List<Datas> mList = new ArrayList<>();
    private int ID;


    public AccountContentPresenter(OffAccountContract.AccountContentView mView, AccountTask mTask, int ID) {
        this.mView = mView;
        this.mTask = mTask;
        this.ID = ID;
    }


    @Override
    public void onSuccess(List<Datas> mList, int flag) {
        switch (flag) {
            case StringUtils.TYPE_ACCOUNT_CONTENT_LOAD:
                mView.setContent(mList, StringUtils.TYPE_ACCOUNT_CONTENT_LOAD);
                break;
            case StringUtils.TYPE_ACCOUNT_CONTENT_ADD:
                mView.setContent(mList, StringUtils.TYPE_ACCOUNT_CONTENT_ADD);
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
        Log.d(TAG, "----initView: ");
        for (int i = 0; i < 5; i++) {
            mList.add(new Datas("", "", "", "", ""));
        }
        mView.setEmptyContent(mList);
    }

    @Override
    public void getTitleText() {
        mTask.execute(null, null, this);
    }

    @Override
    public void getContent(int ID, int page) {
        mTask.execute(ID, page, StringUtils.TYPE_ACCOUNT_CONTENT_LOAD, this);
    }

    @Override
    public void addContent(int ID, int page) {
        mTask.execute(ID, page, StringUtils.TYPE_ACCOUNT_CONTENT_ADD, this);
    }

    @Override
    public int showID() {
        return ID;
    }

}
