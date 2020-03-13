package com.lzq.wanandroid.presenter;

import com.lzq.wanandroid.api.WebTask;
import com.lzq.wanandroid.base.BasePresenter;
import com.lzq.wanandroid.model.Children;
import com.lzq.wanandroid.utils.StringUtils;
import com.lzq.wanandroid.api.OffAccountContract;
import com.lzq.wanandroid.api.LoadTasksCallBack;
import com.lzq.wanandroid.model.Data;

import java.util.ArrayList;
import java.util.List;

public class TitlePresenter extends BasePresenter implements OffAccountContract.AccountTitlePresenter, LoadTasksCallBack<List<Data>> {
    private static final String TAG = "TitlePresenter";
    private WebTask mTask;
    private OffAccountContract.AccountTitleView mView;
    private List<Data> mList = new ArrayList<>();

    public TitlePresenter(OffAccountContract.AccountTitleView mView, WebTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    @Override
    public void onSuccess(List<Data> mList, int...params) {
        switch (params[0]) {
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
    public void initView(String[] tabName) {
        if (mList == null || mList.size() == 0) {
            mList.clear();
        }
        for (int i = 0; i < tabName.length; i++) {
            mList.add(new Data("", "", "", "", tabName[i]));
        }
        mView.setAccountEmptyTitle(mList);
    }

    @Override
    public void initView(int position, String[] tabName, int[] tabID) {
        List<Children> mList=new ArrayList<>();
        for (int i = 0; i < tabID.length; i++) {
            mList.add(new Children(tabID[i],tabName[i]));
        }
        mView.setArticlesContent(position,mList);
    }

    @Override
    public void getTitleText(int type) {
        mTask.execute(this,type);
    }

    @Override
    public void getContent(int ID, int page) {
    }

}
