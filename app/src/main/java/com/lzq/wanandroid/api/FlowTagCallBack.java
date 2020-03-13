package com.lzq.wanandroid.api;


import com.lzq.wanandroid.model.Children;

import java.util.List;

public interface FlowTagCallBack {
    void getTreeLink(String URL);
    void getTreeArticles(int ID, int position, String title, List<Children> children);
}
