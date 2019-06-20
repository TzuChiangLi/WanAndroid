package com.lzq.wanandroid.Presenter;

import com.blankj.utilcode.util.SPUtils;
import com.lzq.wanandroid.Api.Contract;
import com.lzq.wanandroid.Api.LoadTasksCallBack;
import com.lzq.wanandroid.Api.WebTask;
import com.lzq.wanandroid.Base.BasePresenter;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Model.ProjectItem;
import com.lzq.wanandroid.Utils.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ProjectItemPresenter extends BasePresenter implements Contract.ProjectItemPresenter, LoadTasksCallBack<Object> {
    private static final String TAG = "WebPresenter";
    private Contract.ProjectItemView mView;
    private WebTask mTask;
    private List<ProjectItem.DataBean.Datas> mList = new ArrayList<>();

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private int ID = 0;

    public static ProjectItemPresenter createPresenter(Contract.ProjectItemView mView, WebTask mTask, int ID) {
        return new ProjectItemPresenter(mView, mTask, ID);
    }

    public ProjectItemPresenter(Contract.ProjectItemView mView, WebTask mTask, int ID) {
        this.mView = mView;
        this.mTask = mTask;
        this.ID = ID;
    }


    @Override
    public void initView() {
        if (mList == null || mList.size() == 0) {
            mList.clear();
        }
        for (int i = 0; i < 7; i++) {
            mList.add(new ProjectItem.DataBean.Datas("", "", "", ""));
        }
        mView.setEmptyContent(mList, StringUtils.TYPE_PROJECT_ITEM_LOAD);
    }

    @Override
    public void initProjectData(int flag, int page) {
        mTask.execute(this, flag, page, ID);
    }

    @Override
    public void collectArticle(int ID, boolean isCollect, int position) {
        if (SPUtils.getInstance("userinfo").getBoolean("isLogin")) {
            if (isCollect) {
                mTask.execute(this, StringUtils.TYPE_COLLECT_NO, ID, position);
            } else {
                mTask.execute(this, StringUtils.TYPE_COLLECT_YES, ID, position);
            }
        } else {
            Event event = new Event();
            event.target = Event.TARGET_MAIN;
            event.type = Event.TYPE_NEED_LOGIN;
            EventBus.getDefault().post(event);
        }
    }

    @Override
    public void getSelectedURL(String URL) {
        mView.goWebActivity(URL);
    }

    @Override
    public void onSuccess(Object data, int...params) {
        int position;
        switch (params[0]) {
            case StringUtils.TYPE_COLLECT_YES:
                position = (int) data;
                mView.collectedArticle(position, true);
                break;
            case StringUtils.TYPE_COLLECT_NO:
                position = (int) data;
                mView.collectedArticle(position, false);
                break;
            case StringUtils.TYPE_PROJECT_ITEM_LOAD:
            case StringUtils.TYPE_PROJECT_ITEM_ADD:
                mView.setProjectContent((List<ProjectItem.DataBean.Datas>)data, params[0]);
                break;
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onError(int code, String msg) {

    }

    @Override
    public void onFinish() {

    }
}
