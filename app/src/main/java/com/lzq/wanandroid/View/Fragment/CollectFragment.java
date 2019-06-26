package com.lzq.wanandroid.View.Fragment;

import android.content.Intent;
import android.graphics.Canvas;
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
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzq.wanandroid.Base.BaseFragment;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Presenter.CollectPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.View.Adapter.ContentAdapter;
import com.lzq.wanandroid.View.Animation.CollectAnim;
import com.lzq.wanandroid.View.LoginActivity;
import com.lzq.wanandroid.View.WebActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectFragment extends BaseFragment implements Contract.CollectView {
    private static final String TAG = "CollectFragment";
    @BindView(R.id.rv_collect_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_collect_content)
    SmartRefreshLayout mRefreshView;
    private Contract.CollectPresenter mPresenter;
    private View mView;
    private ContentAdapter mAdapter;
    private List<Datas> mList = new ArrayList<>();

    public CollectFragment() {
    }

    public static CollectFragment newInstance() {
        return new CollectFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)){
        EventBus.getDefault().register(this);}
        mView = view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));

        if (mPresenter == null) {
            mPresenter = CollectPresenter.createPresenter(this, WebTask.getInstance());
        }
        mPresenter.initView();
        mPresenter.getCollectList();
        mRefreshView.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        mRefreshView.setReboundDuration(300);//回弹动画时长（毫秒）
        mRefreshView.setEnableRefresh(true);//是否启用下拉刷新功能

        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (SPUtils.getInstance("userinfo").getBoolean("isLogin")) {
                    mPresenter.getCollectList();
                    mRecyclerView.setVisibility(View.VISIBLE);
                    if (onFinishLoad()) {
                        mRefreshView.finishRefresh();
                    }
                } else {
                    mRefreshView.finishRefresh();
                    startActivity(mView,LoginActivity.class);
                }
            }
        });
        return view;
    }

    @Override
    public void setEmptyList(List<Datas> mList) {
        this.mList = mList;
        mAdapter = new ContentAdapter(mView, R.layout.rv_article_normal, mList);
        mRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        mRefreshView.setEnableLoadMore(false);
        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setUpFetchEnable(true);
    }

    @Override
    public void setCollectList(final List<Datas> data) {
        mList.clear();
        mList = data;
        mAdapter = new ContentAdapter(mView, R.layout.rv_article_normal, mList);
        mRecyclerView.setNestedScrollingEnabled(true);//禁止滑动
        mRefreshView.setEnableLoadMore(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rv_article_imgbtn_save:
                        CollectAnim.getInstance().show(view);
                        mPresenter.cancelCollect(data.get(position).getId(), position, data.get(position).getOriginId());
                        break;
                    default:
                        break;
                }
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.getSelectedURL(data.get(position).getLink());
            }
        });

    }

    @Override
    public void goWebActivity(String URL) {
        Intent intent = new Intent(ActivityUtils.getActivityByView(mView), WebActivity.class);
        intent.putExtra("URL", URL);
        startActivity(mView, intent);
    }

    @Override
    public void removeItem(int position) {
        mAdapter.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onFinishLoad() {
        return true;
    }

    @Override
    public void setPresenter(Contract.CollectPresenter presenter) {
        mPresenter = presenter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_COLLECT) {
            switch (event.type) {
                case Event.TYPE_LOGIN_SUCCESS:
                case Event.TYPE_COLLECT_REFRESH:
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRefreshView.autoRefresh();
                    break;
                case Event.TYPE_COLLECT_LOGOUT:
                    mRecyclerView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
        if (event.target==Event.TARGET_RESFRESH){
            if (event.type==Event.TYPE_REFRESH_ISLOGIN){
                mRefreshView.autoRefresh();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
