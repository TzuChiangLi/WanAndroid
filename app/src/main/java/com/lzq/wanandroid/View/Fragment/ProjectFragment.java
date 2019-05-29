package com.lzq.wanandroid.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.BaseFragment;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Presenter.ArticlesPresenter;
import com.lzq.wanandroid.Presenter.ProjectItemPresenter;
import com.lzq.wanandroid.Presenter.ProjectPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.ActivityUtils;
import com.lzq.wanandroid.View.Adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectFragment extends BaseFragment implements Contract.ProjectView {
    private static final String TAG = "ProjectFragment";
    @BindView(R.id.project_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.project_viewpager)
    ViewPager mViewPager;
    private Contract.ProjectPresenter mPresenter;
    private ProjectItemFragment mFragment;
    private List<Fragment> fragments = new ArrayList<>();

    public ProjectFragment() {
    }

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        ButterKnife.bind(this, view);
        if (mPresenter==null){
            ProjectPresenter.createPresenter(this, WebTask.getInstance());
        }
//        mPresenter.initTabView();
        return view;
    }

    @Override
    public void setTabView(List<Data> mList) {
        if (mFragment == null) {
            mFragment = ProjectItemFragment.newInstance();
            ActivityUtils.getInstance().addFragmentToActivity(getChildFragmentManager(), mFragment, R.id.project_viewpager);
        }
        ProjectItemPresenter mItemPresenter = null;
        for (int i = 0; i < mList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
            fragments.add(ProjectItemFragment.newInstance());
            mFragment = (ProjectItemFragment) fragments.get(i);
            mItemPresenter = new ProjectItemPresenter(mFragment,WebTask.getInstance());
            mFragment.setPresenter(mItemPresenter);
        }

        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
        for (int i = 0; i < mList.size(); i++) {
            mTabLayout.getTabAt(i).setText(mList.get(i).getName());
        }
    }

    @Override
    public void setProjectData() {

    }

    @Override
    public void setPresenter(Contract.ProjectPresenter presenter) {
        mPresenter = presenter;
    }
}
