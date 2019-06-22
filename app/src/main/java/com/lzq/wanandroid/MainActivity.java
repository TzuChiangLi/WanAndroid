package com.lzq.wanandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.bottom.NavigationController;
import com.bottom.PageNavigationView;
import com.bottom.item.BaseTabItem;
import com.bottom.listener.OnTabItemSelectedListener;
import com.gyf.immersionbar.ImmersionBar;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Base.NetChangeActivity;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Presenter.HomePresenter;
import com.lzq.wanandroid.Presenter.MainPresenter;
import com.lzq.wanandroid.Presenter.ProjectPresenter;
import com.lzq.wanandroid.Presenter.SystemPresenter;
import com.lzq.wanandroid.Presenter.UserPresenter;
import com.lzq.wanandroid.Utils.AnimationUtil;
import com.lzq.wanandroid.View.Adapter.FragmentAdapter;
import com.lzq.wanandroid.View.Animation.TitleAnim;
import com.lzq.wanandroid.View.Custom.OnlyIconView;
import com.lzq.wanandroid.View.Fragment.HomeFragment;
import com.lzq.wanandroid.View.Fragment.ProjectFragment;
import com.lzq.wanandroid.View.Fragment.SystemFragment;
import com.lzq.wanandroid.View.Fragment.UserFragment;
import com.lzq.wanandroid.View.LoginActivity;
import com.lzq.wanandroid.View.SearchActivity;
import com.lzq.wanandroid.View.SettingsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends NetChangeActivity implements Contract.MainView {
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
    private Contract.MainPresenter mPresenter;
    private HomeFragment mHomeFragment;
    private SystemFragment mSystemFragment;
    private HomePresenter mHomePresenter;
    private UserPresenter mUserPresenter;
    private SystemPresenter mSystemPresenter;
    private UserFragment mUserFragment;
    private ProjectFragment mProjectFragment;
    private ProjectPresenter mProjectPresenter;
    private FragmentAdapter mFAdapter;
    private int oldHeight;
    private long lastClickBackTime = System.currentTimeMillis() - 3000;
    private static int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
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
        WebTask mTask = WebTask.getInstance();
        if (mPresenter == null) {
            mPresenter = MainPresenter.createMainPresenter(MainActivity.this);
        }
        MainActivity.this.setPresenter(mPresenter);


        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance();
            mList.add(mHomeFragment);
        }
        mHomePresenter = new HomePresenter(mHomeFragment, mTask);
        mHomeFragment.setPresenter(mHomePresenter);


        if (mSystemFragment == null) {
            mSystemFragment = SystemFragment.newInstance();
            mList.add(mSystemFragment);
        }
        mSystemPresenter = new SystemPresenter(mSystemFragment);
        mSystemFragment.setPresenter(mSystemPresenter);

        if (mProjectFragment == null) {
            mProjectFragment = ProjectFragment.newInstance();
            mList.add(mProjectFragment);
        }
        mProjectPresenter = new ProjectPresenter(mProjectFragment, mTask);
        mProjectFragment.setPresenter(mProjectPresenter);

        if (mUserFragment == null) {
            mUserFragment = UserFragment.newInstance();
            mList.add(mUserFragment);
        }
        mUserPresenter = new UserPresenter(mUserFragment);
        mUserFragment.setPresenter(mUserPresenter);


        final PageNavigationView.CustomBuilder custom = mBottomBar.custom();
        NavigationController build = custom
                .addItem(newItem(R.mipmap.home_no, R.mipmap.home))
                .addItem(newItem(R.mipmap.tree_no, R.mipmap.tree))
                .addItem(newItem(R.mipmap.project_no, R.mipmap.project_yes))
                .addItem(newItem(R.mipmap.user_no, R.mipmap.user))
                .build();
        //允许4个
        mVPager.setOffscreenPageLimit(4);
        mFAdapter = new FragmentAdapter(getSupportFragmentManager(), mList);
        mVPager.setAdapter(mFAdapter);
        //自动适配ViewPager页面切换
        build.setupWithViewPager(mVPager);
        build.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                TitleAnim.hide(mTitleTv, mFuncImgBtn);
                Event event = new Event();
                switch (index) {
                    case 0:
                        TitleAnim.show(mTitleTv, mFuncImgBtn, "首页", 0);
                        break;
                    case 1:
                        event.target = Event.TARGET_SYSTEM;
                        event.type = Event.TYPE_CHANGE_MAIN_TITLE;
                        EventBus.getDefault().post(event);
                        break;
                    case 2:
                        TitleAnim.show(mTitleTv, mFuncImgBtn, "项目", 2);
                        break;
                    case 3:
                        TitleAnim.show(mTitleTv, mFuncImgBtn, "我", 3);
                        if (SPUtils.getInstance("userinfo").getBoolean("isLogin")) {
                            event.target = Event.TARGET_COLLECT;
                            event.type = Event.TYPE_COLLECT_REFRESH;
                            EventBus.getDefault().post(event);
                        } else {
                            if (flag==0){
                            startActivityWithoutAnimation(LoginActivity.class);
                            flag++;
                            }
                        }
                        break;
                }
            }

            @Override
            public void onRepeat(int index) {
                if (index == 0) {
                    Event event = new Event();
                    event.target = Event.TARGET_HOME;
                    event.type = Event.TYPE_HOME_BACKTOTOP;
                    EventBus.getDefault().post(event);
                }
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
                startActivityWithoutAnimation(SearchActivity.class);
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
        Log.d(TAG, "----main target: " + event.target + "---- type:" + event.type);
        if (event.target == Event.TARGET_MAIN) {
            switch (event.type) {
                case Event.TYPE_CHANGE_DAY_NIGHT_MODE:
                    mFuncImgBtn.setVisibility(View.VISIBLE);
                    mFuncImgBtn.setAlpha(1f);
                    recreate();
                    break;
                case Event.TYPE_LOGIN_SUCCESS:
                    TitleAnim.hide(mTitleTv);
                    break;
                case Event.TYPE_NEED_LOGIN:
                    startActivityWithoutAnimation(LoginActivity.class);
                    break;
                case Event.TYPE_LOGOUT_SUCCESS:
                    mHomePresenter.getHomeTopArticle();
                    break;
                case Event.TYPE_CHANGE_MAIN_TITLE:
                    TitleAnim.hide(mTitleTv, mFuncImgBtn);
                    switch (event.position) {
                        case 0:
                            event.data = "导航";
                            break;
                        case 1:
                            event.data = "知识体系";
                            break;
                    }
                    TitleAnim.show(mTitleTv, mFuncImgBtn, event.data, 1);
                case Event.TYPE_CHANGE_SYS:
                    mVPager.setCurrentItem(1, true);
                    break;
                case Event.TYPE_CHANGE_PROJECT:
                    mVPager.setCurrentItem(2, true);
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void doNetWork() {
        Event event = new Event();
        event.target = Event.TARGET_RESFRESH;
        if (SPUtils.getInstance("userinfo").getBoolean("isLogin")) {
            event.type = Event.TYPE_REFRESH_ISLOGIN;
        } else {
            event.type = Event.TYPE_REFRESH_NOTLOGIN;
        }
        EventBus.getDefault().post(event);
    }


    @Override
    public void setPresenter(Contract.MainPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void afterCheckLogin(boolean flag) {
        if (flag != true) {
            startActivityWithoutAnimation(LoginActivity.class);
        }
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastClickBackTime > 2000) { // 后退阻断
            Toast.makeText(this, "再点一次退出", Toast.LENGTH_LONG).show();
            lastClickBackTime = System.currentTimeMillis();
        } else { // 关掉app
            System.exit(0);//完全退出  再次启动很慢
            finish();//保留进程 再次启动较快
        }
    }
}
