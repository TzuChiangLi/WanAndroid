package com.lzq.wanandroid.Presenter;

import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Net.HomeTask;
import com.lzq.wanandroid.Utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements Contract.HomePresenter, LoadTasksCallBack<Object> {
    private static final String TAG = "HomePresenter";
    private HomeTask mTask;
    private Contract.HomeView mView;
    private List<Data> mTopArticleList = new ArrayList<>();


    public HomePresenter(Contract.HomeView mView, HomeTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    public static HomePresenter createPresenter(Contract.HomeView mView, HomeTask mTask) {
        return new HomePresenter(mView, mTask);
    }

    @Override
    public void onSuccess(Object data, int type) {
        int position;
        try {
            switch (type) {
                case StringUtils.TYPE_COLLECT_NO:
                    position = (int) data;
                    mView.collectedArticle(position, false);
                    break;
                case StringUtils.TYPE_COLLECT_YES:
                    position = (int) data;
                    mView.collectedArticle(position, true);
                    break;
                case StringUtils.TYPE_HOME_TOP_ARTICLE:
                    mView.setHomeTopArticle((List<Data>) data);
                    break;
                case StringUtils.TYPE_HOME_IMG_BANNER:
                    List<Data> mImgList = new ArrayList();
                    List<Data> tList = (List<Data>) data;
                    for (int i = 0; i < tList.size(); i++) {
                        mImgList.add(new Data(tList.get(i).getImagePath(), tList.get(i).getUrl()));
                    }
                    mView.setHomeTopImgBanner(mImgList);
                    break;
            }
        } catch (Exception e) {
            Log.d(TAG, "----onSuccess: " + e.getMessage());
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

    @Override
    public void initView() {
        if (mTopArticleList != null || mTopArticleList.size() != 0) {
            mTopArticleList.clear();
        }
        for (int i = 0; i < 3; i++) {
            mTopArticleList.add(new Data("", "", "", "", ""));
        }
        mView.setEmptyTopArticle(mTopArticleList);
    }

    @Override
    public void getHomeTopArticle() {
        mTask.execute(null, "1", this);
    }

    @Override
    public void getHomeTopImgBanner() {
        mTask.execute(null, "2", this);
    }


    @Override
    public void getSelectedURL(String URL) {
        mView.goWebActivity(URL);
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