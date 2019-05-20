package com.lzq.wanandroid.View.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzq.wanandroid.BaseFragment;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Presenter.SystemPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.View.Adapter.FragmentAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemFragment extends BaseFragment implements Contract.SystemView {
    private static final String TAG = "SystemFragment";
    @BindView(R.id.system_tab)
    TabLayout mTabLayout;
    @BindView(R.id.system_viewpager)
    ViewPager mViewPager;
    private Contract.SystemPresenter mPresenter;
    private List<Fragment> mList = new ArrayList<>();


    public SystemFragment() {
    }

    public static SystemFragment newInstance() {
        return new SystemFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        if (mPresenter == null) {
            mPresenter = SystemPresenter.createPresenter(this);
        }
        mPresenter.initTabView();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Event event = new Event();
                event.target = Event.TARGET_MAIN;
                event.type = Event.TYPE_CHANGE_MAIN_TITLE;
                event.data = tab.getText().toString();
                EventBus.getDefault().post(event);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }


    @Override
    public void setTabView(String[] tabName, int[] imgTab) {
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.tab_divider));
        linearLayout.setDividerPadding(48);
        mList.add(new NaviFragment());
        mList.add(new TreeFragment());

        for (int i = 0; i < tabName.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mTabLayout.setupWithViewPager(mViewPager);
        //允许4个
        mViewPager.setAdapter(new FragmentAdapter(this.getChildFragmentManager(), mList));
        for (int i = 0; i < tabName.length; i++) {
            mTabLayout.getTabAt(i).setText(tabName[i]).setIcon(imgTab[i]);
        }
    }

    @Override
    public void setPresenter(Contract.SystemPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_SYSTEM) {
            switch (event.type) {
                case Event.TYPE_CHANGE_MAIN_TITLE:
                    event.target = Event.TARGET_MAIN;
                    event.type = Event.TYPE_CHANGE_MAIN_TITLE;
                    event.data = mTabLayout.getTabAt(mTabLayout.getSelectedTabPosition()).getText().toString();
                    EventBus.getDefault().post(event);
                    break;
            }
        }
    }
}
