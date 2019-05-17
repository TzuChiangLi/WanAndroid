package com.lzq.wanandroid.Net;

import android.util.Log;

import com.google.gson.Gson;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.WanAndroid;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class WebTask implements NetTask<Data> {
    private static final String TAG = "WebTask";
    private static WebTask INSTANCE = null;

    //构造方法
    public WebTask() {
    }

    //单例模式
    public static WebTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WebTask();
        }
        return INSTANCE;
    }



    @Override
    public void execute(LoadTasksCallBack callBack, int... params) {

    }

    @Override
    public void execute(LoadTasksCallBack callBack, String... infos) {

    }

}
