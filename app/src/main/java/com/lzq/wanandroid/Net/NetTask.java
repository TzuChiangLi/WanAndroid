package com.lzq.wanandroid.Net;

import com.lzq.wanandroid.LoadTasksCallBack;

public interface NetTask<T> {
    void execute(int ID, int page, int flag, LoadTasksCallBack callBack);

    void execute(String data1,String data2,LoadTasksCallBack callBack);

}
