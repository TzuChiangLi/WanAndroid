package com.lzq.wanandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.lzq.wanandroid.base.BaseActivity;
import com.lzq.wanandroid.api.WebContract;
import com.lzq.wanandroid.presenter.WebPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.view.custom.ProgressWebview;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity implements WebContract.View {
    @BindView(R.id.web_titlebar)
    TitleBar mTitleBar;
    @BindView(R.id.web_x5_view)
    ProgressWebview mWebView;
    private WebContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        if (mPresenter == null) {
            mPresenter = WebPresenter.createPresenter(this);
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
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finishActivity();
                }
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                finishActivity();
            }
        });
        ImmersionBar.with(this).statusBarColor(R.color.bg_daily_mode).autoDarkModeEnable(true).fitsSystemWindows(true).keyboardEnable(true).init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if (outView != null && outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
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


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finishActivity();
        }
    }
}
