package com.lzq.wanandroid.Contract;

public interface NetTask<T> {
    void execute(LoadTasksCallBack callBack, int... params);

    void execute(LoadTasksCallBack callBack,String...infos);

}
