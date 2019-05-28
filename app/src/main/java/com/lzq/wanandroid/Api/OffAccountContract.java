package com.lzq.wanandroid.Api;

import com.lzq.wanandroid.BaseView;
import com.lzq.wanandroid.Model.Children;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.Datas;

import java.util.List;

public interface OffAccountContract {
    //与M层交互
    interface AccountTitlePresenter {
        //获取文章前先使用占位符填充
        void initView(String[] tabName);

        void initView(int position,String[] tabName, int[] tabID);

        void getTitleText(int type);

        void getContent(int ID, int page);

    }

    //与M层交互
    interface AccountContentPresenter {
        //获取文章前先使用占位符填充
        void initView();

        void getTitleText(int type);

        void addContent(int ID, int page);

        void getContent(int ID, int page);

        void getSelectedURL(String URL);

        int showID();

        void collectArticle(int ID, boolean isCollect, int position);

    }

    //与V层交互，需要将获取的信息展示出来
    interface AccountTitleView extends BaseView<AccountTitlePresenter> {

        void setAccountEmptyTitle(List<Data> mList);

        void setArticlesContent(int position,List<Children> mList);

        void setTitleText(List<Data> mList);

        void setContent(List<Data> mList);
    }


    //与V层交互，需要将获取的信息展示出来
    interface AccountContentView extends BaseView<AccountContentPresenter> {

        void setEmptyContent(List<Datas> mList);

        void setContent(List<Datas> mList, int flag);

        void goWebActivity(String URL);

        void collectedArticle(int position, boolean isCollect);
    }

}
