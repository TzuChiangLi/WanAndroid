package com.lzq.wanandroid.Net;

import com.lzq.wanandroid.LoadTasksCallBack;

public interface NetTask<T> {
    void execute(LoadTasksCallBack callBack, int... params);

    void execute(LoadTasksCallBack callBack,String...infos);

}
