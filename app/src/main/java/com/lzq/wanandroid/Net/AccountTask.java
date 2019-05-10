package com.lzq.wanandroid.Net;

import android.util.Log;

import com.google.gson.Gson;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.WanAndroid;
import com.lzq.wanandroid.Model.WanAndroid_Content;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class AccountTask implements NetTask<Data> {
    private static final String TAG = "AccountTask";
    private static AccountTask INSTANCE = null;

    //构造方法
    public AccountTask() {
    }

    //单例模式
    public static AccountTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AccountTask();
        }
        return INSTANCE;
    }


    @Override
    public void execute(int ID, int page, final int flag, final LoadTasksCallBack callBack) {
        OkGo.<String>get(StringUtils.URL + StringUtils.OFFICIAL_CONTENT + ID + "/" + page + "/json")
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        callBack.onStart();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        Log.d(TAG, "----onSuccess: " + result);
                        Gson gson = new Gson();
                        WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                        int code = wanAndroid.getErrorCode();
                        if (code == 0) {
                            if (flag == StringUtils.TYPE_ACCOUNT_CONTENT_LOAD) {
                                callBack.onSuccess(wanAndroid.getData().getDatas(), StringUtils.TYPE_ACCOUNT_CONTENT_LOAD);
                            } else {
                                callBack.onSuccess(wanAndroid.getData().getDatas(), StringUtils.TYPE_ACCOUNT_CONTENT_ADD);
                            }
                        } else {
                            callBack.onFailed();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBack.onFailed();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        callBack.onFinish();
                    }
                });
    }



    @Override
    public void execute(String data1,String data2,final LoadTasksCallBack callBack) {
        OkGo.<String>get(StringUtils.URL + StringUtils.OFFICIAL_ACCOUNT)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        callBack.onStart();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body();
                        Log.d(TAG, "----onSuccess: " + result);
                        Gson gson = new Gson();
                        WanAndroid wanAndroid = gson.fromJson(result, WanAndroid.class);
                        int code = wanAndroid.getErrorCode();
                        if (code == 0) {
                            callBack.onSuccess(wanAndroid.getData(), StringUtils.TYPE_ACCOUNT_TITLE);
                        } else {
                            callBack.onFailed();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBack.onFailed();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        callBack.onFinish();
                    }
                });
    }
}
