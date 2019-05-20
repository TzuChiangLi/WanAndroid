package com.lzq.wanandroid.Contract;


import com.lzq.wanandroid.Model.Data;

public interface FlowTagCallBack {
    void getTreeLink(String URL);
    void getTreeArticles(int ID,int position,String title,String[] childName);
}
