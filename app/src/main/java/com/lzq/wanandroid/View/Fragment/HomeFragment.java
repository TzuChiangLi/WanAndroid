package com.lzq.wanandroid.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Base.BaseFragment;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.Datas;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Presenter.HomePresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.GlideImageLoader;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.View.Adapter.ArticleAdapter;
import com.lzq.wanandroid.View.Adapter.ContentAdapter;
import com.lzq.wanandroid.View.Animation.CollectAnim;
import com.lzq.wanandroid.View.ArticlesActivity;
import com.lzq.wanandroid.View.WebActivity;
import com.ms.banner.Banner;
import com.ms.banner.listener.OnBannerClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements Contract.HomeView {
    private static final String TAG = "HomeFragment";
    @BindView(R.id.main_banner)
    Banner mBanner;
    @BindView(R.id.main_rv_top_article)
    RecyclerView mTopArtRv;
    @BindView(R.id.main_rv_more_article)
    RecyclerView mMoreRv;
    @BindView(R.id.main_top_btn_account)
    Button mAccountBtn;
    @BindView(R.id.refresh_home)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.main_top_btn_navi)
    Button mNaviBtn;
    @BindView(R.id.main_appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.main_btn_top_more)
    TextView mMoreTv;
    @BindView(R.id.main_btn_more_top)
    TextView mBackTopTv;
    private Contract.HomePresenter mPresenter;
    private ArticleAdapter mTopArticleAdapter;
    private ContentAdapter mMoreAdapter;
    private View mView;
    private List<Data> mTopArticleList = new ArrayList<>();
    private List<Datas> mMoreList = new ArrayList<>();
    private List<Data> mBannerList = new ArrayList<>();
    private int page = 1;

    //创建
    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        mView = view;
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mBanner.requestDisallowInterceptTouchEvent(true);
        if (mPresenter == null) {
            mPresenter = HomePresenter.createPresenter(this, WebTask.getInstance());
        }
        mPresenter.getHomeTopImgBanner();
        mPresenter.initView();
        mPresenter.getHomeTopArticle();
        mPresenter.getMoreArticle(StringUtils.TYPE_HOME_MORE_ARTICLE_LOAD, page);

        mTopArtRv.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        mMoreRv.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        mRefreshView.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        mRefreshView.setReboundDuration(300);//回弹动画时长（毫秒）
        mRefreshView.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshView.setEnableLoadMore(true);
        mBanner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                mPresenter.getSelectedURL(mBannerList.get(position).getUrl());
            }
        });

        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getHomeTopArticle();
                mPresenter.getHomeTopImgBanner();
                mPresenter.getMoreArticle(StringUtils.TYPE_HOME_MORE_ARTICLE_LOAD, page);
                if (onFinishLoad()) {
                    mRefreshView.finishRefresh();
                }
            }
        });
        mRefreshView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page = page + 1;
                mPresenter.getMoreArticle(StringUtils.TYPE_HOME_MORE_ARTICLE_ADD, page);
                if (onFinishLoad()) {
                    mRefreshView.finishLoadMore();
                }
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.main_top_btn_articles)
    public void showAllHomeArticles() {
        mAppBarLayout.setExpanded(false);
    }

    @OnClick(R.id.main_top_btn_project)
    public void goProjectFragment() {
        Event event = new Event();
        event.target = Event.TARGET_MAIN;
        event.type = Event.TYPE_CHANGE_PROJECT;
        EventBus.getDefault().post(event);
    }

    @OnClick(R.id.main_top_btn_account)
    public void goAccountActivity() {
        Intent intent = new Intent(ActivityUtils.getActivityByView(mView), ArticlesActivity.class);
        intent.putExtra("TITLE_BAR", "公众号");
        intent.putExtra("TYPE", StringUtils.TYPE_ACCOUNT_TITLE);
        startActivity(intent);
        ActivityUtils.getActivityByView(mView).overridePendingTransition(R.anim.enter_fade_out, R.anim.enter_fade_in);
    }

    @OnClick(R.id.main_top_btn_navi)
    public void goSystemFragment() {
        Event event = new Event();
        event.target = Event.TARGET_MAIN;
        event.type = Event.TYPE_CHANGE_SYS;
        EventBus.getDefault().post(event);
    }

    @OnClick(R.id.main_btn_top_more)
    public void moreArticles() {
        mAppBarLayout.setExpanded(false);
    }

    @OnClick(R.id.main_btn_more_top)
    public void backTop() {
        mAppBarLayout.setExpanded(true);
    }


    @Override
    public void setEmptyTopArticle(List<Data> mList) {
        mTopArticleAdapter = new ArticleAdapter(mView, StringUtils.RV_ITEM_NORMAL, R.layout.rv_article_normal, mList);
        mTopArtRv.setNestedScrollingEnabled(false);
        mTopArtRv.setAdapter(mTopArticleAdapter);
    }

    @Override
    public void setMoreArticle(int flag, final List<Datas> data) {
        switch (flag) {
            case StringUtils.TYPE_HOME_MORE_ARTICLE_LOAD:
                mMoreList.clear();
                mMoreList = data;
                mMoreAdapter = new ContentAdapter(mView, R.layout.rv_article_normal, mMoreList);
                mMoreRv.setAdapter(mMoreAdapter);
                break;
            case StringUtils.TYPE_HOME_MORE_ARTICLE_ADD:
                mMoreAdapter.addData(data);
                break;
        }
        mMoreAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.getSelectedURL(mMoreAdapter.getData().get(position).getLink());
            }
        });
        mMoreAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rv_article_imgbtn_save:
                        CollectAnim.getInstance().show(view);
                        mPresenter.collectArticle(StringUtils.TYPE_HOME_MORE_COLLECT,mMoreAdapter.getData().get(position).getId(), mMoreAdapter.getData().get(position).isCollect(), position);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void setHomeTopArticle(final List<Data> mList) {
        mTopArticleList.clear();
        mTopArticleList = mList;
        mTopArticleAdapter = new ArticleAdapter(mView, StringUtils.RV_ITEM_NORMAL, R.layout.rv_article_normal, mList);
        mTopArtRv.setNestedScrollingEnabled(true);
        mTopArtRv.setAdapter(mTopArticleAdapter);
        mTopArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.getSelectedURL(mList.get(position).getLink());
            }
        });
        mTopArticleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rv_article_imgbtn_save:
                        CollectAnim.getInstance().show(view);
                        mPresenter.collectArticle(StringUtils.TYPE_HOME_TOP_COLLECT,mList.get(position).getId(), mTopArticleList.get(position).isCollect(), position);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void setHomeTopImgBanner(final List<Data> mBannerList) {
        this.mBannerList = mBannerList;
        mBanner.setPages(mBannerList, new GlideImageLoader())
                .setAutoPlay(true)
                .setCurrentPage(0)
                .setDelayTime(3000).start();
    }

    @Override
    public void goWebActivity(String URL) {
        Intent intent = new Intent(ActivityUtils.getActivityByView(mView), WebActivity.class);
        intent.putExtra("URL", URL);
        startActivity(mView, intent);
    }


    @Override
    public boolean onFinishLoad() {
        return true;
    }

    @Override
    public void collectedTopArticle(int position, boolean isCollect) {
        mTopArticleList.get(position).setCollect(isCollect);
        mTopArticleAdapter.notifyItemChanged(position);
        ToastUtils.show(isCollect ? "收藏成功" : "取消收藏");
    }

    @Override
    public void collectedMoreArticle(int position, boolean isCollect) {
        mMoreAdapter.getData().get(position).setCollect(isCollect);
        mMoreAdapter.notifyItemChanged(position);
        ToastUtils.show(isCollect ? "收藏成功" : "取消收藏");
    }


    @Override
    public void setPresenter(Contract.HomePresenter mPresenter) {
        if (mPresenter == null)
            this.mPresenter = mPresenter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_HOME) {
            switch (event.type) {
                case Event.TYPE_HOME_BACKTOTOP:
                    mAppBarLayout.setExpanded(true);
                    break;
            }
        }
        if (event.target == Event.TARGET_RESFRESH) {
            mRefreshView.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
