package com.lzq.wanandroid.Api;

import android.util.Log;

import com.google.gson.Gson;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.WanAndroid;
import com.lzq.wanandroid.Model.WanAndroid_Content;
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
    public void execute(final LoadTasksCallBack callBack, final int... params) {
        switch (params[0]) {
            case StringUtils.TYPE_TREE_NAVI:
                OkGo.<String>get(StringUtils.URL+StringUtils.NAVI)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result=response.body();
                                Gson gson=new Gson();
                                WanAndroid wanAndroid=gson.fromJson(result,WanAndroid.class);
                                if (wanAndroid.getErrorCode()==0){
                                    callBack.onSuccess(wanAndroid.getData(),StringUtils.TYPE_TREE_NAVI);
                                }
                            }
                        });
                break;
            case StringUtils.TYPE_TREE_KNOW:
                OkGo.<String>get(StringUtils.URL+StringUtils.TREE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result=response.body();
                                Gson gson=new Gson();
                                WanAndroid wanAndroid=gson.fromJson(result,WanAndroid.class);
                                if (wanAndroid.getErrorCode()==0){
                                    callBack.onSuccess(wanAndroid.getData(),StringUtils.TYPE_TREE_KNOW);
                                }
                            }
                        });
                break;
            case StringUtils.TYPE_HOT_KEY:
                OkGo.<String>get(StringUtils.URL + StringUtils.HOT_KEY)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid wanAndroid = gson.fromJson(result, WanAndroid.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    callBack.onSuccess(wanAndroid.getData(), StringUtils.TYPE_HOT_KEY);
                                }
                            }
                        });
                break;
            case StringUtils.TYPE_COLLECT_YES:
                //收藏
                OkGo.<String>post(StringUtils.URL + StringUtils.COLLECT_ARTICCLE + params[1] + "/json")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    callBack.onSuccess(params[2], StringUtils.TYPE_COLLECT_YES);
                                }

                            }
                        });
                break;
            case StringUtils.TYPE_COLLECT_NO:
                //取消收藏
                OkGo.<String>post(StringUtils.URL + StringUtils.CANCEL_COLLECT + params[1] + "/json")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    callBack.onSuccess(params[2], StringUtils.TYPE_COLLECT_NO);
                                }
                            }
                        });
                break;
            case StringUtils.TYPE_COLLECT_CONTENT_LOAD:
                OkGo.<String>get(StringUtils.URL + StringUtils.COLLECT_LIST + params[1] + "/json")
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
                OkGo.<String>get(StringUtils.URL + StringUtils.OFFICIAL_CONTENT + params[1] + "/" + params[2] + "/json")
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
                OkGo.<String>get(StringUtils.URL + StringUtils.OFFICIAL_CONTENT + params[1] + "/" + params[2] + "/json")
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
                                    Log.d(TAG, "----onSuccess 公众号: ");
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
            case StringUtils.TYPE_COLLECT_NO_USER:
                OkGo.<String>post(StringUtils.URL + StringUtils.CANCEL_COLLECT_USER + params[1] + "/json")
                        .params("originId", params[3])
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    callBack.onSuccess(params[2], StringUtils.TYPE_COLLECT_NO);
                                }
                            }
                        });
                break;
            case StringUtils.TYPE_ACCOUNT_TITLE:
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
                break;
        }
    }

    @Override
    public void execute(final LoadTasksCallBack callBack, String... infos) {
        switch (infos[0]) {
            case StringUtils.TYPE_LOGIN:
                OkGo.<String>post(StringUtils.URL + StringUtils.USER_LOGIN)
                        .params("username", infos[1])
                        .params("password", infos[2])
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                switch (wanAndroid.getErrorCode()) {
                                    case 0:
                                        callBack.onSuccess(wanAndroid.getData(), 0);
                                        break;
                                    case -1:
                                        callBack.onError(wanAndroid.getErrorCode(), wanAndroid.getErrorMsg());
                                        break;
                                    default:
                                        callBack.onFailed();
                                        break;
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                callBack.onFailed();
                            }
                        });
                break;
            case StringUtils.TYPE_REGISTER:
                OkGo.<String>post(StringUtils.URL + StringUtils.USER_REGISTER)
                        .params("username", infos[1])
                        .params("password", infos[2])
                        .params("repassword", infos[2])
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                switch (wanAndroid.getErrorCode()) {
                                    case 0:
                                        callBack.onSuccess(wanAndroid.getData(), 0);
                                        break;
                                    case -1:
                                        callBack.onError(wanAndroid.getErrorCode(), wanAndroid.getErrorMsg());
                                        break;
                                    default:
                                        callBack.onFailed();
                                        break;
                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                callBack.onFailed();
                            }
                        });
                break;
            case StringUtils.TYPE_TOP_ARTICLE:
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
            case StringUtils.TYPE_IMG_BANNER:
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

