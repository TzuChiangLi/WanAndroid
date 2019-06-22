package com.lzq.wanandroid.View;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lzq.wanandroid.Base.BaseActivity;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Model.SearchResult;
import com.lzq.wanandroid.Presenter.SearchPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.View.Custom.ClearEditText;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements Contract.SearchView {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.search_edt)
    ClearEditText mSearchEdt;
    @BindView(R.id.search_flowlayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.search_imgbtn)
    ImageButton mSearchBtn;
    private Contract.SearchPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarColor(R.color.bg_daily_mode).autoDarkModeEnable(true).fitsSystemWindows(true).keyboardEnable(true).init();
        if (mPresenter == null) {
            mPresenter = SearchPresenter.createPresenter(this, WebTask.getInstance());
        }
        mPresenter.getHotKey();
    }

    @OnClick(R.id.search_imgbtn)
    public void doSearch() {
        if (!TextUtils.isEmpty(mSearchEdt.getText().toString())) {
            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("HOT_KEY", mSearchEdt.getText().toString());
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void setHotKey(final String[] keys) {
        showSoftInputUtil(mSearchEdt);
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
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (TextUtils.isEmpty(mSearchEdt.getText().toString())) {
                    mSearchEdt.setText(keys[position]);
                } else {
                    mSearchEdt.setText(mSearchEdt.getText().append(" " + keys[position]));
                }
                return true;
            }
        });
    }

    @Override
    public void initView(List data) {

    }


    @Override
    public void setHotKeyContent(int flag,List<SearchResult.DataBean.Datas> datas) {

    }

    @Override
    public void collectedArticle(int position, boolean isCollect) {

    }

    @Override
    public void goWebActivity(String URL) {

    }


    @Override
    public void setPresenter(Contract.SearchPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideSoftInputUtil();
    }
}
