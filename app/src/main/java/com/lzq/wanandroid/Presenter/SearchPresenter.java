package com.lzq.wanandroid.Presenter;

import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Net.HomeTask;
import com.lzq.wanandroid.Utils.StringUtils;

import java.util.List;

public class SearchPresenter implements Contract.SearchPresenter, LoadTasksCallBack {
    private Contract.SearchView mView;
    private HomeTask mTask;

    public SearchPresenter(Contract.SearchView mView, HomeTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    public static SearchPresenter createPresenter(Contract.SearchView mView, HomeTask mTask) {
        return new SearchPresenter(mView, mTask);
    }


    @Override
    public void getHotKey() {
        mTask.execute(this, 0, 0, StringUtils.TYPE_HOT_KEY);
    }

    @Override
    public void onSuccess(Object o, int flag) {
        switch (flag) {
            case StringUtils.TYPE_HOT_KEY:

                List<Data> mList = (List<Data>) o;
                if (mList.size() != 0 || mList != null) {
                    String[] keys = new String[mList.size()];
                    for (int i = 0; i < mList.size(); i++) {
                        keys[i] = mList.get(i).getName();
                    }
                    mView.setHotKey(keys);
                }
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
