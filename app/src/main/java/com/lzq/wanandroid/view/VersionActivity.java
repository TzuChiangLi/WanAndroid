package com.lzq.wanandroid.view;

import android.os.Bundle;
import android.view.View;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.lzq.wanandroid.base.BaseActivity;
import com.lzq.wanandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VersionActivity extends BaseActivity {
    @BindView(R.id.ver_titlebar)
    TitleBar mTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finishActivity();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }
}
