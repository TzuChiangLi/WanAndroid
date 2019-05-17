package com.lzq.wanandroid.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzq.wanandroid.BaseFragment;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.DataBase.Tree;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Net.WebTask;
import com.lzq.wanandroid.Presenter.TreePresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.View.Adapter.TreeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TreeFragment extends BaseFragment implements Contract.TreeView {
    private static final String TAG = "TreeFragment";
    @BindView(R.id.rv_tree_flow)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.tree_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tree_refresh)
    SmartRefreshLayout mRefreshView;
    private Contract.TreePresenter mPresenter;
    private List<Data> mList=new ArrayList<>();
    private TreeAdapter mAdapter;

    public TreeFragment() {
    }

    public TreeFragment newInstance() {
        return new TreeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        ButterKnife.bind(this, view);
        if (mPresenter == null) {
            WebTask mTask = WebTask.getInstance();
            mPresenter = TreePresenter.createPresenter(this, mTask);
        }
        return view;
    }

    @Override
    public void setPresenter(Contract.TreePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initDataList(List<Tree> mList) {

    }

    @Override
    public void isEmptyLocal() {
        mPresenter.loadOnline();
    }

    @Override
    public void onLoadParentName(List<Data> data) {
        mAdapter=new TreeAdapter(R.layout.rv_tree_item,data);

    }
}
