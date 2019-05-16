package com.lzq.wanandroid.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.Net.HomeTask;
import com.lzq.wanandroid.Presenter.SearchPresenter;
import com.lzq.wanandroid.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements Contract.SearchView {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.search_flowlayout)
    TagFlowLayout mFlowLayout;
    private Contract.SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarColor(R.color.bg_daily_mode).autoDarkModeEnable(true).fitsSystemWindows(true).keyboardEnable(true).init();
        if (mPresenter == null) {
            mPresenter = SearchPresenter.createPresenter(this, HomeTask.getInstance());
        }
        mPresenter.getHotKey();

    }

    @Override
    public void setHotKey(String[] keys) {
        Log.d(TAG, "----setHotKey: "+keys[1]);
        final LayoutInflater mInflater = LayoutInflater.from(this);
        mFlowLayout.setAdapter(new TagAdapter<String>(keys) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.flow_tv_item,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    @Override
    public void setPresenter(Contract.SearchPresenter presenter) {
        mPresenter = presenter;
    }
}
