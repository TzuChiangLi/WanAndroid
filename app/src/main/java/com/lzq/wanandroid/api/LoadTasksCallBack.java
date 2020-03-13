package com.lzq.wanandroid.api;

public interface LoadTasksCallBack<T> {
    void onSuccess(T t,int...params);
    void onStart();
    void onFailed();
    void onError(int code,String msg);
    void onFinish();
}
