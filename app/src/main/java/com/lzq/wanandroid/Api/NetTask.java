package com.lzq.wanandroid.api;

public interface NetTask<T> {
    void execute(LoadTasksCallBack callBack, int... params);

    void execute(LoadTasksCallBack callBack,String...infos);

}
