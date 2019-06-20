package com.lzq.wanandroid.Api;

import android.util.Log;

import com.google.gson.Gson;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.ProjectItem;
import com.lzq.wanandroid.Model.ProjectTree;
import com.lzq.wanandroid.Model.SearchResult;
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
            case StringUtils.TYPE_HOME_MORE_ARTICLE_LOAD:
            case StringUtils.TYPE_HOME_MORE_ARTICLE_ADD:
                OkGo.<String>get(StringUtils.URL + StringUtils.HOME_MORE_ARTICLE + params[1] + "/json")
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid_content = gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid_content.getErrorCode() == 0) {
                                    callBack.onSuccess(wanAndroid_content.getData().getDatas(), params[0]);
                                }
                            }

                            @Override
                            public void onFinish() {
                                super.onFinish();
                                callBack.onFinish();
                            }
                        });
                break;
            case StringUtils.TYPE_PROJECT_ITEM_LOAD:
                //1/json?cid=294
                OkGo.<String>get(StringUtils.URL + StringUtils.PROJECT_LOAD + params[1] + "/json?cid=" + params[2])
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                ProjectItem projectItem = gson.fromJson(result, ProjectItem.class);
                                if (projectItem.getErrorCode() == 0) {
                                    callBack.onSuccess(projectItem.getData().getDatas(), params[0]);
                                }
                            }
                        });
                break;
            case StringUtils.TYPE_PROJECT_TREE:
                OkGo.<String>get(StringUtils.URL + StringUtils.PROJECT_TREE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                ProjectTree projectTree = gson.fromJson(result, ProjectTree.class);
                                if (projectTree.getErrorCode() == 0) {
                                    callBack.onSuccess(projectTree.getData(), params[0]);
                                }
                            }
                        });
                break;
            case StringUtils.TYPE_TREE_NAVI:
                OkGo.<String>get(StringUtils.URL + StringUtils.NAVI)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid wanAndroid = gson.fromJson(result, WanAndroid.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    callBack.onSuccess(wanAndroid.getData(), StringUtils.TYPE_TREE_NAVI);
                                }
                            }
                        });
                break;
            case StringUtils.TYPE_TREE_KNOW:
                OkGo.<String>get(StringUtils.URL + StringUtils.TREE)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid wanAndroid = gson.fromJson(result, WanAndroid.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    callBack.onSuccess(wanAndroid.getData(), StringUtils.TYPE_TREE_KNOW);
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
                                Log.d(TAG, "----onSuccess 检查: " + params[1] + "/" + params[2]);
                                String result = response.body();
                                Gson gson = new Gson();
                                WanAndroid_Content wanAndroid = gson.fromJson(result, WanAndroid_Content.class);
                                if (wanAndroid.getErrorCode() == 0) {
                                    if (params.length > 3) {
                                        if (params[3] == StringUtils.TYPE_HOME_TOP_COLLECT || params[3] == StringUtils.TYPE_HOME_MORE_COLLECT) {
                                            callBack.onSuccess(params[2], StringUtils.TYPE_COLLECT_YES, params[3]);
                                            return;
                                        }
                                    }
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
                                    if (params.length > 3) {
                                        if (params[3] == StringUtils.TYPE_HOME_TOP_COLLECT || params[3] == StringUtils.TYPE_HOME_MORE_COLLECT) {
                                            callBack.onSuccess(params[2], StringUtils.TYPE_COLLECT_NO, params[3]);
                                            return;
                                        }
                                    }
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
    public void execute(final LoadTasksCallBack callBack, final String... infos) {
        switch (infos[0]) {
            case StringUtils.TYPE_HOTKEY_CONTENT_ADD:
            case StringUtils.TYPE_HOTKEY_CONTENT_LOAD:
                OkGo.<String>post(StringUtils.URL + StringUtils.HOT_KEY_CONTENT + infos[2] + "/json")
                        .params("k", infos[1])
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                String result = response.body();
                                Gson gson = new Gson();
                                SearchResult searchResult = gson.fromJson(result, SearchResult.class);
                                switch (searchResult.getErrorCode()) {
                                    case 0:
                                        if (infos[0].equals(StringUtils.TYPE_HOTKEY_CONTENT_ADD)) {
                                            callBack.onSuccess(searchResult.getData(), StringUtils.TYPE_HOT_KEY_CONTENT_ADD);
                                        } else {
                                            callBack.onSuccess(searchResult.getData(), StringUtils.TYPE_HOT_KEY_CONTENT_LOAD);
                                        }
                                        break;
                                    case -1:
                                        callBack.onError(searchResult.getErrorCode(), searchResult.getErrorMsg());
                                        break;
                                    default:
                                        callBack.onFailed();
                                        break;
                                }
                            }
                        });
                break;
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

