package com.lzq.wanandroid;

public interface LoadTasksCallBack<T> {
    void onSuccess(T t,int flag);
    void onStart();
    void onFailed();
    void onError(int code,String msg);
    void onFinish();
}
