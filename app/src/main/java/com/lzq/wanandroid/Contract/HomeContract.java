package com.lzq.wanandroid.Contract;

import com.lzq.wanandroid.BaseView;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.Datas;

import java.util.List;

public interface HomeContract {

    interface Presenter{
        //与M层交互
        //获取文章前先使用占位符填充
        void initView();
        //获取玩安卓置顶文章
        void getHomeTopArticle();
        //获取玩安卓顶部图片
        void getHomeTopImgBanner();
        void getSelectedURL(String URL);
    }


    interface View extends BaseView<Presenter>{
        //与V层交互，需要将获取的信息展示出来
        void setEmptyTopArticle(List<Data> mList);
        void setHomeTopArticle(List<Data> mList);
        void setHomeTopImgBanner(List<Data> mList);
        void goWebActivity(String URL);
        boolean onFinishLoad();
    }


}
