package com.lzq.wanandroid.Api;


import com.lzq.wanandroid.Model.Children;

import java.util.List;

public interface FlowTagCallBack {
    void getTreeLink(String URL);
    void getTreeArticles(int ID, int position, String title, List<Children> children);
}
