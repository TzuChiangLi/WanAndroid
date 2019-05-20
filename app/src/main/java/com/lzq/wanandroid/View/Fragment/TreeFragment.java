package com.lzq.wanandroid.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzq.wanandroid.BaseFragment;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Net.WebTask;
import com.lzq.wanandroid.Presenter.TreePresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.View.Adapter.TreeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TreeFragment extends BaseFragment implements Contract.TreeView {
    private static final String TAG = "SystemFragment";
    @BindView(R.id.tree_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tree_refresh)
    SmartRefreshLayout mRefreshView;
    private Contract.TreePresenter mPresenter;
    private TreeAdapter mAdapter;

    public TreeFragment() {
    }

    public static TreeFragment newInstance() {
        return new TreeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tree, container, false);
        ButterKnife.bind(this, view);
        if (mPresenter == null) {
            WebTask mTask = WebTask.getInstance();
            mPresenter = TreePresenter.createPresenter(this, mTask);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mPresenter.initView(StringUtils.TYPE_TREE_KNOW);
        mPresenter.loadOnline(StringUtils.TYPE_TREE_KNOW);
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadOnline(StringUtils.TYPE_TREE_KNOW);
                if (onFinishLoad()) {
                    mRefreshView.finishRefresh();
                }
            }
        });
        return view;
    }

    @Override
    public void setPresenter(Contract.TreePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initDataList(int type, List<Data> data) {
        mAdapter = new TreeAdapter(R.layout.rv_tree_item, data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onlineLoad(int type) {
        mPresenter.loadOnline(type);
    }

    @Override
    public void onLoadTreeData(int type, List<Data> data) {
        mAdapter = new TreeAdapter(R.layout.rv_tree_item, data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onFinishLoad() {
        return true;
    }
}
