package com.lzq.wanandroid.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzq.wanandroid.BaseFragment;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.Net.AccountTask;
import com.lzq.wanandroid.Presenter.CollectPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.View.Adapter.ArticleAdapter;
import com.lzq.wanandroid.View.Adapter.ContentAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectFragment extends BaseFragment implements Contract.CollectView {
    private static final String TAG = "CollectFragment";
    @BindView(R.id.rv_collect_list)
    RecyclerView mRecyclerView;
    private Contract.CollectPresenter mPresenter;
    private View mView;
    private ContentAdapter mAdapter;


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
        mView=view;
        Log.d(TAG, "----onCreateView: ");
        if (mPresenter==null){
            mPresenter= CollectPresenter.createPresenter(this, AccountTask.getInstance());
        }
        mPresenter.initView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        mPresenter.getCollectList();
        return view;
    }

    @Override
    public void setEmptyList(List<Datas> mList) {
        mAdapter=new ContentAdapter(mView,R.layout.rv_article,mList);
        mAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setUpFetchEnable(true);
    }

    @Override
    public void setCollectList(List<Datas> mList) {

    }

    @Override
    public void removeItem() {

    }

    @Override
    public void setPresenter(Contract.CollectPresenter presenter) {
        mPresenter = presenter;
    }
}
