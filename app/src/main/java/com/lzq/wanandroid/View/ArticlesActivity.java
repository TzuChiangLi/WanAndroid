package com.lzq.wanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.lzq.wanandroid.Api.OffAccountContract;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Base.BaseActivity;
import com.lzq.wanandroid.Model.Children;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Presenter.ArticlesPresenter;
import com.lzq.wanandroid.Presenter.TitlePresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.ActivityUtils;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.View.Adapter.FragmentAdapter;
import com.lzq.wanandroid.View.Fragment.ContentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesActivity extends BaseActivity implements OffAccountContract.AccountTitleView {
    private static final String TAG = "ArticlesActivity";
    @BindView(R.id.official_account_topbar)
    TitleBar mTitleBar;
    @BindView(R.id.official_account_tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.official_account_viewpager)
    ViewPager mVPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<Data> mList = new ArrayList<>();
    private String[] title = {"郭霖", "玉刚说", "刘望舒", "鸿洋"}, childName;
    private int[] childID;
    private TitlePresenter mPresenter;
    private OffAccountContract.AccountTitlePresenter mAccountPresenter;
    private ContentFragment mFragment;
    private Intent intent;
    private int ID, position;
    private WebTask mTask = WebTask.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        ButterKnife.bind(this);
        intent = getIntent();
        initView();
        initDataList();
        initOnClickListener();
    }

    private void initDataList() {
        WebTask mTask = WebTask.getInstance();
        mPresenter = new TitlePresenter(ArticlesActivity.this, mTask);
        ArticlesActivity.this.setPresenter(mPresenter);
        if (intent.getIntExtra("TYPE", StringUtils.TYPE_ACCOUNT_TITLE) == StringUtils.TYPE_TREE_KNOW_ARTICLES) {
            ID = intent.getIntExtra("ID", 0);//CID
            childName = intent.getStringArrayExtra("TABNAME");
            childID = intent.getIntArrayExtra("TABID");
            position = intent.getIntExtra("POSITION", 0);
            mPresenter.initView(position, childName, childID);
        } else {
            mPresenter.initView(title);
            mPresenter.getTitleText(intent.getIntExtra("TYPE", StringUtils.TYPE_ACCOUNT_TITLE));
        }
    }

    private void initView() {
        mTitleBar.setTitle(intent.getStringExtra("TITLE_BAR"));
        ImmersionBar.with(this).statusBarColor(R.color.bg_daily_mode).autoDarkModeEnable(true).fitsSystemWindows(true).keyboardEnable(true).init();
    }


    private void initOnClickListener() {
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
                overridePendingTransition(R.anim.exit_fade_out, R.anim.exit_fade_in);
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });

    }


    @Override
    public void setAccountEmptyTitle(List<Data> mList) {
        for (int i = 0; i < title.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab());
            fragments.add(new ContentFragment());
            mTabLayout.getTabAt(i).setText(title[i]);
        }
    }

    @Override
    public void setArticlesContent(int currentPositon, List<Children> mList) {
        if (mFragment == null) {
            mFragment = ContentFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.official_account_viewpager);
        }
        ArticlesPresenter mContentPresenter = null;
        for (int i = 0; i < mList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
            fragments.add(ContentFragment.newInstance());
            mFragment = (ContentFragment) fragments.get(i);
            mContentPresenter = new ArticlesPresenter(mFragment, mTask, mList.get(i).getId());
            mFragment.setPresenter(mContentPresenter);
        }

        mTabLayout.setupWithViewPager(mVPager);
        mVPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        mVPager.setCurrentItem(currentPositon);
        mVPager.setOffscreenPageLimit(2);
        for (int i = 0; i < mList.size(); i++) {
            mTabLayout.getTabAt(i).setText(mList.get(i).getName());
        }
    }

    @Override
    public void setTitleText(List<Data> mList) {
        this.mList = mList;
        //在使用TabLayout和Viewpager时，要先循环添加Fragment和创建TabLayout
        //然后绑定，最后再给TabLayout的标签页的名称赋值
        if (mList != null || mList.size() != 0) {
            mTabLayout.removeAllTabs();
            fragments.clear();
            if (mFragment == null) {
                mFragment = ContentFragment.newInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.official_account_viewpager);
            }
            ArticlesPresenter mContentPresenter = null;
            for (int i = 0; i < mList.size(); i++) {
                mTabLayout.addTab(mTabLayout.newTab());
                fragments.add(ContentFragment.newInstance());
                mFragment = (ContentFragment) fragments.get(i);
                mContentPresenter = new ArticlesPresenter(mFragment, mTask, mList.get(i).getId());
                mFragment.setPresenter(mContentPresenter);
            }

            mTabLayout.setupWithViewPager(mVPager);
            mVPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
            mVPager.setCurrentItem(0);
            mVPager.setOffscreenPageLimit(4);
            for (int i = 0; i < mList.size(); i++) {
                mTabLayout.getTabAt(i).setText(mList.get(i).getName());
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.exit_fade_out, R.anim.exit_fade_in);
    }


    @Override
    public void setContent(List mList) {

    }

    @Override
    public void setPresenter(OffAccountContract.AccountTitlePresenter mAccountPresenter) {
        this.mAccountPresenter = mAccountPresenter;
    }

}
