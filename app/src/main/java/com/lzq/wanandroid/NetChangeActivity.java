package com.lzq.wanandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.blankj.utilcode.util.NetworkUtils;
import com.hjq.toast.ToastUtils;
import com.lzq.wanandroid.Base.BaseActivity;


/*
 * 需要网络状态监听的集成此基类
 * */
public abstract class NetChangeActivity extends BaseActivity {
    //监听网络变化
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    //默认禁止屏幕旋转
    private boolean isAllowScrRoate = false;
    //采用ButterKnife绑定，每一个Acitivity都需要重新绑定，对标题的设置才能生效

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenRoate(isAllowScrRoate);
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }


    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()&& NetworkUtils.isAvailableByPing()) {
                doNetWork();
            } else {
                ToastUtils.show("您当前网络有问题，请检查设置！");
            }
        }
    }

    public abstract void doNetWork();

    public void setScreenRoate(boolean isAllowScrRoate) {
        this.isAllowScrRoate = isAllowScrRoate;
    }
}
