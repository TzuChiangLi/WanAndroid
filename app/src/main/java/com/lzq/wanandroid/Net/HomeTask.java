package com.lzq.wanandroid.Net;

import android.util.Log;

import com.google.gson.Gson;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.WanAndroid;
import com.lzq.wanandroid.Model.WanAndroid_Content;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class HomeTask implements NetTask<Data> {
    private static final String TAG = "HomeTask";
    private static HomeTask INSTANCE = null;

    //构造方法
    public HomeTask() {
    }

    //单例模式
    public static HomeTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HomeTask();
        }
        return INSTANCE;
    }

    @Override
    public void execute(int ID, final int position, final int type, final LoadTasksCallBack callBack) {
        switch (type){
            case StringUtils.TYPE_COLLECT_YES:
                //收藏
                OkGo.<String>post(StringUtils.URL+StringUtils.COLLECT_ARTICCLE+ID+"/json")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson=new Gson();
                                WanAndroid_Content wanAndroid=gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid.getErrorCode()==0){
                                    callBack.onSuccess(position,StringUtils.TYPE_COLLECT_YES);
                                }

                            }
                        });
                break;
            case StringUtils.TYPE_COLLECT_NO:
                //取消收藏
                OkGo.<String>post(StringUtils.URL+StringUtils.CANCEL_COLLECT+ID+"/json")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson=new Gson();
                                WanAndroid_Content wanAndroid=gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid.getErrorCode()==0){
                                    callBack.onSuccess(position,StringUtils.TYPE_COLLECT_NO);
                                }
                            }
                        });
        }

    }

    @Override
    public void execute(String data1, String data2, final LoadTasksCallBack callBack) {
        switch (data2) {
            case "1":
                OkGo.<String>get(StringUtils.URL + StringUtils.HOME_TOP_ARTICLE)
                        .retryCount(5)
                        .execute(new StringCallback() {
                            @Override
                            public void onStart(Request<String, ? extends Request> request) {
                                super.onStart(request);
                                callBack.onStart();
                            }

                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid wanAndroid = gson.fromJson(result, WanAndroid.class);
                                int code = wanAndroid.getErrorCode();
                                if (code == 0) {
                                    callBack.onSuccess(wanAndroid.getData(), StringUtils.TYPE_HOME_TOP_ARTICLE);
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
                break;
            case "2":
                //题头
                OkGo.<String>get(StringUtils.URL + StringUtils.HOME_IMG_BANNER)
                        .retryCount(5)
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
                                    callBack.onSuccess(wanAndroid.getData(), StringUtils.TYPE_HOME_IMG_BANNER);
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
                break;
            default:
                break;
        }




    }


}
