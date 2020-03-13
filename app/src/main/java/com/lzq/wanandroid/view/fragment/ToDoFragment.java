package com.lzq.wanandroid.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzq.wanandroid.base.BaseFragment;
import com.lzq.wanandroid.api.Contract;
import com.lzq.wanandroid.model.Datas;
import com.lzq.wanandroid.model.Event;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.view.adapter.ContentAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ToDoFragment extends BaseFragment implements Contract.ToDoView {
    private static final String TAG = "todoFragment";
//    @BindView(R.id.rv_todo_list)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.refresh_todo_content)
//    SmartRefreshLayout mRefreshView;
    private Contract.ToDoPresenter mPresenter;
    private View mView;
    private ContentAdapter mAdapter;
    private List<Datas> mList = new ArrayList<>();

    public ToDoFragment() {
    }

    public static ToDoFragment newInstance() {
        return new ToDoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)){
        EventBus.getDefault().register(this);}
        mView = view;
        return view;
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setPresenter(Contract.ToDoPresenter presenter) {
        mPresenter=presenter;
    }
}
