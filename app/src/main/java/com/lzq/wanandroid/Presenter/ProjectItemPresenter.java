package com.lzq.wanandroid.Presenter;

import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.WebTask;

public class ProjectItemPresenter implements Contract.ProjectItemPresenter {
    private static final String TAG = "WebPresenter";
    private Contract.ProjectItemView mView;
    private WebTask mTask;

    public static ProjectItemPresenter createPresenter(Contract.ProjectItemView mView, WebTask mTask) {
        return new ProjectItemPresenter(mView, mTask);
    }

    public ProjectItemPresenter(Contract.ProjectItemView mView, WebTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }


}
