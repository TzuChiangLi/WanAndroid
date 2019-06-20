package com.lzq.wanandroid.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Base.BaseFragment;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.Model.ProjectItem;
import com.lzq.wanandroid.Presenter.ProjectItemPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.View.Adapter.ArticleAdapter;
import com.lzq.wanandroid.View.Adapter.ContentAdapter;
import com.lzq.wanandroid.View.Adapter.ProjectItemAdapter;
import com.lzq.wanandroid.View.Animation.CollectAnim;
import com.lzq.wanandroid.View.WebActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectItemFragment extends BaseFragment implements Contract.ProjectItemView {
    private static final String TAG = "ProjectFragment";
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_content)
    SmartRefreshLayout mRefreshView;
    private ProjectItemAdapter mAdapter;
    private int page = 1;
    private View mView;
    private List<ProjectItem.DataBean.Datas> mContentList = new ArrayList<>();
    private Contract.ProjectItemPresenter mPresenter;

    public ProjectItemFragment() {
    }

    public static ProjectItemFragment newInstance() {
        return new ProjectItemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_item, container, false);
        ButterKnife.bind(this, view);
        mView = view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRefreshView.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        mRefreshView.setReboundDuration(300);//回弹动画时长（毫秒）
        mRefreshView.setEnableRefresh(true);//是否启用下拉刷新功能
        if (mPresenter == null) {
            mPresenter = ProjectItemPresenter.createPresenter(this, WebTask.getInstance(), 0);
        } else {
            mPresenter.initView();
            mPresenter.initProjectData(StringUtils.TYPE_PROJECT_ITEM_LOAD, 1);
        }
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.initProjectData(StringUtils.TYPE_PROJECT_ITEM_LOAD, page);
                mRefreshView.finishRefresh();
            }
        });
        mRefreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                mPresenter.initProjectData(StringUtils.TYPE_PROJECT_ITEM_ADD, page);
                mRefreshView.finishLoadMore(2000);
            }
        });
        return view;
    }

    @Override
    public void setPresenter(Contract.ProjectItemPresenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void setEmptyContent(List<ProjectItem.DataBean.Datas> data, int flag) {
        mAdapter = new ProjectItemAdapter(mView, R.layout.rv_article_img, data);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRefreshView.setEnableLoadMore(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setProjectContent(List<ProjectItem.DataBean.Datas> data, int flag) {
        mRecyclerView.setNestedScrollingEnabled(true);
        mRefreshView.setEnableLoadMore(true);
        if (flag == StringUtils.TYPE_PROJECT_ITEM_LOAD) {
            mContentList.clear();
            mContentList = data;
            mAdapter = new ProjectItemAdapter(mView, R.layout.rv_article_img, mContentList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.addData(data);
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
                        CollectAnim.getInstance().show(view);
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
        Intent intent = new Intent(ActivityUtils.getActivityByView(mView), WebActivity.class);
        intent.putExtra("URL", URL);
        startActivity(mView, intent);
    }
}
