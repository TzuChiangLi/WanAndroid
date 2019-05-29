package com.lzq.wanandroid.Presenter;

import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.WebTask;

public class ProjectPresenter implements Contract.ProjectPresenter {
    private static final String TAG = "WebPresenter";
    private Contract.ProjectView mView;
    private WebTask mTask;

    public static ProjectPresenter createPresenter(Contract.ProjectView mView, WebTask mTask) {
        return new ProjectPresenter(mView, mTask);
    }

    public ProjectPresenter(Contract.ProjectView mView, WebTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }


    @Override
    public void initTabView() {
    }

    @Override
    public void initProjectData() {

    }
}
