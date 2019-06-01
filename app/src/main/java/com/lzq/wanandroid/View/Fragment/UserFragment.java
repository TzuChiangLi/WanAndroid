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
import android.widget.TextView;

import com.lzq.wanandroid.Base.BaseFragment;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Presenter.CollectPresenter;
import com.lzq.wanandroid.Presenter.UserPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.View.Adapter.FragmentAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFragment extends BaseFragment implements Contract.UserView {
    private static final String TAG = "UserFragment";
    @BindView(R.id.user_tv_name)
    TextView mUserNameTv;
    @BindView(R.id.user_tv_id)
    TextView mIDTv;
    @BindView(R.id.user_tab)
    TabLayout mTabLayout;
    @BindView(R.id.user_viewpager)
    ViewPager mViewPager;
    private List<Fragment> mList = new ArrayList<>();
    private Contract.UserPresenter mPresenter;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    public UserFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        if (mPresenter == null) {
            mPresenter = UserPresenter.createPresenter(this);
        }
        mPresenter.initView();
        mPresenter.initTabView();
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_USER) {
            if (event.type == Event.TYPE_LOGIN_SUCCESS) {
                mPresenter.initView();
            }
            if (event.type == Event.TYPE_LOGOUT_SUCCESS) {
                mPresenter.initView();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUserInfo(String id, String username) {
        mIDTv.setText(id);
        mUserNameTv.setText(username);
    }

    @Override
    public void setTabView(String[] tabName, int[] imgTab) {
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.tab_divider));
        linearLayout.setDividerPadding(48);
        WebTask mTask = WebTask.getInstance();
        CollectPresenter mCollectPresenter;
        CollectFragment mFragment;

        mList.add(new CollectFragment());
        mList.add(new ToDoFragment());
        for (int i = 0; i < tabName.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mFragment = (CollectFragment) mList.get(0);
        mCollectPresenter = new CollectPresenter(mFragment, mTask);
        mFragment.setPresenter(mCollectPresenter);
        mTabLayout.setupWithViewPager(mViewPager);
        //允许4个
        mViewPager.setAdapter(new FragmentAdapter(this.getChildFragmentManager(), mList));
        //自动适配ViewPager页面切换
        for (int i = 0; i < tabName.length; i++) {
            mTabLayout.getTabAt(i).setText(tabName[i]).setIcon(imgTab[i]);
        }
    }

    @Override
    public void setPresenter(Contract.UserPresenter presenter) {
        mPresenter = presenter;
    }
}
