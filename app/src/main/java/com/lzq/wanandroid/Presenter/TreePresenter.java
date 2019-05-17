package com.lzq.wanandroid.Presenter;

import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.DataBase.Tree;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Net.WebTask;
import com.lzq.wanandroid.Utils.StringUtils;

import org.litepal.LitePal;

import java.util.List;

public class TreePresenter implements Contract.TreePresenter, LoadTasksCallBack {
    private Contract.TreeView mView;
    private WebTask mTask;

    public static TreePresenter createPresenter(Contract.TreeView mView, WebTask mTask) {
        return new TreePresenter(mView, mTask);
    }

    public TreePresenter(Contract.TreeView mView, WebTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    @Override
    public void initView() {
        //先搜索数据库，没数据再联网
        int count = LitePal.count("Tree");
        if (count != 0) {
            //有数据
            List<Tree> data = LitePal.findAll(Tree.class);
            mView.initDataList(data);
        } else {
            //无数据
            mView.isEmptyLocal();
        }
    }

    @Override
    public void loadOnline() {
        mTask.execute(this, StringUtils.TYPE_TREE);
    }

    @Override
    public void onSuccess(Object o, int flag) {
        switch (flag) {
            case StringUtils.TYPE_TREE:
                mView.onLoadParentName((List<Data>) o);
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
}
