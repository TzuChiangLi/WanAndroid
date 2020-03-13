package com.lzq.wanandroid.presenter;

import com.lzq.wanandroid.api.WebContract;
import com.lzq.wanandroid.base.BasePresenter;

public class WebPresenter extends BasePresenter implements WebContract.Presenter {
    private static final String TAG = "WebPresenter";
    private WebContract.View mView;

    public static WebPresenter createPresenter(WebContract.View mView) {
        return new WebPresenter(mView);
    }

    public WebPresenter(WebContract.View mView) {
        this.mView = mView;
    }


    @Override
    public void getContent(String URL) {
        mView.setContent(URL);
    }
}
