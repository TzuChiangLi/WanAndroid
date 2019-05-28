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
import com.lzq.wanandroid.BaseFragment;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.FlowTagCallBack;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Model.Children;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Presenter.TreePresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.View.Adapter.TreeAdapter;
import com.lzq.wanandroid.View.ArticlesActivity;
import com.lzq.wanandroid.View.WebActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TreeFragment extends BaseFragment implements Contract.TreeView, FlowTagCallBack {
    private static final String TAG = "SystemFragment";
    @BindView(R.id.tree_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tree_refresh)
    SmartRefreshLayout mRefreshView;
    private Contract.TreePresenter mPresenter;
    private TreeAdapter mAdapter;
    private View mView;

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
        mView = view;
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
        mAdapter = new TreeAdapter(R.layout.rv_tree_item, data, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onlineLoad(int type) {
        mPresenter.loadOnline(type);
    }

    @Override
    public void onLoadTreeData(int type, List<Data> data) {
        mAdapter = new TreeAdapter(R.layout.rv_tree_item, data, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onFinishLoad() {
        return true;
    }

    @Override
    public void goWebActivity(String URL) {
        Intent intent = new Intent(ActivityUtils.getActivityByView(mView), WebActivity.class);
        intent.putExtra("URL", URL);
        startActivity(mView, intent);
    }

    @Override
    public void getTreeArticles(int ID, int position, String title, List<Children> children) {
        //获取到点击的位置，所属父类的名称，以及子项的所有名称
        Intent intent = new Intent(ActivityUtils.getActivityByView(mView), ArticlesActivity.class);
        intent.putExtra("TYPE", StringUtils.TYPE_TREE_KNOW_ARTICLES);
        intent.putExtra("TITLE_BAR", title);
        intent.putExtra("ID", ID);
        intent.putExtra("POSITION",position);
        String[] childName=new String[children.size()];
        int[] childID=new int[children.size()];
        for (int i = 0; i < children.size(); i++) {
            childName[i]=children.get(i).getName();
            childID[i]=children.get(i).getId();
        }
        intent.putExtra("TABNAME", childName);
        intent.putExtra("TABID",childID);
        startActivity(mView, intent);
    }

    @Override
    public void getTreeLink(String URL) {

    }
}
