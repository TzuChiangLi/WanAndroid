package com.lzq.wanandroid.Presenter;

import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.LoadTasksCallBack;
import com.lzq.wanandroid.Base.BasePresenter;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Model.SearchResult;
import com.lzq.wanandroid.Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter extends BasePresenter implements Contract.SearchPresenter, LoadTasksCallBack {
    private Contract.SearchView mView;
    private WebTask mTask;

    public SearchPresenter(Contract.SearchView mView, WebTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    public static SearchPresenter createPresenter(Contract.SearchView mView, WebTask mTask) {
        return new SearchPresenter(mView, mTask);
    }


    @Override
    public void getHotKey() {
        mTask.execute(this, StringUtils.TYPE_HOT_KEY);
    }

    @Override
    public void initView() {
        List<SearchResult.DataBean.Datas> mList=new ArrayList();
        for (int i = 0; i < 3; i++) {
            mList.add(new SearchResult.DataBean.Datas());
        }
        mView.initView(mList);
    }

    @Override
    public void getHotKeyContent(String hotkeys,int page) {
        mTask.execute(this,StringUtils.TYPE_HOTKEY_CONTENT,hotkeys,String.valueOf(page));
    }

    @Override
    public void onSuccess(Object o, int flag) {
        switch (flag) {
            case  StringUtils.TYPE_HOT_KEY_CONTENT:
                SearchResult.DataBean dataBean= (SearchResult.DataBean) o;
                if (dataBean.getCurPage()!=0&&dataBean.getDatas()!=null){
                    mView.setHotKeyContent(dataBean.getDatas());
                }
                break;
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
