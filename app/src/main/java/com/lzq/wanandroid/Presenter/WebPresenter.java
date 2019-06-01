package com.lzq.wanandroid.Presenter;

import com.lzq.wanandroid.Api.WebContract;
import com.lzq.wanandroid.Base.BasePresenter;

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
