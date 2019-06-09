package com.lzq.wanandroid.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Base.BaseActivity;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.SearchResult;
import com.lzq.wanandroid.Presenter.SearchPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.View.Adapter.ArticleAdapter;
import com.lzq.wanandroid.View.Adapter.SearchResultAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity implements Contract.SearchView, OnTitleBarListener {
    @BindView(R.id.search_result_titlebar)
    TitleBar mTitleBar;
    @BindView(R.id.search_result_tv)
    TextView mHotKeyTv;
    @BindView(R.id.search_result_refresh_content)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.search_result_rv)
    RecyclerView mRecyclerView;
    private Contract.SearchPresenter mPresenter;
    private int page = 0;
    private SearchResultAdapter mAdapter;
    private List<SearchResult.DataBean.Datas> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final String hotkeys = intent.getStringExtra("HOT_KEY");
        mHotKeyTv.setText(hotkeys);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRefreshView.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        mRefreshView.setReboundDuration(300);//回弹动画时长（毫秒）
        mRefreshView.setEnableRefresh(true);//是否启用下拉刷新功能
        if (mPresenter == null) {
            mPresenter = SearchPresenter.createPresenter(this, WebTask.getInstance());
        }
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mPresenter.getHotKeyContent(hotkeys, page);
                mRefreshView.finishRefresh();
            }
        });
        mRefreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                mPresenter.addHotKeyContent(hotkeys, page);
                mRefreshView.finishLoadMore(2000);
            }
        });
        mTitleBar.setOnTitleBarListener(this);
        mPresenter.initView();
        mPresenter.getHotKeyContent(hotkeys, page);
    }

    @Override
    public void setHotKey(String[] keys) {

    }

    @Override
    public void initView(List<SearchResult.DataBean.Datas> data) {
        mAdapter = new SearchResultAdapter(StringUtils.RV_ITEM_IMG, R.layout.rv_article_normal, data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setHotKeyContent(int flag, final List<SearchResult.DataBean.Datas> datas) {
        if (flag == StringUtils.TYPE_HOT_KEY_CONTENT_LOAD) {
            mList=datas;
            mAdapter = new SearchResultAdapter(StringUtils.RV_ITEM_IMG, R.layout.rv_article_normal, datas);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.addData(datas);
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.getSelectedURL(mAdapter.getData().get(position).getLink());
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rv_article_imgbtn_save:
                        mPresenter.collectArticle(mAdapter.getData().get(position).getId(), mAdapter.getData().get(position).isCollect(), position);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void collectedArticle(int position, boolean isCollect) {
        mAdapter.getData().get(position).setCollect(isCollect);
        mAdapter.notifyItemChanged(position);
        ToastUtils.show(isCollect ? "收藏成功" : "取消收藏");
    }

    @Override
    public void goWebActivity(String URL) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("URL", URL);
        startActivity(intent);
    }

    @Override
    public void setPresenter(Contract.SearchPresenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onLeftClick(View v) {
        finishActivity();
    }

    @Override
    public void onTitleClick(View v) {
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onRightClick(View v) {

    }
}
