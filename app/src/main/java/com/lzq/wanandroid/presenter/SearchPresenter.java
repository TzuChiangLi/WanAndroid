package com.lzq.wanandroid.presenter;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.api.Contract;
import com.lzq.wanandroid.api.LoadTasksCallBack;
import com.lzq.wanandroid.base.BasePresenter;
import com.lzq.wanandroid.model.Data;
import com.lzq.wanandroid.api.WebTask;
import com.lzq.wanandroid.model.Event;
import com.lzq.wanandroid.model.SearchResult;
import com.lzq.wanandroid.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

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
        for (int i = 0; i < 7; i++) {
            mList.add(new SearchResult.DataBean.Datas());
        }
        mView.initView(mList);
    }

    @Override
    public void getHotKeyContent(String hotkeys,int page) {
        mTask.execute(this,StringUtils.TYPE_HOTKEY_CONTENT_LOAD,hotkeys,String.valueOf(page));
    }
    @Override
    public void addHotKeyContent(String hotkeys,int page) {
        mTask.execute(this,StringUtils.TYPE_HOTKEY_CONTENT_ADD,hotkeys,String.valueOf(page));
    }

    @Override
    public void collectArticle(int ID, boolean isCollect, int position) {
        if (SPUtils.getInstance("userinfo").getBoolean("isLogin")) {
            if (isCollect) {
                mTask.execute(this, StringUtils.TYPE_COLLECT_NO, ID, position);
            } else {
                mTask.execute(this, StringUtils.TYPE_COLLECT_YES, ID, position);
            }
        } else {
            Event event = new Event();
            event.target = Event.TARGET_MAIN;
            event.type = Event.TYPE_NEED_LOGIN;
            EventBus.getDefault().post(event);
        }
    }

    @Override
    public void getSelectedURL(String URL) {
        mView.goWebActivity(URL);
    }

    @Override
    public void onSuccess(Object o,int...params) {
        switch (params[0]) {
            case StringUtils.TYPE_COLLECT_NO:
                int position = (int) o;
                mView.collectedArticle(position, false);
                break;
            case StringUtils.TYPE_COLLECT_YES:
                position = (int) o;
                mView.collectedArticle(position, true);
                break;
            case  StringUtils.TYPE_HOT_KEY_CONTENT_ADD:
            case  StringUtils.TYPE_HOT_KEY_CONTENT_LOAD:
                SearchResult.DataBean dataBean= (SearchResult.DataBean) o;
                if (dataBean.getCurPage()!=0&&dataBean.getDatas()!=null){
                    mView.setHotKeyContent(params[0],dataBean.getDatas());
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
