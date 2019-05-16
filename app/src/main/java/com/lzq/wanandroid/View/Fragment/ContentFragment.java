package com.lzq.wanandroid.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.BaseFragment;
import com.lzq.wanandroid.Contract.OffAccountContract;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.Net.AccountTask;
import com.lzq.wanandroid.Presenter.AccountContentPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.View.Adapter.ContentAdapter;
import com.lzq.wanandroid.View.WebActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentFragment extends BaseFragment implements OffAccountContract.AccountContentView {
    private static final String TAG = "ContentFragment";
    @BindView(R.id.rv_account_content)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_account_content)
    SmartRefreshLayout mRefreshView;
    private OffAccountContract.AccountContentPresenter mPresenter;
    private ContentAdapter mAdapter;
    private List<Datas> mContentList = new ArrayList<>();
    private int page = 1, maxPage;
    private View mView;


    //创建
    public static ContentFragment newInstance() {
        return new ContentFragment();
    }

    public ContentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);
        mView = view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRefreshView.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        mRefreshView.setReboundDuration(300);//回弹动画时长（毫秒）
        mRefreshView.setEnableRefresh(true);//是否启用下拉刷新功能
        if (mPresenter == null) {
            mPresenter = AccountContentPresenter.createPresenter(this, new AccountTask(), 0);
        } else {
            mPresenter.initView();
            mPresenter.getContent(mPresenter.showID(), page);
        }
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getContent(mPresenter.showID(), page);
                mRefreshView.finishRefresh();
            }
        });
        mRefreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                mPresenter.addContent(mPresenter.showID(), page);
                mRefreshView.finishLoadMore(2000);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void setEmptyContent(List<Datas> mList) {
        mAdapter = new ContentAdapter(mView, R.layout.rv_article, mList);
        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setUpFetchEnable(true);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void goWebActivity(String URL) {
        Intent intent = new Intent(ActivityUtils.getActivityByView(mView), WebActivity.class);
        intent.putExtra("URL", URL);
        startActivity(mView, intent);
    }

    @Override
    public void collectedArticle(int position, boolean isCollect) {
        mContentList.get(position).setCollect(isCollect);
        mAdapter.notifyItemChanged(position);
        ToastUtils.show(isCollect ? "收藏成功" : "取消收藏");
    }


    @Override
    public void setContent(final List<Datas> mList, int flag) {
        switch (flag) {
            case 0:
                mContentList.clear();
                mContentList = mList;
                mAdapter = new ContentAdapter(mView, R.layout.rv_article, mContentList);
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 1:
//                for (int i = 0; i < mList.size(); i++) {
//                    mContentList.add(mList.get(i));
//                }
                mAdapter.addData(mList);
                break;
        }
        mRecyclerView.setNestedScrollingEnabled(true);
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
    public void setPresenter(OffAccountContract.AccountContentPresenter presenter) {
        if (mPresenter == null) {
            Log.d(TAG, "----setPresenter: null");
        }
        mPresenter = presenter;

    }
}
