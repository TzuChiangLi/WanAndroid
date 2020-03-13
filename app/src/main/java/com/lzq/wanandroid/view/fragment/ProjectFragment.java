package com.lzq.wanandroid.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzq.wanandroid.api.Contract;
import com.lzq.wanandroid.api.WebTask;
import com.lzq.wanandroid.base.BaseFragment;
import com.lzq.wanandroid.model.Event;
import com.lzq.wanandroid.model.ProjectTree;
import com.lzq.wanandroid.presenter.ProjectItemPresenter;
import com.lzq.wanandroid.presenter.ProjectPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.utils.ActivityUtils;
import com.lzq.wanandroid.view.adapter.FragmentAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private boolean isRefresh = false;

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
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (mPresenter == null) {
            mPresenter = ProjectPresenter.createPresenter(this, WebTask.getInstance());
        }
//        mPresenter.initView();
//        mPresenter.initTabView();
        return view;
    }

    @Override
    public void setEmptyTabView(String[] title) {
        ProjectItemPresenter mItemPresenter = null;
        for (int i = 0; i < title.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab());
            fragments.add(ProjectItemFragment.newInstance());
            mFragment = (ProjectItemFragment) fragments.get(i);
            mItemPresenter = new ProjectItemPresenter(mFragment, WebTask.getInstance(), 0);
            mFragment.setPresenter(mItemPresenter);
        }
        mViewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < title.length; i++) {
            mTabLayout.getTabAt(i).setText(title[i]);
        }
    }

    @Override
    public void setTabView(List<ProjectTree.DataBean> mList) {
        try {
            mTabLayout.removeAllTabs();
            fragments.clear();
            if (mFragment == null) {
                mFragment = ProjectItemFragment.newInstance();
                ActivityUtils.getInstance().addFragmentToActivity(getChildFragmentManager(), mFragment, R.id.project_viewpager);
            }
            ProjectItemPresenter mItemPresenter = null;
            for (int i = 0; i < mList.size(); i++) {
                mTabLayout.addTab(mTabLayout.newTab());
                fragments.add(ProjectItemFragment.newInstance());
                mFragment = (ProjectItemFragment) fragments.get(i);
                mItemPresenter = new ProjectItemPresenter(mFragment, WebTask.getInstance(), mList.get(i).getId());
                mFragment.setPresenter(mItemPresenter);
            }

            mTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragments));
            mViewPager.setCurrentItem(0);
            mViewPager.setOffscreenPageLimit(2);
            for (int i = 0; i < mList.size(); i++) {
                mTabLayout.getTabAt(i).setText(mList.get(i).getName());
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void setProjectData() {

    }

    @Override
    public void setPresenter(Contract.ProjectPresenter presenter) {
        mPresenter = presenter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_RESFRESH) {
            mPresenter.initTabView();
        }
        if (event.target == Event.TARGET_PROJECT) {
            if (event.type == Event.TYPE_PROJECT_REFRESH) {
                mPresenter.initView();
                mPresenter.initTabView();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
