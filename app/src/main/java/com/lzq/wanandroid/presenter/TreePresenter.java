package com.lzq.wanandroid.presenter;

import com.lzq.wanandroid.api.Contract;
import com.lzq.wanandroid.base.BasePresenter;
import com.lzq.wanandroid.db.Tree;
import com.lzq.wanandroid.api.LoadTasksCallBack;
import com.lzq.wanandroid.model.Data;
import com.lzq.wanandroid.api.WebTask;
import com.lzq.wanandroid.utils.StringUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class TreePresenter extends BasePresenter implements Contract.TreePresenter, LoadTasksCallBack {
    private static final String TAG = "TreePresenter";
    private Contract.TreeView mView;
    private WebTask mTask;

    public static TreePresenter createPresenter(Contract.TreeView mView, WebTask mTask) {
        return new TreePresenter(mView, mTask);
    }

    public TreePresenter(Contract.TreeView mView, WebTask mTask) {
        this.mView = mView;
        this.mTask = mTask;
    }

    @Override
    public void initView(int type) {
        //先搜索数据库，没数据再联网
        int count = LitePal.where("type=?", String.valueOf(type)).count("Tree");
        if (count != 0) {
            //有数据
            List<Tree> data = LitePal.where("type=?", String.valueOf(type)).find(Tree.class);
            List<Data> mList = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                mList.add(new Data(data.get(i).getParentID(), data.get(i).getName()));
            }
            mView.initDataList(type, mList);
        }
        //无数据
        mView.onlineLoad(type);
    }

    @Override
    public void loadOnline(int type) {
        mTask.execute(this, type);
    }

    @Override
    public void getSelectedURL(String URL) {
        mView.goWebActivity(URL);
    }

    @Override
    public void onSuccess(Object o, final int...params) {
        final List<Data> data = (List<Data>) o;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Tree tree = null;
                LitePal.deleteAll(Tree.class, "type=?", String.valueOf(params[0]));
                switch (params[0]) {
                    case StringUtils.TYPE_TREE_NAVI:
                        for (int i = 0; i < data.size(); i++) {
                            tree = new Tree(data.get(i).getCid(), data.get(i).getName(), params[0]);
                            tree.save();
                        }
                        break;
                    case StringUtils.TYPE_TREE_KNOW:
                        for (int i = 0; i < data.size(); i++) {
                            tree = new Tree(data.get(i).getId(), data.get(i).getName(), params[0]);
                            tree.save();
                        }
                        break;
                }
            }
        }).start();
        mView.onLoadTreeData(params[0], data);
        mView.onFinishLoad();
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
