package com.lzq.wanandroid.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Base.BaseFragment;
import com.lzq.wanandroid.R;

import butterknife.ButterKnife;

public class ProjectItemFragment extends BaseFragment implements Contract.ProjectItemView {
    private static final String TAG = "ProjectFragment";
    private Contract.ProjectItemPresenter mPresenter;
    public ProjectItemFragment() {
    }

    public static ProjectItemFragment newInstance() {
        return new ProjectItemFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setPresenter(Contract.ProjectItemPresenter presenter) {
        mPresenter=presenter;
    }
}
