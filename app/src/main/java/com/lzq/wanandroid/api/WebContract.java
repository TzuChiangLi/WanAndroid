package com.lzq.wanandroid.Api;

import com.lzq.wanandroid.Base.BaseView;

public interface WebContract {

    interface Presenter{
        //与M层交互
        void getContent(String URL);
    }


    interface View extends BaseView<Presenter>{
        //与V层交互，需要将获取的信息展示出来
        void setContent(String URL);
    }


}
