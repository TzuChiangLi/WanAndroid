package com.lzq.wanandroid.View.Custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.lzq.wanandroid.R;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class ProgressWebview extends WebView {
    private ProgressBar progressbar;  //进度条

    private int progressHeight = 8;  //进度条的高度，默认10px

    public ProgressWebview(Context context) {
        super(context);
        initView(context);
    }

    public ProgressWebview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);

    }


    private void initView(Context context) {

        //开启js脚本支持
        getSettings().setJavaScriptEnabled(true);

        //创建进度条
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        //设置加载进度条的高度
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, progressHeight, 0, 0));

        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
        progressbar.setProgressDrawable(drawable);

        //添加进度到WebView
        addView(progressbar);

        //适配手机大小
        getSettings().setUseWideViewPort(true);
        getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setDisplayZoomControls(false);


        setWebChromeClient(new WVChromeClient());
        setWebViewClient(new WVClient());
    }

    private class WVChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {


            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }

            if (mListener != null) {
                mListener.onProgressChange(view, newProgress);
            }

            super.onProgressChanged(view, newProgress);
        }

    }

    private class WVClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            //在当前Activity打开
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //https忽略证书问题
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            progressbar.setVisibility(GONE);
            if (mListener != null) {
                mListener.onPageFinish(view);
            }

            super.onPageFinished(view, url);

        }

    }
    private onWebViewListener mListener;

    public void setOnWebViewListener(onWebViewListener listener) {
        this.mListener = listener;
    }

    //进度回调接口
    public interface onWebViewListener {
        void onProgressChange(WebView view, int newProgress);

        void onPageFinish(WebView view);
    }

}
