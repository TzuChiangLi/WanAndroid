package com.lzq.wanandroid.View;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.lzq.wanandroid.BaseActivity;
import com.lzq.wanandroid.Contract.Contract;
import com.lzq.wanandroid.Model.Event;
import com.lzq.wanandroid.Presenter.SettingPresenter;
import com.lzq.wanandroid.R;
import com.lzq.wanandroid.Utils.StringUtils;
import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsActivity extends BaseActivity implements Contract.SettingView, SwitchButton.OnCheckedChangeListener {
    private static final String TAG = "SettingsActivity";
    @BindView(R.id.set_titlebar)
    TitleBar mTitleBar;
    @BindView(R.id.set_night_mode)
    SwitchButton mNightBtn;
    @BindView(R.id.set_btn_logout)
    Button mLogoutBtn;
    private Contract.SettingPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        if (mPresenter == null) {
            mPresenter = SettingPresenter.createPresenter(this);
        }
        initView();

    }

    private void initView() {
        boolean nightMode = SPUtils.getInstance(StringUtils.CONFIG_SETTINGS, MODE_PRIVATE).getBoolean(StringUtils.KEY_NIGHT_MODE, false);
        mNightBtn.setChecked(nightMode);
        mNightBtn.setOnCheckedChangeListener(this);
        ImmersionBar.with(this).statusBarColor(R.color.bg_daily_mode).autoDarkModeEnable(true).fitsSystemWindows(true).keyboardEnable(true).init();
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finishActivity();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }


    @OnClick(R.id.set_btn_logout)
    public void doLogout() {
        if (SPUtils.getInstance("userinfo").getBoolean("isLogin")==false) {
            startActivity(LoginActivity.class);
            finish();
        } else {
            mPresenter.Logout();
        }
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        if (isChecked) {
            ToastUtils("开");
            mPresenter.changeDisplayMode(true);


        } else {
            ToastUtils("关");
            mPresenter.changeDisplayMode(false);
        }


    }

    @Override
    public void afterChangeMode() {
        getDelegate().setLocalNightMode((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        recreate();
        boolean nightMode = SPUtils.getInstance(StringUtils.CONFIG_SETTINGS).getBoolean(StringUtils.KEY_NIGHT_MODE, false);
        AppCompatDelegate.setDefaultNightMode(nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        Event event = new Event();
        event.target = Event.TARGET_MAIN;
        event.type = Event.TYPE_CHANGE_DAY_NIGHT_MODE;
        EventBus.getDefault().post(event);
    }

    @Override
    public void afterLogout() {
        Event event = new Event();
        event.target = Event.TARGET_USER;
        event.type = Event.TYPE_LOGOUT_SUCCESS;
        EventBus.getDefault().post(event);
        event.target = Event.TARGET_MAIN;
        event.type = Event.TYPE_LOGOUT_SUCCESS;
        EventBus.getDefault().post(event);
        event.target = Event.TARGET_COLLECT;
        event.type = Event.TYPE_COLLECT_LOGOUT;
        EventBus.getDefault().post(event);
        finishActivity();
    }

    @Override
    public void setPresenter(Contract.SettingPresenter presenter) {
        mPresenter = presenter;
    }
}
