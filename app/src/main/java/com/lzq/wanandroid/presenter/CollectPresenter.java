package com.lzq.wanandroid.Presenter;

import android.util.Log;

import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.LoadTasksCallBack;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Base.BasePresenter;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CollectPresenter extends BasePresenter implements Contract.CollectPresenter, LoadTasksCallBack<Object> {
    private Contract.CollectView mView;
    private WebTask mTask;

    public CollectPresenter(Contract.CollectView mView, WebTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    public static CollectPresenter createPresenter(Contract.CollectView mView, WebTask mTask) {
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
        mTask.execute(this, StringUtils.TYPE_COLLECT_CONTENT_LOAD, 0);
    }

    @Override
    public void getSelectedURL(String URL) {
        mView.goWebActivity(URL);
    }

    @Override
    public void cancelCollect(int ID, int position, int orginID) {
        mTask.execute(this, StringUtils.TYPE_COLLECT_NO_USER, ID, position, orginID);
    }


    @Override
    public void onSuccess(Object o, int... params) {
        switch (params[0]) {
            case StringUtils.TYPE_COLLECT_CONTENT_LOAD:
                List<Datas> mList = (List<Datas>) o;
                Log.d("data", "----onSuccess: 收藏" + mList.size());
                if (mList.size() != 0 || mList != null) {

                    for (int i = 0; i < mList.size(); i++) {
                        mList.get(i).setCollect(true);
                    }
                    mView.setCollectList(mList);
                } else {
                    mView.setCollectList(null);
                }
                break;
            case StringUtils.TYPE_COLLECT_NO:
                mView.removeItem((Integer) o);
                break;
            default:
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
        mView.onFinishLoad();
    }


}
