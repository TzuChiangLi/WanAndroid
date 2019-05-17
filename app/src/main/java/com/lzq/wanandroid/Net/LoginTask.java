package com.lzq.wanandroid.Net;

import com.google.gson.Gson;
import com.lzq.wanandroid.Model.WanAndroid_Content;
import com.lzq.wanandroid.Utils.StringUtils;
import com.lzq.wanandroid.LoadTasksCallBack;
import com.lzq.wanandroid.Model.Data;
import com.lzq.wanandroid.Model.WanAndroid;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class LoginTask implements NetTask<Data> {
    private static final String TAG = "LoginTask";
    private static LoginTask INSTANCE = null;


    public LoginTask() {
    }

    public static LoginTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginTask();
        }
        return INSTANCE;
    }


    @Override
    public void execute(LoadTasksCallBack callBack, int... params) {

    }

    @Override
    public void execute(final LoadTasksCallBack callBack,String...infos) {
        String username=infos[0], password=infos[1],type=infos[2];
        switch (type){
            case StringUtils.TYPE_LOGIN:
                OkGo.<String>post(StringUtils.URL + StringUtils.USER_LOGIN)
                        .params("username", username)
                        .params("password", password)
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
                        .params("username", username)
                        .params("password", password)
                        .params("repassword", password)
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
        }

    }
}
