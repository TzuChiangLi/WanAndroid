package com.lzq.wanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.hjq.bar.TitleBar;
import com.lzq.wanandroid.BaseActivity;
import com.lzq.wanandroid.Contract.WebContract;
import com.lzq.wanandroid.Presenter.WebPresenter;
import com.lzq.wanandroid.R;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;

public class WebActivity extends BaseActivity implements WebContract.View {
    @BindView(R.id.web_titlebar)
    TitleBar mTitleBar;
    @BindView(R.id.web_x5_view)
    WebView mWebView;
    private WebContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        if (mPresenter == null) {
            mPresenter= WebPresenter.createPresenter(this);
        }
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        String URL = intent.getStringExtra("URL");
        if (TextUtils.isEmpty(URL)) {
            URL = "http://www.baidu.com";
        }
        mPresenter.getContent(URL);
    }

    private void initView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(mWebView);
    }


    @Override
    public void setPresenter(WebContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setContent(String URL) {
        mWebView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        mWebView.loadUrl(URL);          //调用loadUrl方法为WebView加入链接
    }
}
