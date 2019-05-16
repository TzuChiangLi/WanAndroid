package com.lzq.wanandroid.Net;

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

import static android.os.Build.ID;

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

    //ID----params[0],position----params[1],type----params[2]
    @Override
    public void execute(final LoadTasksCallBack callBack, final int... params) {
        int ID = params[0], type = params[2];
        final int position = params[1];
        switch (type) {
            case StringUtils.TYPE_COLLECT_CONTENT_LOAD:
                OkGo.<String>get(StringUtils.URL + StringUtils.COLLECT_LIST + ID + "/json")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                int code = wanAndroid.getErrorCode();
                                if (code == 0) {
                                    callBack.onSuccess(wanAndroid.getData().getDatas(), StringUtils.TYPE_COLLECT_CONTENT_LOAD);
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
            case StringUtils.TYPE_COLLECT_CONTENT_ADD:
                break;
            case StringUtils.TYPE_ACCOUNT_CONTENT_ADD:
                OkGo.<String>get(StringUtils.URL + StringUtils.OFFICIAL_CONTENT + ID + "/" + params[1] + "/json")
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
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                int code = wanAndroid.getErrorCode();
                                if (code == 0) {
                                    callBack.onSuccess(wanAndroid.getData().getDatas(), StringUtils.TYPE_ACCOUNT_CONTENT_ADD);
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
            case StringUtils.TYPE_ACCOUNT_CONTENT_LOAD:
                OkGo.<String>get(StringUtils.URL + StringUtils.OFFICIAL_CONTENT + ID + "/" + params[1] + "/json")
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
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                int code = wanAndroid.getErrorCode();
                                if (code == 0) {
                                    callBack.onSuccess(wanAndroid.getData().getDatas(), StringUtils.TYPE_ACCOUNT_CONTENT_LOAD);
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
            case StringUtils.TYPE_COLLECT_YES:
                //收藏
                OkGo.<String>post(StringUtils.URL + StringUtils.COLLECT_ARTICCLE + ID + "/json")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    callBack.onSuccess(params[1], StringUtils.TYPE_COLLECT_YES);
                                }

                            }
                        });
                break;
            case StringUtils.TYPE_COLLECT_NO:
                //取消收藏
                OkGo.<String>post(StringUtils.URL + StringUtils.CANCEL_COLLECT + ID + "/json")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    callBack.onSuccess(params[1], StringUtils.TYPE_COLLECT_NO);
                                }
                            }
                        });
                break;
            case StringUtils.TYPE_COLLECT_NO_USER:
                OkGo.<String>post(StringUtils.URL + StringUtils.CANCEL_COLLECT_USER + ID + "/json")
                        .params("originId", params[3])
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    callBack.onSuccess(position, StringUtils.TYPE_COLLECT_NO);
                                }
                            }
                        });
                break;
        }


    }


    @Override
    public void execute(String data1, String data2, final LoadTasksCallBack callBack) {
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
