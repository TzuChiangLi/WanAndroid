package com.lzq.wanandroid.Presenter;

import com.lzq.wanandroid.Contract.HomeContract;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Net.HomeTask;
import com.lzq.wanandroid.Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter, LoadTasksCallBack<List<Data>> {
    private static final String TAG = "HomePresenter";
    private HomeTask mTask;
    private HomeContract.View mView;
    private List<Data> mTopArticleList = new ArrayList<>();


    public HomePresenter(HomeContract.View mView, HomeTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    public static HomePresenter createPresenter(HomeContract.View mView, HomeTask mTask) {
        return new HomePresenter(mView, mTask);
    }

    @Override
    public void onSuccess(List<Data> mList, int type) {
        switch (type) {
            case StringUtils.TYPE_HOME_MORE_ARTICLE:

                break;
            case StringUtils.TYPE_HOME_TOP_ARTICLE:
                mView.setHomeTopArticle(mList);
                break;
            case StringUtils.TYPE_HOME_IMG_BANNER:
                List mImgList = new ArrayList();
                for (int i = 0; i < mList.size(); i++) {
                    mImgList.add(mList.get(i).getImagePath());
                }
                mView.setHomeTopImgBanner(mImgList);
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
        mTask.execute(null, null, this);
    }

    @Override
    public void getHomeTopImgBanner() {
        mTask.execute(null, null, this);
    }

    @Override
    public void getSelectedURL(String URL) {
//        mView.goWebActivity(URL);
    }


}
