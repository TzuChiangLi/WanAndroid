package com.lzq.wanandroid.Presenter;

import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.R;

public class SystemPresenter implements Contract.SystemPresenter {
    private Contract.SystemView mView;


    public SystemPresenter(Contract.SystemView mView) {
        this.mView = mView;
    }

    public static SystemPresenter createPresenter(Contract.SystemView mView) {
        return new SystemPresenter(mView);
    }


    @Override
    public void initTabView() {
        String[] titleName = {"导航", "知识体系"};
        int[] imgTab = {R.mipmap.navi, R.mipmap.tree_knowledge};
        mView.setTabView(titleName, imgTab);
    }
}
