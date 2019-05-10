package com.lzq.wanandroid.Contract;

import com.lzq.wanandroid.BaseView;
import com.lzq.wanandroid.Model.Data;

import java.util.List;

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
