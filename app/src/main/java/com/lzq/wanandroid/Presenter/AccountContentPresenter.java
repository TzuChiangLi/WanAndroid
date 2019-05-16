package com.lzq.wanandroid.Presenter;

import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.Contract.OffAccountContract;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Net.AccountTask;
import com.lzq.wanandroid.Utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AccountContentPresenter implements OffAccountContract.AccountContentPresenter, LoadTasksCallBack<Object> {
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

    public static AccountContentPresenter createPresenter(OffAccountContract.AccountContentView mView, AccountTask mTask, int ID){
        return new AccountContentPresenter(mView,mTask,ID);
    }


    @Override
    public void onSuccess(Object data, int flag) {
        int position;
        try {
            switch (flag) {
                case StringUtils.TYPE_COLLECT_NO:
                    position = (int) data;
                    mView.collectedArticle(position, false);
                    break;
                case StringUtils.TYPE_COLLECT_YES:
                    position = (int) data;
                    mView.collectedArticle(position, true);
                    break;
                case StringUtils.TYPE_ACCOUNT_CONTENT_LOAD:
                    mView.setContent((List<Datas>) data, StringUtils.TYPE_ACCOUNT_CONTENT_LOAD);
                    break;
                case StringUtils.TYPE_ACCOUNT_CONTENT_ADD:
                    mView.setContent((List<Datas>) data, StringUtils.TYPE_ACCOUNT_CONTENT_ADD);
                    break;
            }
        }catch (Exception e){
            Log.d(TAG, "----onSuccess: "+e.getMessage());
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
        mTask.execute(this,ID, page, StringUtils.TYPE_ACCOUNT_CONTENT_LOAD);
    }

    @Override
    public void getSelectedURL(String URL) {
        mView.goWebActivity(URL);
    }

    @Override
    public void addContent(int ID, int page) {
        mTask.execute(this,ID, page, StringUtils.TYPE_ACCOUNT_CONTENT_ADD);
    }

    @Override
    public int showID() {
        return ID;
    }

    @Override
    public void collectArticle(int ID, boolean isCollect, int position) {
        if (SPUtils.getInstance("userinfo").getBoolean("isLogin")) {
            if (isCollect) {
                mTask.execute(this,ID, position, StringUtils.TYPE_COLLECT_NO);
            } else {
                mTask.execute(this,ID, position, StringUtils.TYPE_COLLECT_YES);
            }
        } else {
            Event event = new Event();
            event.target = Event.TARGET_MAIN;
            event.type = Event.TYPE_NEED_LOGIN;
            EventBus.getDefault().post(event);
        }
    }

}
