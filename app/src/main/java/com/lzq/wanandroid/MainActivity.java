package com.lzq.wanandroid;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bottom.NavigationController;
import com.bottom.PageNavigationView;
import com.bottom.item.BaseTabItem;
import com.bottom.listener.OnTabItemSelectedListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Net.HomeTask;
import com.lzq.wanandroid.Net.LoginTask;
import com.lzq.wanandroid.Presenter.HomePresenter;
import com.lzq.wanandroid.Presenter.LoginPresenter;
import com.lzq.wanandroid.Utils.ActivityUtils;
import com.lzq.wanandroid.Utils.AnimationUtil;
import com.lzq.wanandroid.View.Adapter.FragmentAdapter;
import com.lzq.wanandroid.View.Animation.TitleAnim;
import com.lzq.wanandroid.View.Custom.OnlyIconView;
import com.lzq.wanandroid.View.Fragment.HomeFragment;
import com.lzq.wanandroid.View.Fragment.ThreeFragment;
import com.lzq.wanandroid.View.Fragment.UserFragment;
import com.lzq.wanandroid.View.SettingsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.main_viewpager)
    ViewPager mVPager;
    @BindView(R.id.main_bottom_bar)
    PageNavigationView mBottomBar;
    @BindView(R.id.main_tv_title)
    TextView mTitleTv;
    @BindView(R.id.main_imgbtn_top_func)
    ImageButton mFuncImgBtn;
    private List<Fragment> mList = new ArrayList<>();
    private HomeFragment mHomeFragment;
    private HomePresenter mHomePresenter;
    private UserFragment mUserFragment;
    private Fragment mCurrentFragment;
    private LoginPresenter mUserPresenter;
    private FragmentTransaction mTransaction;
    private int oldHeight, currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Log.d(TAG, "----onCreate: ");
        initView();
        initOnListener();
    }

    private void initOnListener() {
        mTitleTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDifference = AnimationUtil.getInputHeight(MainActivity.this);
                if (oldHeight != heightDifference) {
                    oldHeight = heightDifference;
                    boolean isExistBottomBar = AnimationUtil.checkDeviceHasNavigationBar(MainActivity.this);
                    if (isExistBottomBar) {
                        //存在底部导航栏
                        heightDifference = heightDifference - AnimationUtil.getBottomBarHeight(MainActivity.this);
                        Log.d(TAG, "----onGlobalLayout: " + heightDifference);
                    }
                    if (heightDifference > 0) {
                        //隐藏
                        mBottomBar.setVisibility(View.GONE);
                    } else {
                        //显示
                        mBottomBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void initView() {
        ImmersionBar.with(this).statusBarColor(R.color.bg_daily_mode).autoDarkModeEnable(true).fitsSystemWindows(true).keyboardEnable(true).init();
        if (mHomeFragment == null) {
            Log.d(TAG, "----HomeFragment: null");
            mHomeFragment = HomeFragment.newInstance();
            mList.add(mHomeFragment);
        }
//          ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mHomeFragment, R.id.main_viewpager);
        HomeTask homeTask = HomeTask.getInstance();
        mHomePresenter = new HomePresenter(mHomeFragment, homeTask);
        mHomeFragment.setPresenter(mHomePresenter);


        mList.add(new ThreeFragment());
        mList.add(new ThreeFragment());

        if (mUserFragment == null) {
            Log.d(TAG, "----UserFragment: null");
            mUserFragment = UserFragment.newInstance();
            mList.add(mUserFragment);
//          ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mUserFragment, R.id.main_viewpager);
        }
        LoginTask loginTask = LoginTask.getInstance();
        mUserPresenter = new LoginPresenter(mUserFragment, loginTask);
        mUserFragment.setPresenter(mUserPresenter);

        PageNavigationView.CustomBuilder custom = mBottomBar.custom();
        NavigationController build = custom
                .addItem(newItem(R.mipmap.home_no, R.mipmap.home))
                .addItem(newItem(R.mipmap.tree_no, R.mipmap.tree))
                .addItem(newItem(R.mipmap.chat_no, R.mipmap.chat))
                .addItem(newItem(R.mipmap.user_no, R.mipmap.user))
                .build();
        //允许4个
        mVPager.setOffscreenPageLimit(2);
        FragmentAdapter mFAdapter=new FragmentAdapter(getSupportFragmentManager(), mList);
        Log.d(TAG, "----initView: "+mFAdapter.getCount());
        mVPager.setAdapter(mFAdapter);
        //自动适配ViewPager页面切换
        build.setupWithViewPager(mVPager);
        build.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                TitleAnim.hide(mTitleTv, mFuncImgBtn);
                switch (index) {
                    case 0:
                        TitleAnim.show(mTitleTv, mFuncImgBtn, "首页", 0);
                        break;
                    case 1:
                        TitleAnim.show(mTitleTv, mFuncImgBtn, "体系", 1);
                        break;
                    case 2:
                        TitleAnim.show(mTitleTv, mFuncImgBtn, "反馈", 2);
                        break;
                    case 3:
                        TitleAnim.show(mTitleTv, mFuncImgBtn, "我", 3);
                        mUserFragment.doAnimation();
                        break;
                }
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable) {
        OnlyIconView onlyIconItemView = new OnlyIconView(this);
        onlyIconItemView.initialize(drawable, checkedDrawable);
        return onlyIconItemView;
    }

    @OnClick(R.id.main_imgbtn_top_func)
    public void doFunction() {
        switch (mVPager.getCurrentItem()) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                startActivity(SettingsActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_MAIN) {
            if (event.type == Event.TYPE_CHANGE_DAY_NIGHT_MODE) {
                recreate();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
